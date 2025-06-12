package learningprogramming.academy.reviewrabbit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetInvitesApiResponse(
    val companyId: Long,
    val companyName: String,
    val nInvites: Int
)

@Serializable
data class SendInvitesApiModel(
    val companyId: Long,
    val emails: List<String>
)

@Serializable
data class SendInvitesApiResponse(
    val status: String,
    val nInvites: Int
)
