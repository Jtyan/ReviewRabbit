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
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import learningprogramming.academy.reviewrabbit.data.model.PostUserForgetPasswordApi
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomTextField
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.viewmodels.ForgotPasswordUiState
import learningprogramming.academy.reviewrabbit.viewmodels.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    forgotPasswordViewModel: ForgotPasswordViewModel,
    onClick: () -> Unit
) {

    val emailState = rememberTextFieldState()

    val forgotPasswordUiState by forgotPasswordViewModel.forgotPasswordUiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            forgotPasswordViewModel.resetForgotPasswordStateToIdle()
        }
    }

    LaunchedEffect(forgotPasswordUiState) {
        if (forgotPasswordUiState is ForgotPasswordUiState.Success) {
            forgotPasswordViewModel.resetForgotPasswordStateToIdle()
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
            text = "Forgot Password",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (forgotPasswordUiState is ForgotPasswordUiState.Error) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.error)
                    .padding(12.dp)
            ) {
                Text(
                    text = (forgotPasswordUiState as ForgotPasswordUiState.Error).message,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        } else if (forgotPasswordUiState is ForgotPasswordUiState.Success) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Green)
                    .padding(12.dp)
            ) {
                Text(
                    text = "A recovery token has been sent to your email!",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        CustomTextField(
            textFieldState = emailState,
            text = "Email",
            placeholder = "Your Email",
            required = true
        )

        if (forgotPasswordUiState is ForgotPasswordUiState.Loading) {
            CircularProgressIndicator()
        } else {
            CustomButton(
                text = "Recover password",
                onClick = {
                    forgotPasswordViewModel.onRecoverPasswordClicked(PostUserForgetPasswordApi(
                        email = emailState.text.toString().trim()
                    ))
                },
                modifier = Modifier
                    .align(Alignment.Start)
                    .width(224.dp)
                    .padding(16.dp)
            )
            Text(
                text = "Have a password recovery token?",
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                color = extendedLight.forgotPassword.colorContainer,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 16.dp)
                    .clickable {}
            )
        }
    }
}