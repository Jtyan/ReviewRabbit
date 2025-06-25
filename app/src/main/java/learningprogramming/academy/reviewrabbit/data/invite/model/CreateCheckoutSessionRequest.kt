package learningprogramming.academy.reviewrabbit.data.invite.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateCheckoutSessionRequest(
    val companyId: Long
)