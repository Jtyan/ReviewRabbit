package learningprogramming.academy.reviewrabbit.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserResponse(
    val id: Long,
    val email: String,
    val token: String,
    val expiration: Long
)