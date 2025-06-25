package learningprogramming.academy.reviewrabbit.data.user

import learningprogramming.academy.reviewrabbit.data.user.model.ChangePasswordRequest
import learningprogramming.academy.reviewrabbit.data.user.model.ForgetPasswordRequest
import learningprogramming.academy.reviewrabbit.data.user.model.LoginUserRequest
import learningprogramming.academy.reviewrabbit.data.user.model.LoginUserResponse
import learningprogramming.academy.reviewrabbit.data.user.model.ResetPasswordRequest
import learningprogramming.academy.reviewrabbit.data.user.model.SignupUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApiService {
    @POST("users/login")
    suspend fun loginUser(@Body loginUserRequest: LoginUserRequest): Response<LoginUserResponse>

    @POST("users/forgot")
    suspend fun recoverPassword(@Body forgetPasswordRequest: ForgetPasswordRequest): Response<Unit>

    @POST("users/recover")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<Unit>

    @PUT("users/password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<Unit>

    @POST("users")
    suspend fun signupUser(@Body signupUserRequest: SignupUserRequest):Response<Unit>
}