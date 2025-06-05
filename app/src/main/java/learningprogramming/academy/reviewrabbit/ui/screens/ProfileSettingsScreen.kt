package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomTextField
import learningprogramming.academy.reviewrabbit.ui.theme.ReviewRabbitTheme
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight

@Composable
fun ProfileSettingsScreen(
    modifier: Modifier = Modifier,
    onChangePasswordClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 48.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Profile",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Account Setting",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp)
        )
        Text(
            text = "Change Password",
            fontSize = 14.sp,
            color = extendedLight.forgotPassword.colorContainer,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clickable { onChangePasswordClick() }
        )
        InvitesSection(
            numOfInvites = 9
        )
        CustomButton(
            text = "Invite",
            onClick = {},
            modifier = modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun InvitesSection(
    modifier: Modifier = Modifier,
    numOfInvites: Int
) {
    val inviteTextState = rememberTextFieldState()

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Invites",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Company Name",
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
        CustomTextField(
            textFieldState = inviteTextState,
            text = "$numOfInvites invites left",
            placeholder = "Enter emails, one per line",
            required = false,
            lineLimits = TextFieldLineLimits.MultiLine(),
            modifier = modifier
                .padding(vertical = 4.dp)
                .height(175.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(2.dp)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileSettingsScreenPreview() {
    ReviewRabbitTheme(dynamicColor = false) {
        ProfileSettingsScreen(
            onChangePasswordClick = {}
        )
    }
}