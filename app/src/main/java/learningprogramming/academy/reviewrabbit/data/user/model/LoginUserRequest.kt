package learningprogramming.academy.reviewrabbit.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserRequest(
    val email: String,
    val password: String
)