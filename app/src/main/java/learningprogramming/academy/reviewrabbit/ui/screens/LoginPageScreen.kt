package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import learningprogramming.academy.reviewrabbit.data.model.PostUserApi
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomTextField
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.util.PasswordOutputTransformation
import learningprogramming.academy.reviewrabbit.viewmodels.LoginScreenUiState
import learningprogramming.academy.reviewrabbit.viewmodels.LoginViewModel

@Composable
fun LoginPageScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    onLoginSuccessNavigation: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    notification: String? = null,
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()

    val loginUiState by loginViewModel.loginUiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            loginViewModel.resetLoginStateToIdle()
        }
    }

    LaunchedEffect(loginUiState) {
        if (loginUiState is LoginScreenUiState.Success) {
            onLoginSuccessNavigation()
            loginViewModel.resetLoginStateToIdle()
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
            text = "Log In",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (loginUiState is LoginScreenUiState.Error) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.error)
                    .padding(12.dp)
            ) {
                Text(
                    text = (loginUiState as LoginScreenUiState.Error).message,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        if (!notification.isNullOrBlank()) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = extendedLight.forgotPassword.color)
                    .padding(12.dp)
            ) {
                Text(
                    text = notification,
                    fontSize = 12.sp,
                    color = extendedLight.forgotPassword.color,
                )
            }
        }
        CustomTextField(
            textFieldState = emailState,
            text = "Email",
            placeholder = "Your Email",
            required = true,
            modifier = Modifier.padding(16.dp)
        )
        CustomTextField(
            textFieldState = passwordState,
            text = "Password",
            placeholder = "Your Password",
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
            required = true,
            modifier = Modifier.padding(16.dp)
        )
        if (loginUiState is LoginScreenUiState.Loading) {
            CircularProgressIndicator()
        } else {
            CustomButton(
                text = "Log In",
                onClick = {
                    loginViewModel.onLoginClicked(
                        PostUserApi(
                            email = emailState.text.toString().trim(),
                            password = passwordState.text.toString().trim()
                        )
                    )
                },
                modifier = Modifier
                    .align(Alignment.Start)
                    .width(224.dp)
                    .padding(16.dp)
            )
            Text(
                text = "Forgot Password?",
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
