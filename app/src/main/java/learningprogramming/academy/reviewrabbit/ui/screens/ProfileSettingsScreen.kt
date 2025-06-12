package learningprogramming.academy.reviewrabbit.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import learningprogramming.academy.reviewrabbit.data.model.GetInvitesApiResponse
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomTextField
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.viewmodels.InviteUiState
import learningprogramming.academy.reviewrabbit.viewmodels.InviteViewModel

@Composable
fun ProfileSettingsScreen(
    inviteViewModel: InviteViewModel,
    modifier: Modifier = Modifier,
    onChangePasswordClick: () -> Unit
) {
    val getInviteUiState = inviteViewModel.getInvites.collectAsState().value
    var listOfCompanies by remember { mutableStateOf<List<GetInvitesApiResponse>>(emptyList()) }

    LaunchedEffect(Unit) {
        inviteViewModel.getInvites()
    }

    LaunchedEffect(getInviteUiState) {
        if (getInviteUiState is InviteUiState.GetInvitesSuccess) {
            listOfCompanies = getInviteUiState.companyInvites.filterNotNull()
        }
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 48.dp)
            .pointerInput(Unit) {
            detectTapGestures(onTap = {
                inviteViewModel.clearSendInviteResult()
            })
        }
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
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Invites",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        if (getInviteUiState is InviteUiState.GetInvitesSuccess) {
            if (listOfCompanies.isNotEmpty()) {
                LazyColumn {
                    items(listOfCompanies) { company ->
                        InvitesSection(company = company, inviteViewModel = inviteViewModel)
                    }
                }
            }
        } else {
            Text(
                text = "You have no invites available",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun InvitesSection(
    modifier: Modifier = Modifier,
    company: GetInvitesApiResponse,
    inviteViewModel: InviteViewModel
) {
    val inviteTextState = rememberTextFieldState()
    val sentInviteUiState by inviteViewModel.sendInviteResult.collectAsState()

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = company.companyName,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
        CustomTextField(
            textFieldState = inviteTextState,
            text = "${company.nInvites} invites left",
            placeholder = "Enter emails, one per line",
            required = false,
            lineLimits = TextFieldLineLimits.MultiLine(),
            textFieldModifier = modifier
                .height(175.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(2.dp)
                )
        )
        CustomButton(
            text = "Invite",
            onClick = {
                inviteViewModel.sendInvites(
                    companyId = company.companyId,
                    text = inviteTextState.text.toString()
                )
            },
            modifier = Modifier.padding(bottom = 12.dp)
        )
        if (sentInviteUiState.companyId == company.companyId) {
            when (sentInviteUiState.state) {
                is InviteUiState.SendInvitesSuccess -> {
                    Box(
                        modifier = Modifier
                            .border(width = 1.dp, color = extendedLight.forgotPassword.color)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = (sentInviteUiState.state as InviteUiState.SendInvitesSuccess).message,
                            fontSize = 12.sp,
                            color = extendedLight.forgotPassword.color,
                        )
                    }
                }

                is InviteUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .border(width = 1.dp, color = MaterialTheme.colorScheme.error)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = (sentInviteUiState.state as InviteUiState.Error).message,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
                is InviteUiState.Idle -> {}
                is InviteUiState.Loading -> {}
                is InviteUiState.GetInvitesSuccess -> {}
            }
        }
    }
}