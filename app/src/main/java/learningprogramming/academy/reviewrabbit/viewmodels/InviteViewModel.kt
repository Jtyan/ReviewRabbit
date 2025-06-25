package learningprogramming.academy.reviewrabbit.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import learningprogramming.academy.reviewrabbit.data.model.InvitesResponse
import learningprogramming.academy.reviewrabbit.data.model.CreateCheckoutSessionRequest
import learningprogramming.academy.reviewrabbit.data.model.SendInvitesRequest
import learningprogramming.academy.reviewrabbit.data.repository.CompanyInviteResult
import learningprogramming.academy.reviewrabbit.data.repository.InviteRepository
import learningprogramming.academy.reviewrabbit.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class InviteViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val inviteRepository: InviteRepository
) : ViewModel() {
    private val _getInvites: MutableStateFlow<InviteUiState> = MutableStateFlow(InviteUiState.Idle)
    val getInvites: StateFlow<InviteUiState> = _getInvites.asStateFlow()
    private val _sendInviteResult: MutableStateFlow<SendInviteResult> =
        MutableStateFlow(SendInviteResult(companyId = 0L, state = InviteUiState.Idle))
    val sendInviteResult: StateFlow<SendInviteResult> = _sendInviteResult.asStateFlow()
    private val _redirectInviteState: MutableStateFlow<InviteCheckoutUiState> =
        MutableStateFlow(InviteCheckoutUiState())
    val redirectInviteState: StateFlow<InviteCheckoutUiState> = _redirectInviteState.asStateFlow()


    fun getInvites() {
        viewModelScope.launch {
            _getInvites.value = InviteUiState.Loading

            if (!sessionManager.isSessionValid()) {
                Log.e("InviteViewModel", "User ${sessionManager.userIdFlow} is not logged in")
                _getInvites.value = InviteUiState.Error("Please log in first.")
            }

            when (val response = inviteRepository.getInvites()) {
                is CompanyInviteResult.GetInviteSuccess -> {
                    Log.i("InviteViewModel", "Get Invites successful!")

                    val sortedList = response.response.sortedBy { it?.companyId }
                    _getInvites.value =
                        InviteUiState.GetInvitesSuccess(companyInvites = sortedList)
                }

                is CompanyInviteResult.Error -> {
                    Log.w("InviteViewModel", "(GetInvites) Error: ${response.message}")
                    _getInvites.value = InviteUiState.Error(response.message)
                }

                is CompanyInviteResult.NetworkError -> {
                    Log.w("InviteViewModel", "(GetInvites) Network error")
                    _getInvites.value =
                        InviteUiState.Error("Network error. Please check your connection.")
                }

                is CompanyInviteResult.UnknownError -> {
                    Log.e("InviteViewModel", "(GetInvites) Unknown error")
                    _getInvites.value = InviteUiState.Error("An unexpected error occurred.")
                }

                else -> {}
            }
        }
    }

    fun sendInvites(companyId: Long, text: String) {
        _sendInviteResult.value = SendInviteResult(companyId, InviteUiState.Loading)
        viewModelScope.launch {
            if (text.isEmpty()) {
                _sendInviteResult.value = SendInviteResult(
                    companyId,
                    InviteUiState.Error("Field is empty. Please enter emails.")
                )
                return@launch
            }

            val emails = text.trim().splitToSequence('\n').filter { it.isNotEmpty() }.toList()
            emails.forEach { email ->
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    _sendInviteResult.value = SendInviteResult(
                        companyId,
                        InviteUiState.Error("At least one email is invalid.")
                    )
                    return@launch
                }
            }

            val listOfInvites = SendInvitesRequest(companyId, emails)

            when (val response = inviteRepository.sendInvites(listOfInvites)) {
                is CompanyInviteResult.SendInviteSuccess -> {
                    Log.i("InviteViewModel", "Send Invites successful")
                    _sendInviteResult.value = SendInviteResult(
                        companyId,
                        InviteUiState.SendInvitesSuccess("Invites successfully sent!")
                    )
                    getInvites()
                }

                is CompanyInviteResult.Error -> {
                    Log.w("InviteViewModel", "(SendInvites) Error: ${response.message}")
                    _sendInviteResult.value = SendInviteResult(
                        companyId, InviteUiState.Error(response.message)
                    )
                }

                is CompanyInviteResult.NetworkError -> {
                    Log.w("InviteViewModel", "(SendInvites) Network error")
                    _sendInviteResult.value = SendInviteResult(
                        companyId,
                        InviteUiState.Error("Network error. Please check your connection.")
                    )
                }

                is CompanyInviteResult.UnknownError -> {
                    Log.e("InviteViewModel", "(SendInvites) Unknown error")
                    _sendInviteResult.value = SendInviteResult(
                        companyId, InviteUiState.Error("An unexpected error occurred.")
                    )
                }

                else -> {}
            }
        }
    }

    fun redirectInviteToStripe(companyId: Long) {
        viewModelScope.launch {
            when (val response =
                inviteRepository.getInviteCheckoutUrl(CreateCheckoutSessionRequest(companyId))) {
                is CompanyInviteResult.RedirectToStripeSuccess -> {
                    Log.i("InviteViewModel", "Request checkout Url success")
                    _redirectInviteState.value = InviteCheckoutUiState(
                        checkoutUrl = response.urlString,
                        message = "Redirecting to payment..."
                    )
                }

                is CompanyInviteResult.Error -> {
                    Log.e(
                        "InviteViewModel",
                        "Unable to get checkout url. companyId = $companyId. response = ${response.message}"
                    )
                    _redirectInviteState.value =
                        InviteCheckoutUiState(message = response.message)
                }

                is CompanyInviteResult.NetworkError -> {
                    Log.e(
                        "InviteViewModel",
                        "Network error occurred. Unable to retrieve checkout URL"
                    )
                    _redirectInviteState.value =
                        InviteCheckoutUiState(message = "Network error occurred. Unable to retrieve checkout URL")
                }

                is CompanyInviteResult.UnknownError -> {
                    Log.e(
                        "InviteViewModel",
                        "Unknown error occurred. Unable to retrieve checkout URL"
                    )
                    _redirectInviteState.value =
                        InviteCheckoutUiState(message = "UnknownError : ${response.exception}")
                }

                else -> {}
            }
        }
    }

    fun resetRedirectInviteState() {
        _redirectInviteState.value = InviteCheckoutUiState()
    }

    fun clearSendInviteResult() {
        _sendInviteResult.value = SendInviteResult(companyId = 0L, state = InviteUiState.Idle)
    }
}

sealed interface InviteUiState {
    data object Idle : InviteUiState
    data object Loading : InviteUiState
    data class GetInvitesSuccess(val companyInvites: List<InvitesResponse?>) : InviteUiState
    data class SendInvitesSuccess(val message: String) : InviteUiState
    data class Error(val message: String) : InviteUiState
}

data class SendInviteResult(
    val companyId: Long,
    val state: InviteUiState
)

data class InviteCheckoutUiState(
    val checkoutUrl: String? = null,
    val message: String? = null
)