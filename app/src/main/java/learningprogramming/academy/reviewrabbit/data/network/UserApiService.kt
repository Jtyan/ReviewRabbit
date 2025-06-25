package learningprogramming.academy.reviewrabbit.data.network

import learningprogramming.academy.reviewrabbit.data.model.ChangePasswordRequest
import learningprogramming.academy.reviewrabbit.data.model.ForgetPasswordRequest
import learningprogramming.academy.reviewrabbit.data.model.LoginUserRequest
import learningprogramming.academy.reviewrabbit.data.model.ResetPasswordRequest
import learningprogramming.academy.reviewrabbit.data.model.SignupUserRequest
import learningprogramming.academy.reviewrabbit.data.model.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApiService {
    @POST("users/login")
    suspend fun loginUser(@Body loginUserRequest: LoginUserRequest): Response<UserLoginResponse>

    @POST("users/forgot")
    suspend fun recoverPassword(@Body forgetPasswordRequest: ForgetPasswordRequest): Response<Unit>

    @POST("users/recover")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<Unit>

    @PUT("users/password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<Unit>

    @POST("users")
    suspend fun signupUser(@Body signupUserRequest: SignupUserRequest):Response<Unit>
}