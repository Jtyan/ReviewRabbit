package learningprogramming.academy.reviewrabbit.viewmodels

import android.webkit.URLUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import learningprogramming.academy.reviewrabbit.data.model.PostCompanyApi
import learningprogramming.academy.reviewrabbit.data.repository.CompanyApiResult
import learningprogramming.academy.reviewrabbit.data.repository.CompanyRepository
import javax.inject.Inject

@HiltViewModel
class PostCompanyViewModel @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel() {
    private val _postCompanyUiState = MutableStateFlow<PostCompanyUiState>(PostCompanyUiState.Idle)
    val postCompanyUiState: StateFlow<PostCompanyUiState> = _postCompanyUiState.asStateFlow()

    fun addNewCompany(
        name: String,
        companyUrl: String,
        image: String?,
        location: String?,
        country: String?,
        industry: String?,
        companyTags: String?
    ) {
        viewModelScope.launch {
            _postCompanyUiState.value = PostCompanyUiState.Loading
            if (name.isEmpty()) {
                _postCompanyUiState.value =
                    PostCompanyUiState.Error("Company name must not be empty.")
                return@launch
            }

            if (companyUrl.isEmpty() || (!URLUtil.isHttpUrl(companyUrl) && !URLUtil.isHttpsUrl(
                    companyUrl
                ) &&
                        !companyUrl.startsWith("www.", true))
            ) {
                _postCompanyUiState.value = PostCompanyUiState.Error("Company Url is invalid.")
                return@launch
            }

            val tags =
                if (!companyTags.isNullOrEmpty()) companyTags.splitToSequence(",").map { it.trim() }
                    .filter { it.isNotEmpty() }.toList() else emptyList()

            val postCompanyApi = PostCompanyApi(
                name = name,
                url = companyUrl,
                location = location,
                country = country,
                industry = industry,
                image = image,
                tags = tags
            )
            when (val response = companyRepository.postNewCompany(postCompanyApi)) {
                is CompanyApiResult.PostCompanySuccess -> {
                    _postCompanyUiState.value =
                        PostCompanyUiState.Success("Company $name is added successfully.")
                }

                is CompanyApiResult.Error -> {
                    _postCompanyUiState.value =
                        PostCompanyUiState.Error("Failed to add company $name. ${response.message}")
                }

                is CompanyApiResult.NetworkError -> {
                    _postCompanyUiState.value =
                        PostCompanyUiState.Error("Failed to add company $name. Network error has occurred.")
                }

                is CompanyApiResult.UnknownError -> {
                    _postCompanyUiState.value =
                        PostCompanyUiState.Error("Unknown error has occurred. ${response.exception}")
                }
            }
        }
    }

    fun resetStateToIdle() {
        _postCompanyUiState.value = PostCompanyUiState.Idle
    }
}

sealed interface PostCompanyUiState {
    data object Idle : PostCompanyUiState
    data object Loading : PostCompanyUiState
    data class Success(val message: String) : PostCompanyUiState
    data class Error(val message: String) : PostCompanyUiState
}