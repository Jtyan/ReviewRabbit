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
import learningprogramming.academy.reviewrabbit.data.user.UserAuthResult
import learningprogramming.academy.reviewrabbit.data.user.UserRepository
import learningprogramming.academy.reviewrabbit.data.user.model.SignupUserRequest
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _signupUiState = MutableStateFlow<SignupScreenUiState>(SignupScreenUiState.Idle)
    val signupUiState: StateFlow<SignupScreenUiState> = _signupUiState.asStateFlow()


    fun onSignupClicked(signupUserRequest: SignupUserRequest) {
        viewModelScope.launch {

            if (!Patterns.EMAIL_ADDRESS.matcher(signupUserRequest.email).matches()) {
                _signupUiState.value =
                    SignupScreenUiState.Error("Email ${signupUserRequest.email} is invalid")
                return@launch
            }
            if (signupUserRequest.password.isEmpty()) {
                _signupUiState.value =
                    SignupScreenUiState.Error("Password must not be empty")
                return@launch
            } else if (signupUserRequest.password.length < 8) {
                _signupUiState.value =
                    SignupScreenUiState.Error("Password must be 8 or more characters")
                return@launch
            }

            _signupUiState.value = SignupScreenUiState.Loading

            when (val response = userRepository.signupUser(signupUserRequest)) {
                is UserAuthResult.SignupUserSuccess -> {
                    Log.i("LoginViewModel", "User sign up successful")
                    _signupUiState.value = SignupScreenUiState.Success("User sign up successfully. You will be redirected to Login page...")
                }

                is UserAuthResult.Error -> {
                    Log.w("LoginViewModel", "Signup error: ${response.message}")
                    _signupUiState.value = SignupScreenUiState.Error(response.message)
                }

                UserAuthResult.NetworkError -> {
                    Log.w("LoginViewModel", "Signup network error")
                    _signupUiState.value = SignupScreenUiState.Error("Network error. Please check your connection.")
                }

                is UserAuthResult.UnknownError -> {
                    Log.e("LoginViewModel", "Signup unknown error", response.exception)
                    _signupUiState.value = SignupScreenUiState.Error("An unexpected error occurred.")
                }
                else -> {}
            }
        }
    }

    fun resetSignupStateToIdle() {
        _signupUiState.value = SignupScreenUiState.Idle
    }
}


sealed interface SignupScreenUiState {
    data object Idle : SignupScreenUiState
    data object Loading : SignupScreenUiState
    data class Success(val message: String) : SignupScreenUiState
    data class Error(val message: String) : SignupScreenUiState
}