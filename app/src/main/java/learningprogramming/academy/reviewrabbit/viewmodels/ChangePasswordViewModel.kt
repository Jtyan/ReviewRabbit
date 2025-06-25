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
import learningprogramming.academy.reviewrabbit.data.user.model.ChangePasswordRequest
import learningprogramming.academy.reviewrabbit.data.user.UserAuthResult
import learningprogramming.academy.reviewrabbit.data.user.UserRepository
import learningprogramming.academy.reviewrabbit.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _changePasswordUiState =
        MutableStateFlow<ChangePasswordUiState>(ChangePasswordUiState.Idle)
    val changePasswordUiState: StateFlow<ChangePasswordUiState> =
        _changePasswordUiState.asStateFlow()

    fun onChangePasswordClicked(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            val email = sessionManager.getEmail()

            if (email.isNullOrBlank()) {
                Log.e("ChangePassword", "Email is null or empty")
                UserAuthResult.Error("Unable to find user email address")
                return@launch
            } else {
                val changePasswordRequest = ChangePasswordRequest(
                    email = email,
                    oldPassword = oldPassword,
                    newPassword = newPassword
                )

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    _changePasswordUiState.value =
                        ChangePasswordUiState.Error("Email $email is invalid")
                    return@launch
                }
                if (newPassword.isEmpty()) {
                    _changePasswordUiState.value =
                        ChangePasswordUiState.Error("Password must not be empty")
                    return@launch
                } else if (newPassword.length < 8) {
                    _changePasswordUiState.value =
                        ChangePasswordUiState.Error("Password must be 8 or more characters")
                    return@launch
                }

                _changePasswordUiState.value = ChangePasswordUiState.Loading

                when (val response = userRepository.changePassword(changePasswordRequest)) {
                    is UserAuthResult.ChangePasswordSuccess -> {
                        Log.i("ChangePasswordViewModel", "Password reset successful!")
                        _changePasswordUiState.value =
                            ChangePasswordUiState.Success("Password changed successfully!")
                    }

                    is UserAuthResult.Error -> {
                        Log.w(
                            "ChangePasswordViewModel",
                            "Password change error: ${response.message}"
                        )
                        _changePasswordUiState.value =
                            ChangePasswordUiState.Error("The password is invalid")
                    }

                    is UserAuthResult.NetworkError -> {
                        Log.w("ChangePasswordViewModel", "Password change network error")
                        _changePasswordUiState.value =
                            ChangePasswordUiState.Error("Network error. Please check your connection.")
                    }

                    is UserAuthResult.UnknownError -> {
                        Log.e(
                            "ChangePasswordViewModel",
                            "Password change unknown error",
                            response.exception
                        )
                        _changePasswordUiState.value =
                            ChangePasswordUiState.Error("An unexpected error occurred.")
                    }

                    else -> {}
                }
            }
        }
    }

    fun resetStateToIdle() {
        _changePasswordUiState.value = ChangePasswordUiState.Idle
    }
}

sealed interface ChangePasswordUiState {
    data object Idle : ChangePasswordUiState
    data object Loading : ChangePasswordUiState
    data class Success(val message: String) : ChangePasswordUiState
    data class Error(val message: String) : ChangePasswordUiState
}