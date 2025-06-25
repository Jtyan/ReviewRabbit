package learningprogramming.academy.reviewrabbit.data.invite.model

import kotlinx.serialization.Serializable

@Serializable
data class SendInvitesResponse(
    val status: String,
    val nInvites: Int
)