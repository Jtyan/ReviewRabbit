package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomTextField
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.util.PasswordChecker
import learningprogramming.academy.reviewrabbit.util.PasswordOutputTransformation
import learningprogramming.academy.reviewrabbit.viewmodels.ChangePasswordUiState
import learningprogramming.academy.reviewrabbit.viewmodels.ChangePasswordViewModel

@Composable
fun ChangePasswordScreen(
    modifier: Modifier = Modifier,
    changePasswordViewModel: ChangePasswordViewModel,
    onPasswordChangeSuccessNavigation: () -> Unit
) {
    var isCurrentPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isNewPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordVisible by rememberSaveable { mutableStateOf(false) }


    val currentPasswordState = rememberTextFieldState()
    val newPasswordState = rememberTextFieldState()
    val confirmPasswordState = rememberTextFieldState()

    val changePasswordUiState by changePasswordViewModel.changePasswordUiState.collectAsState()

    val isPasswordMatching: Boolean = PasswordChecker.checkIfPasswordsMatch(
        newPasswordState.text.toString(), confirmPasswordState.text.toString()
    )

    DisposableEffect(Unit) {
        onDispose {
            changePasswordViewModel.resetStateToIdle()
        }
    }

    LaunchedEffect(changePasswordUiState) {
        if (changePasswordUiState is ChangePasswordUiState.Success) {
            delay(2000L)
            onPasswordChangeSuccessNavigation()
            changePasswordViewModel.resetStateToIdle()
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
            text = "Change Password",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (changePasswordUiState is ChangePasswordUiState.Error) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.error)
                    .padding(12.dp)
            ) {
                Text(
                    text = (changePasswordUiState as ChangePasswordUiState.Error).message,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        } else if (changePasswordUiState is ChangePasswordUiState.Success) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = extendedLight.forgotPassword.color)
                    .padding(12.dp)
            ) {
                Text(
                    text = (changePasswordUiState as ChangePasswordUiState.Success).message,
                    fontSize = 12.sp,
                    color = extendedLight.forgotPassword.color,
                )
            }
        }
        CustomTextField(
            textFieldState = currentPasswordState,
            text = "Current Password",
            placeholder = "Your current password",
            trailingIcon = {
                val image =
                    if (isCurrentPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (isCurrentPasswordVisible) "Hide password" else "Show password"
                IconButton(
                    onClick = { isCurrentPasswordVisible = !isCurrentPasswordVisible }
                ) {
                    Icon(image, description)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            outputTransformation = if (isCurrentPasswordVisible) null else PasswordOutputTransformation(),
            required = true,
            modifier = Modifier.padding(16.dp)
        )
        CustomTextField(
            textFieldState = newPasswordState,
            text = "New Password",
            placeholder = "Your new password",
            trailingIcon = {
                val image =
                    if (isNewPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (isNewPasswordVisible) "Hide password" else "Show password"
                IconButton(
                    onClick = { isNewPasswordVisible = !isNewPasswordVisible }
                ) {
                    Icon(image, description)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            outputTransformation = if (isNewPasswordVisible) null else PasswordOutputTransformation(),
            required = true,
            modifier = Modifier.padding(16.dp)
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
            required = true,
            modifier = Modifier.padding(16.dp)
        )
        if (!isPasswordMatching) {
            Text(
                text = "Please make sure your passwords match.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = modifier.padding(horizontal = 16.dp).align(Alignment.Start)
            )
        }
        if (changePasswordUiState is ChangePasswordUiState.Loading) {
            CircularProgressIndicator()
        } else {
            CustomButton(
                text = "Change password",
                onClick = {
                    changePasswordViewModel.onChangePasswordClicked(
                        oldPassword = currentPasswordState.text.toString().trim(),
                        newPassword = newPasswordState.text.toString().trim()
                    )
                },
                enabled = isPasswordMatching,
                modifier = Modifier
                    .align(Alignment.Start)
                    .wrapContentWidth()
                    .padding(16.dp)
            )
        }
    }
}