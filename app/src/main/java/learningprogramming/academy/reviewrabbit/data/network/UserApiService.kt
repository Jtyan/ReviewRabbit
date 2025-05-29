package learningprogramming.academy.reviewrabbit.data.network

import learningprogramming.academy.reviewrabbit.data.model.PostUserLoginApi
import learningprogramming.academy.reviewrabbit.data.model.UserLoginApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("users/login")
    suspend fun loginUser(@Body postUserLoginApi: PostUserLoginApi): Response<UserLoginApiResponse>
}