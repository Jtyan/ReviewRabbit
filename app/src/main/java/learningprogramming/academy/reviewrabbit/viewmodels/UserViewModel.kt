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
import learningprogramming.academy.reviewrabbit.data.model.PostUserLoginApi
import learningprogramming.academy.reviewrabbit.data.model.UserLoginApiResponse
import learningprogramming.academy.reviewrabbit.data.repository.LoginResult
import learningprogramming.academy.reviewrabbit.data.repository.UserRepository
import learningprogramming.academy.reviewrabbit.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
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

    fun onLoginClicked(postUserLoginApi: PostUserLoginApi) {
        viewModelScope.launch {
            _loginUiState.value = LoginScreenUiState.Loading
            when (val response = userRepository.loginUser(postUserLoginApi)) {
                is LoginResult.Success -> {
                    Log.i("UserViewModel", "Login successful")
                    _loginUiState.value = LoginScreenUiState.Success(response.userData)
                }

                is LoginResult.Error -> {
                    Log.w("UserViewModel", "Login error: ${response.message}")
                    _loginUiState.value = LoginScreenUiState.Error(response.message)
                }

                LoginResult.NetworkError -> {
                    Log.w("UserViewModel", "Login network error")
                    _loginUiState.value = LoginScreenUiState.Error("Network error. Please check your connection.")
                }

                is LoginResult.UnknownError -> {
                    Log.e("UserViewModel", "Login unknown error", response.exception)
                    _loginUiState.value = LoginScreenUiState.Error("An unexpected error occurred.")
                }
            }
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