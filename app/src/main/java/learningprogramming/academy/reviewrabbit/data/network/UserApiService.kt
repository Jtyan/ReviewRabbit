package learningprogramming.academy.reviewrabbit.data.network

import learningprogramming.academy.reviewrabbit.data.model.ChangePasswordApi
import learningprogramming.academy.reviewrabbit.data.model.PostUserForgetPasswordApi
import learningprogramming.academy.reviewrabbit.data.model.PostUserApi
import learningprogramming.academy.reviewrabbit.data.model.ResetPasswordApi
import learningprogramming.academy.reviewrabbit.data.model.UserLoginApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApiService {
    @POST("users/login")
    suspend fun loginUser(@Body postUserApi: PostUserApi): Response<UserLoginApiResponse>

    @POST("users/forgot")
    suspend fun recoverPassword(@Body postUserForgetPasswordApi: PostUserForgetPasswordApi): Response<Unit>

    @POST("users/recover")
    suspend fun resetPassword(@Body resetPasswordApi: ResetPasswordApi): Response<Unit>

    @PUT("users/password")
    suspend fun changePassword(@Body changePasswordApi: ChangePasswordApi): Response<Unit>
}