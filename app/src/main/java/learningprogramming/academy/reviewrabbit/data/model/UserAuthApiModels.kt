package learningprogramming.academy.reviewrabbit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    val id: Long,
    val email: String,
    val token: String,
    val expiration: Long
)

@Serializable
data class LoginUserRequest(
    val email: String,
    val password: String
)

@Serializable
data class SignupUserRequest(
    val email: String,
    val password: String
)

@Serializable
data class ForgetPasswordRequest(
    val email: String
)

@Serializable
data class ResetPasswordRequest(
    val email: String,
    val token: String,
    val newPassword: String
)

@Serializable
data class ChangePasswordRequest(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)