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
import learningprogramming.academy.reviewrabbit.data.user.model.ResetPasswordRequest
import learningprogramming.academy.reviewrabbit.data.user.UserAuthResult
import learningprogramming.academy.reviewrabbit.data.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _resetPasswordUiState = MutableStateFlow<ResetPasswordUiState>(ResetPasswordUiState.Idle)
    val resetPasswordUiState: StateFlow<ResetPasswordUiState> = _resetPasswordUiState.asStateFlow()

    fun onResetPasswordClicked(email: String, token: String, password: String) {
        val resetPasswordRequest = ResetPasswordRequest(
            email = email,
            token = token,
            newPassword = password
        )

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _resetPasswordUiState.value = ResetPasswordUiState.Error("Email $email is invalid")
            return
        }
        if (password.isEmpty()) {
            _resetPasswordUiState.value = ResetPasswordUiState.Error("Password must not be empty")
            return
        } else if (password.length < 8) {
            _resetPasswordUiState.value = ResetPasswordUiState.Error("Password must be 8 or more characters")
            return
        }
        viewModelScope.launch {
            _resetPasswordUiState.value = ResetPasswordUiState.Loading
            when (val response = userRepository.resetPassword(resetPasswordRequest)) {
                is UserAuthResult.ResetPasswordSuccess -> {
                    Log.i("ResetPasswordViewModel", "Password reset successful!")
                    _resetPasswordUiState.value = ResetPasswordUiState.Success("Password reset successful! Please log in with your new password")
                }

                is UserAuthResult.Error -> {
                    Log.w("ResetPasswordViewModel", "Password reset error: ${response.message}")
                    _resetPasswordUiState.value = ResetPasswordUiState.Error("The email / token combination is invalid")
                }

                is UserAuthResult.NetworkError -> {
                    Log.w("ResetPasswordViewModel", "Password reset network error")
                    _resetPasswordUiState.value =
                        ResetPasswordUiState.Error("Network error. Please check your connection.")
                }

                is UserAuthResult.UnknownError -> {
                    Log.e("ResetPasswordViewModel", "Password reset unknown error", response.exception)
                    _resetPasswordUiState.value =
                        ResetPasswordUiState.Error("An unexpected error occurred.")
                }

                else -> {}
            }
        }
    }

    fun resetStateToIdle() {
        _resetPasswordUiState.value = ResetPasswordUiState.Idle
    }
}

sealed interface ResetPasswordUiState {
    data object Idle : ResetPasswordUiState
    data object Loading : ResetPasswordUiState
    data class Success(val message: String) : ResetPasswordUiState
    data class Error(val message: String) : ResetPasswordUiState
}