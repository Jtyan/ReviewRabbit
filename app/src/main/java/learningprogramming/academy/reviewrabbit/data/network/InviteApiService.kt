package learningprogramming.academy.reviewrabbit.data.network

import learningprogramming.academy.reviewrabbit.data.model.GetInvitesApiResponse
import learningprogramming.academy.reviewrabbit.data.model.SendInvitesApiModel
import learningprogramming.academy.reviewrabbit.data.model.SendInvitesApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InviteApiService {

    @GET("invite/all")
    suspend fun getInvites(): Response<List<GetInvitesApiResponse>>

    @POST("invite")
    suspend fun sendInvites(@Body invites: SendInvitesApiModel): Response<SendInvitesApiResponse>
}