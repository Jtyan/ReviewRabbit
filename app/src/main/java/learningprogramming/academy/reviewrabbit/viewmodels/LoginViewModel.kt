package learningprogramming.academy.reviewrabbit.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import learningprogramming.academy.reviewrabbit.data.model.PostUserApi
import learningprogramming.academy.reviewrabbit.data.model.UserLoginApiResponse
import learningprogramming.academy.reviewrabbit.data.repository.UserAuthResult
import learningprogramming.academy.reviewrabbit.data.repository.UserRepository
import learningprogramming.academy.reviewrabbit.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginScreenUiState>(LoginScreenUiState.Idle)
    val loginUiState: StateFlow<LoginScreenUiState> = _loginUiState.asStateFlow()

    val isLoggedIn: StateFlow<Boolean> = sessionManager.isLoggedInFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun onLoginClicked(postUserApi: PostUserApi) {
        viewModelScope.launch {
            _loginUiState.value = LoginScreenUiState.Loading
            when (val response = userRepository.loginUser(postUserApi)) {
                is UserAuthResult.LoginUserSuccess -> {
                    Log.i("LoginViewModel", "Login successful")
                    _loginUiState.value = LoginScreenUiState.Success(response.userData)
                }

                is UserAuthResult.Error -> {
                    Log.w("LoginViewModel", "Login error: ${response.message}")
                    _loginUiState.value = LoginScreenUiState.Error(response.message)
                }

                UserAuthResult.NetworkError -> {
                    Log.w("LoginViewModel", "Login network error")
                    _loginUiState.value = LoginScreenUiState.Error("Network error. Please check your connection.")
                }

                is UserAuthResult.UnknownError -> {
                    Log.e("LoginViewModel", "Login unknown error", response.exception)
                    _loginUiState.value = LoginScreenUiState.Error("An unexpected error occurred.")
                }
                else -> {}
            }
        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            sessionManager.clearSession()
        }
    }

    fun resetLoginStateToIdle() {
        _loginUiState.value = LoginScreenUiState.Idle
    }
}


sealed interface LoginScreenUiState {
    data object Idle : LoginScreenUiState
    data object Loading : LoginScreenUiState
    data class Success(val userCred: UserLoginApiResponse) : LoginScreenUiState
    data class Error(val message: String) : LoginScreenUiState
}