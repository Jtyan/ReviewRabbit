package learningprogramming.academy.reviewrabbit.ui.screens

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import learningprogramming.academy.reviewrabbit.R
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomButton
import learningprogramming.academy.reviewrabbit.ui.components.common.CustomTextField
import learningprogramming.academy.reviewrabbit.ui.theme.extendedLight
import learningprogramming.academy.reviewrabbit.util.Base64Decoder
import learningprogramming.academy.reviewrabbit.viewmodels.PostCompanyUiState
import learningprogramming.academy.reviewrabbit.viewmodels.PostCompanyViewModel


@Composable
fun CreateCompanyScreen(
    modifier: Modifier = Modifier,
    postCompanyViewModel: PostCompanyViewModel
) {
    val postCompanyUiState by postCompanyViewModel.postCompanyUiState.collectAsState()

    val companyNameState = rememberTextFieldState()
    val companyUrlState = rememberTextFieldState()
    val locationState = rememberTextFieldState()
    val countryState = rememberTextFieldState()
    val industryState = rememberTextFieldState()
    val tagsState = rememberTextFieldState()

    var selectedImage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImage = Base64Decoder.uriToBase64(context, uri)
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            postCompanyViewModel.resetStateToIdle()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 48.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Post New Company",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (postCompanyUiState is PostCompanyUiState.Error) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.error)
                    .padding(12.dp)
            ) {
                Text(
                    text = (postCompanyUiState as PostCompanyUiState.Error).message,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        } else if (postCompanyUiState is PostCompanyUiState.Success) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = extendedLight.forgotPassword.color)
                    .padding(12.dp)
            ) {
                Text(
                    text = (postCompanyUiState as PostCompanyUiState.Success).message,
                    fontSize = 12.sp,
                    color = extendedLight.forgotPassword.color,
                )
            }
        }
        CustomTextField(
            textFieldState = companyNameState,
            text = "Company Name",
            placeholder = "ACME Inc",
            required = true,
            modifier = Modifier.padding(16.dp)
        )
        CustomTextField(
            textFieldState = companyUrlState,
            text = "Company URL",
            placeholder = "https://www.example.com",
            required = true,
            modifier = Modifier.padding(16.dp)
        )
        ImageSelectorField(
            selectedImageUri = selectedImage,
            onDelete = {
                selectedImage = null
            },
            launcher = launcher
        )
        CustomTextField(
            textFieldState = locationState,
            text = "Location",
            placeholder = "San Francisco",
            required = false,
            modifier = Modifier.padding(16.dp)
        )
        CustomTextField(
            textFieldState = countryState,
            text = "Country",
            placeholder = "USA",
            required = false,
            modifier = Modifier.padding(16.dp)
        )
        CustomTextField(
            textFieldState = industryState,
            text = "Industry",
            placeholder = "Tech",
            required = false,
            modifier = Modifier.padding(16.dp)
        )
        CustomTextField(
            textFieldState = tagsState,
            text = "Tags - separated by ','",
            placeholder = "Kotlin, Saas",
            required = false,
            modifier = Modifier.padding(16.dp)
        )
        if (postCompanyUiState is PostCompanyUiState.Loading) {
            CircularProgressIndicator()
        } else {
            CustomButton(
                text = "Post Company",
                onClick = {
                    postCompanyViewModel.addNewCompany(
                        name = companyNameState.text.toString().trim(),
                        companyUrl = companyUrlState.text.toString().trim(),
                        image = selectedImage,
                        location = locationState.text.toString().trim(),
                        country = countryState.text.toString().trim(),
                        industry = industryState.text.toString().trim(),
                        companyTags = tagsState.text.toString().trim()
                    )
                },
                modifier = Modifier
                    .align(Alignment.Start)
                    .wrapContentWidth()
                    .padding(16.dp)
            )
        }
    }
}


@Composable
fun ImageSelectorField(
    selectedImageUri: String?,
    launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    onDelete: () -> Unit
) {

    val imageData =
        remember(selectedImageUri) { Base64Decoder.base64ToByteArray(selectedImageUri.toString()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Upload Company Logo"
        )
        CustomButton(
            text = "Choose File",
            onClick = {
                launcher.launch(
                    PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        )
        if (selectedImageUri != null) {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageData)
                            .fallback(R.drawable.company_logo_placeholder)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Company Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(120.dp)
                    )
                    IconButton(onClick = {
                        onDelete()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

            }
        }
    }
}