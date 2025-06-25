package learningprogramming.academy.reviewrabbit.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class ForgetPasswordRequest(
    val email: String
)