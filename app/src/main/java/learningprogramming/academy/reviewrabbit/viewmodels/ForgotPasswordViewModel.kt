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
import learningprogramming.academy.reviewrabbit.data.model.PostUserForgetPasswordApi
import learningprogramming.academy.reviewrabbit.data.repository.UserAuthResult
import learningprogramming.academy.reviewrabbit.data.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _forgotPasswordUiState =
        MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val forgotPasswordUiState: StateFlow<ForgotPasswordUiState> =
        _forgotPasswordUiState.asStateFlow()

    fun onRecoverPasswordClicked(postUserForgetPasswordApi: PostUserForgetPasswordApi) {
        val email = postUserForgetPasswordApi.email

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _forgotPasswordUiState.value = ForgotPasswordUiState.Error("Email $email is invalid")
            return
        }
        viewModelScope.launch {
            _forgotPasswordUiState.value = ForgotPasswordUiState.Loading
             when (val response = userRepository.recoverPassword(postUserForgetPasswordApi)) {
                is UserAuthResult.PasswordRecoverySent -> {
                    Log.i("ForgotPasswordViewModel", "Password recovery email sent for $email")
                    _forgotPasswordUiState.value = ForgotPasswordUiState.Success
                }

                is UserAuthResult.Error -> {
                    Log.w("ForgotPasswordViewModel", "Password Recovery error: ${response.message}")
                    _forgotPasswordUiState.value = ForgotPasswordUiState.Error(response.message)
                }

                UserAuthResult.NetworkError -> {
                    Log.w("ForgotPasswordViewModel", "Password Recovery network error")
                    _forgotPasswordUiState.value =
                        ForgotPasswordUiState.Error("Network error. Please check your connection.")
                }

                is UserAuthResult.UnknownError -> {
                    Log.e("ForgotPasswordViewModel", "Password Recovery unknown error", response.exception)
                    _forgotPasswordUiState.value =
                        ForgotPasswordUiState.Error("An unexpected error occurred.")
                }

                else -> {}
            }
        }
    }

    fun resetForgotPasswordStateToIdle() {
        _forgotPasswordUiState.value = ForgotPasswordUiState.Idle
    }
}


sealed interface ForgotPasswordUiState {
    data object Idle : ForgotPasswordUiState
    data object Loading : ForgotPasswordUiState
    data object Success : ForgotPasswordUiState
    data class Error(val message: String) : ForgotPasswordUiState
}