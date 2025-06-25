package learningprogramming.academy.reviewrabbit.data.network

import learningprogramming.academy.reviewrabbit.data.model.InvitesResponse
import learningprogramming.academy.reviewrabbit.data.model.CreateCheckoutSessionRequest
import learningprogramming.academy.reviewrabbit.data.model.SendInvitesRequest
import learningprogramming.academy.reviewrabbit.data.model.SendInvitesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface InviteApiService {

    @GET("invite/all")
    suspend fun getInvites(): Response<List<InvitesResponse>>

    @POST("invite")
    suspend fun sendInvites(@Body invites: SendInvitesRequest): Response<SendInvitesResponse>

    @Headers("Accept: text/plain")
    @POST("invite/promoted")
    suspend fun getInviteCheckoutUrl(@Body createCheckoutSessionRequest: CreateCheckoutSessionRequest): Response<String>
}