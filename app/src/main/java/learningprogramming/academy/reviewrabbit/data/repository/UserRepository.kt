package learningprogramming.academy.reviewrabbit.data.repository

import android.util.Log
import learningprogramming.academy.reviewrabbit.data.model.PostUserLoginApi
import learningprogramming.academy.reviewrabbit.data.model.UserLoginApiResponse
import learningprogramming.academy.reviewrabbit.data.network.UserApiService
import learningprogramming.academy.reviewrabbit.data.session.SessionManager
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

interface UserRepository {
    suspend fun loginUser(postUserLoginApi: PostUserLoginApi): LoginResult
}

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val sessionManager: SessionManager
) :
    UserRepository {
    override suspend fun loginUser(postUserLoginApi: PostUserLoginApi): LoginResult {
        return try {
            val response: Response<UserLoginApiResponse> =
                userApiService.loginUser(postUserLoginApi)
            if (response.isSuccessful) {
                val userLoginApiResponse = response.body()
                if (userLoginApiResponse != null) {
                    sessionManager.saveSession(userLoginApiResponse)
                    LoginResult.Success(userData = userLoginApiResponse)
                } else {
                    Log.e("UserRepository", "Login successful but body was null")
                    LoginResult.Error("Login successful but server response was empty.")
                }
            } else {
                val errorBodyString = response.errorBody()?.string()
                val errorMessage = if (!errorBodyString.isNullOrBlank()) {
                    errorBodyString
                } else {
                    "Login failed: HTTP: ${response.code()}"
                }
                Log.e("UserRepository", "Login Api Error: ${response.code()}")
                LoginResult.Error(errorMessage)
            }
        } catch (e: IOException) {
            Log.e("UserRepository", "Login network error: ${e.message}")
            LoginResult.NetworkError
        } catch (e: Exception) {
            Log.e("UserRepository", "Login failed with unexpected error: ${e.message}")
            LoginResult.UnknownError(e)
        }
    }
}

sealed interface LoginResult {
    data class Success(val userData: UserLoginApiResponse) : LoginResult
    data class Error(val message: String) : LoginResult
    data object NetworkError : LoginResult
    data class UnknownError(val exception: Throwable) : LoginResult
}