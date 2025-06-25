package learningprogramming.academy.reviewrabbit.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val email: String,
    val token: String,
    val newPassword: String
)