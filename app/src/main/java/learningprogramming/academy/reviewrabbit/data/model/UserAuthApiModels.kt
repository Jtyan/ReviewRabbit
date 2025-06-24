package learningprogramming.academy.reviewrabbit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginApiResponse(
    val id: Long,
    val email: String,
    val token: String,
    val expiration: Long
)

@Serializable
data class PostUserApi(
    val email: String,
    val password: String
)

@Serializable
data class PostUserForgetPasswordApi(
    val email: String
)

@Serializable
data class ResetPasswordApi(
    val email: String,
    val token: String,
    val newPassword: String
)

@Serializable
data class ChangePasswordApi(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)