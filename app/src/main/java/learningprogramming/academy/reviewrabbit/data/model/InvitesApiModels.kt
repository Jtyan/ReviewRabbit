package learningprogramming.academy.reviewrabbit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class InvitesResponse(
    val companyId: Long,
    val companyName: String,
    val nInvites: Int
)

@Serializable
data class SendInvitesRequest(
    val companyId: Long,
    val emails: List<String>
)

@Serializable
data class SendInvitesResponse(
    val status: String,
    val nInvites: Int
)

@Serializable
data class CreateCheckoutSessionRequest(
    val companyId: Long
)