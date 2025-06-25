package learningprogramming.academy.reviewrabbit.data.user

import android.util.Log
import learningprogramming.academy.reviewrabbit.data.session.SessionManager
import learningprogramming.academy.reviewrabbit.data.user.model.ChangePasswordRequest
import learningprogramming.academy.reviewrabbit.data.user.model.ForgetPasswordRequest
import learningprogramming.academy.reviewrabbit.data.user.model.LoginUserRequest
import learningprogramming.academy.reviewrabbit.data.user.model.LoginUserResponse
import learningprogramming.academy.reviewrabbit.data.user.model.ResetPasswordRequest
import learningprogramming.academy.reviewrabbit.data.user.model.SignupUserRequest
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

interface UserRepository {
    suspend fun loginUser(loginUserRequest: LoginUserRequest): UserAuthResult
    suspend fun recoverPassword(forgetPasswordRequest: ForgetPasswordRequest): UserAuthResult
    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): UserAuthResult
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): UserAuthResult
    suspend fun signupUser(signupUserRequest: SignupUserRequest): UserAuthResult
}

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val sessionManager: SessionManager
) : UserRepository {
    override suspend fun loginUser(loginUserRequest: LoginUserRequest): UserAuthResult {
        return try {
            val response: Response<LoginUserResponse> =
                userApiService.loginUser(loginUserRequest)
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

    override suspend fun recoverPassword(forgetPasswordRequest: ForgetPasswordRequest): UserAuthResult {
        return try {
            val response = userApiService.recoverPassword(forgetPasswordRequest)
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

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): UserAuthResult {
        return try {
            val response = userApiService.resetPassword(resetPasswordRequest)
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

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): UserAuthResult {
        return try {
            val response = userApiService.changePassword(changePasswordRequest)
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

    override suspend fun signupUser(signupUserRequest: SignupUserRequest): UserAuthResult {
        return try {
            val response = userApiService.signupUser(signupUserRequest)
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
    data class LoginUserSuccess(val userData: LoginUserResponse) : UserAuthResult
    data object PasswordRecoverySent : UserAuthResult
    data object ResetPasswordSuccess: UserAuthResult
    data object ChangePasswordSuccess: UserAuthResult
    data object SignupUserSuccess: UserAuthResult
    data class Error(val message: String) : UserAuthResult
    data object NetworkError : UserAuthResult
    data class UnknownError(val exception: Throwable) : UserAuthResult
}