package learningprogramming.academy.reviewrabbit.data.invite.model

import kotlinx.serialization.Serializable

@Serializable
data class InvitesResponse(
    val companyId: Long,
    val companyName: String,
    val nInvites: Int
)