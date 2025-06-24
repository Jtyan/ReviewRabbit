package learningprogramming.academy.reviewrabbit.data.repository

import android.util.Log
import learningprogramming.academy.reviewrabbit.data.model.ChangePasswordApi
import learningprogramming.academy.reviewrabbit.data.model.PostUserForgetPasswordApi
import learningprogramming.academy.reviewrabbit.data.model.PostUserApi
import learningprogramming.academy.reviewrabbit.data.model.ResetPasswordApi
import learningprogramming.academy.reviewrabbit.data.model.UserLoginApiResponse
import learningprogramming.academy.reviewrabbit.data.network.UserApiService
import learningprogramming.academy.reviewrabbit.data.session.SessionManager
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

interface UserRepository {
    suspend fun loginUser(postUserApi: PostUserApi): UserAuthResult
    suspend fun recoverPassword(postUserForgetPasswordApi: PostUserForgetPasswordApi): UserAuthResult
    suspend fun resetPassword(resetPasswordApi: ResetPasswordApi): UserAuthResult
    suspend fun changePassword(changePasswordApi: ChangePasswordApi): UserAuthResult
    suspend fun signupUser(postUserApi: PostUserApi): UserAuthResult
}

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val sessionManager: SessionManager
) : UserRepository {
    override suspend fun loginUser(postUserApi: PostUserApi): UserAuthResult {
        return try {
            val response: Response<UserLoginApiResponse> =
                userApiService.loginUser(postUserApi)
            if (response.isSuccessful) {
                val userLoginApiResponse = response.body()
                if (userLoginApiResponse != null) {
                    sessionManager.saveSession(userLoginApiResponse)
                    UserAuthResult.LoginUserSuccess(userData = userLoginApiResponse)
                } else {
                    Log.e("UserRepository", "Login successful but body was null")
                    UserAuthResult.Error("Login successful but server response was empty.")
                }
            } else {
                val errorBodyString = response.errorBody()?.string()
                val errorMessage = if (!errorBodyString.isNullOrBlank()) {
                    errorBodyString
                } else {
                    "Login failed: HTTP: ${response.code()}"
                }
                Log.e("UserRepository", "Login Api Error: ${response.code()}")
                UserAuthResult.Error(errorMessage)
            }
        } catch (e: IOException) {
            Log.e("UserRepository", "Login network error: ${e.message}")
            UserAuthResult.NetworkError
        } catch (e: Exception) {
            Log.e("UserRepository", "Login failed with unexpected error: ${e.message}")
            UserAuthResult.UnknownError(e)
        }
    }

    override suspend fun recoverPassword(postUserForgetPasswordApi: PostUserForgetPasswordApi): UserAuthResult {
        return try {
            val response = userApiService.recoverPassword(postUserForgetPasswordApi)
            if (response.isSuccessful) {
                Log.i("UserRepository", "Email for password recovery successfully sent")
                UserAuthResult.PasswordRecoverySent
            } else {
                Log.e("UserRepository", "Password recovery API error: ${response.code()}")
                UserAuthResult.Error(response.errorBody().toString())
            }
        } catch (e: IOException) {
            Log.e("UserRepository", "Password recovery network error: ${e.message}")
            UserAuthResult.NetworkError
        } catch (e: Exception) {
            Log.e("UserRepository", "Password recovery error: ${e.message}")
            UserAuthResult.UnknownError(e)
        }
    }

    override suspend fun resetPassword(resetPasswordApi: ResetPasswordApi): UserAuthResult {
        return try {
            val response = userApiService.resetPassword(resetPasswordApi)
            if (response.isSuccessful) {
                Log.i("UserRepository", "Password reset successful")
                UserAuthResult.ResetPasswordSuccess
            } else {
                Log.e("UserRepository", "Password reset API error: ${response.code()}")
                UserAuthResult.Error(response.errorBody().toString())
            }
        } catch (e: IOException) {
            Log.e("UserRepository", "Password reset network error: ${e.message}")
            UserAuthResult.NetworkError
        } catch (e: Exception) {
            Log.e("UserRepository", "Password reset unknown error: ${e.message}")
            UserAuthResult.UnknownError(e)
        }
    }

    override suspend fun changePassword(changePasswordApi: ChangePasswordApi): UserAuthResult {
        return try {
            val response = userApiService.changePassword(changePasswordApi)
            if (response.isSuccessful) {
                Log.i("UserRepository", "Password successfully changed.")
                UserAuthResult.ChangePasswordSuccess
            } else {
                Log.e("UserRepository", "Password change has failed. Status code: ${response.code()}")
                UserAuthResult.Error("Password change has failed.")
            }
        } catch (e: IOException) {
            Log.e("UserRepository", "Password change network error: ${e.message}")
            UserAuthResult.NetworkError
        } catch (e: Exception) {
            Log.e("UserRepository", "Password change unknown error: ${e.message}")
            UserAuthResult.UnknownError(e)
        }
    }

    override suspend fun signupUser(postUserApi: PostUserApi): UserAuthResult {
        return try {
            val response = userApiService.signupUser(postUserApi)
            if (response.isSuccessful) {
                Log.i("UserRepository", "User sign up successful")
                UserAuthResult.SignupUserSuccess
            } else {
                Log.e("UserRepository", "Sign up Error. ${response.code()} ${response.message()}")
                UserAuthResult.Error("Unable to sign up user. ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Log.e("UserRepository", "User sign up network error: ${e.message}")
            UserAuthResult.NetworkError
        } catch (e: Exception) {
            Log.e("UserRepository", "User sign up unknown error: ${e.message}")
            UserAuthResult.UnknownError(e)
        }
    }
}

sealed interface UserAuthResult {
    data class LoginUserSuccess(val userData: UserLoginApiResponse) : UserAuthResult
    data object PasswordRecoverySent : UserAuthResult
    data object ResetPasswordSuccess: UserAuthResult
    data object ChangePasswordSuccess: UserAuthResult
    data object SignupUserSuccess: UserAuthResult
    data class Error(val message: String) : UserAuthResult
    data object NetworkError : UserAuthResult
    data class UnknownError(val exception: Throwable) : UserAuthResult
}