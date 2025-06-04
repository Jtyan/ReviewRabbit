package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomTextField
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.util.PasswordOutputTransformation
import learningprogramming.academy.reviewrabbit.viewmodels.ResetPasswordUiState
import learningprogramming.academy.reviewrabbit.viewmodels.ResetPasswordViewModel

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    resetPasswordViewModel: ResetPasswordViewModel,
    onResetSuccessNavigation: (String) -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val emailState = rememberTextFieldState()
    val recoveryTokenState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    val confirmPasswordState = rememberTextFieldState()

    val resetPasswordUiState by resetPasswordViewModel.resetPasswordUiState.collectAsState()

    val isPasswordMatching: Boolean = resetPasswordViewModel.checkIfPasswordsMatch(
        passwordState.text.toString(), confirmPasswordState.text.toString()
    )

    DisposableEffect(Unit) {
        onDispose {
            resetPasswordViewModel.resetStateToIdle()
        }
    }

    LaunchedEffect(resetPasswordUiState) {
        if (resetPasswordUiState is ResetPasswordUiState.Success) {
            val notification = (resetPasswordUiState as ResetPasswordUiState.Success).message
            onResetSuccessNavigation(notification)
            resetPasswordViewModel.resetStateToIdle()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 48.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Reset Password",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (resetPasswordUiState is ResetPasswordUiState.Error) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.error)
                    .padding(12.dp)
            ) {
                Text(
                    text = (resetPasswordUiState as ResetPasswordUiState.Error).message,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        CustomTextField(
            textFieldState = emailState,
            text = "Email",
            placeholder = "Your email",
            required = true
        )
        CustomTextField(
            textFieldState = recoveryTokenState,
            text = "Recovery Token (from email)",
            placeholder = "The token",
            required = true
        )
        CustomTextField(
            textFieldState = passwordState,
            text = "New Password",
            placeholder = "Your new password",
            trailingIcon = {
                val image =
                    if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (isPasswordVisible) "Hide password" else "Show password"
                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible }
                ) {
                    Icon(image, description)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            outputTransformation = if (isPasswordVisible) null else PasswordOutputTransformation(),
            required = true
        )
        CustomTextField(
            textFieldState = confirmPasswordState,
            text = "Confirm Password",
            placeholder = "Confirm Password",
            trailingIcon = {
                val image =
                    if (isConfirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (isConfirmPasswordVisible) "Hide password" else "Show password"
                IconButton(
                    onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
                ) {
                    Icon(image, description)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            outputTransformation = if (isConfirmPasswordVisible) null else PasswordOutputTransformation(),
            focusedContainerColor = if (isPasswordMatching) {
                MaterialTheme.colorScheme.surfaceContainerLow
            } else {
                MaterialTheme.colorScheme.errorContainer
            },
            required = true
        )
        if (!isPasswordMatching) {
            Text(
                text = "Please make sure your passwords match.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = modifier.padding(horizontal = 16.dp).align(Alignment.Start)
            )
        }
        if (resetPasswordUiState is ResetPasswordUiState.Loading) {
            CircularProgressIndicator()
        } else {
            CustomButton(
                text = "Reset password",
                onClick = {
                    resetPasswordViewModel.onResetPasswordClicked(
                        email = emailState.text.toString().trim(),
                        token = recoveryTokenState.text.toString().trim(),
                        password = passwordState.text.toString()
                    )
                },
                enabled = isPasswordMatching,
                modifier = Modifier
                    .align(Alignment.Start)
                    .wrapContentWidth()
                    .padding(16.dp)
            )
            Text(
                text = "Need a password recovery token?",
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                color = extendedLight.forgotPassword.colorContainer,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 16.dp)
                    .clickable { onForgotPasswordClick() }
            )
        }
    }
}