package learningprogramming.academy.reviewrabbit.data.invite.model

import kotlinx.serialization.Serializable

@Serializable
data class SendInvitesRequest(
    val companyId: Long,
    val emails: List<String>
)
