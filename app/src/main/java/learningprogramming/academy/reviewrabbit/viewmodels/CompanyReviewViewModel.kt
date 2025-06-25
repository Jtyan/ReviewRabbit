package learningprogramming.academy.reviewrabbit.viewmodels

import android.util.Log
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import learningprogramming.academy.reviewrabbit.data.company.model.CompanyResponse
import learningprogramming.academy.reviewrabbit.data.review.model.CreateReviewRequest
import learningprogramming.academy.reviewrabbit.data.review.model.ReviewResponse
import learningprogramming.academy.reviewrabbit.data.review.model.ReviewSummaryResponse
import learningprogramming.academy.reviewrabbit.data.company.CompanyRepository
import learningprogramming.academy.reviewrabbit.data.review.ReviewsRepository
import learningprogramming.academy.reviewrabbit.data.session.SessionManager
import learningprogramming.academy.reviewrabbit.model.ReviewCategories
import javax.inject.Inject

@HiltViewModel
class CompanyReviewViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
    private val companyRepository: CompanyRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val initialReviewSummary = ReviewSummaryResponse(
        companyId = 0, contents = "No generated review summary yet.", created = ""
    )
    private val initialNewReview = CreateReviewRequest(
        companyId = 0,
        management = 0,
        culture = 0,
        salary = 0,
        benefits = 0,
        wouldRecommend = 0,
        review = ""
    )

    private val _selectedCompany = MutableStateFlow<CompanyResponse?>(null)
    val selectedCompany: StateFlow<CompanyResponse?> = _selectedCompany.asStateFlow()
    private val _reviewSummary = MutableStateFlow(initialReviewSummary)
    val reviewSummary: StateFlow<ReviewSummaryResponse> = _reviewSummary.asStateFlow()
    private val _listOfReviews = MutableStateFlow<List<ReviewResponse>>(emptyList())
    val listOfReviews: StateFlow<List<ReviewResponse>> = _listOfReviews.asStateFlow()
    private val _newReview = MutableStateFlow(initialNewReview)
    val newReview: StateFlow<CreateReviewRequest> = _newReview.asStateFlow()
    private val _reviewUiState = MutableStateFlow<ReviewUiState>(ReviewUiState.Idle)
    val reviewUiState: StateFlow<ReviewUiState> = _reviewUiState.asStateFlow()
    private val _userId = MutableStateFlow<Long?>(null)
    val userId: StateFlow<Long?> = _userId.asStateFlow()
    private val _hasUserPostedReview = MutableStateFlow<Boolean>(false)
    val hasUserPostedReview: StateFlow<Boolean> = _hasUserPostedReview.asStateFlow()

    init {
        viewModelScope.launch {
            _userId.value = sessionManager.getUserId()
        }
    }

    fun getCompanyById(companyId: Int) {
        viewModelScope.launch {
            try {
                _selectedCompany.value = companyRepository.getCompanyById(companyId)
            } catch (e: Exception) {
                Log.e("ReviewRabbitViewModel", "Error fetching company by $companyId. $e")
            }
        }
    }

    fun getReviewSummary(companyId: Long) {
        viewModelScope.launch {
            try {
                _reviewSummary.value = reviewsRepository.getReviewSummaryByCompanyId(companyId)
            } catch (e: Exception) {
                Log.w(
                    "CompanyReviewViewModel",
                    "Error getting Review Summary for company id = $companyId. $e"
                )
                _reviewSummary.value = initialReviewSummary
            }
        }
    }

    fun generatedReviewSummary(companyId: Long) {
        viewModelScope.launch {
            try {
                _reviewSummary.value = reviewsRepository.generateReviewSummaryByCompanyId(companyId)
            } catch (e: Exception) {
                Log.e(
                    "CompanyReviewViewModel",
                    "Error generating a review Summary for company id = $companyId. $e"
                )
            }
        }
    }

    fun displayReviews(companyId: Long) {
        viewModelScope.launch {
            try {
                _listOfReviews.value = reviewsRepository.getAllReviewsByCompanyId(companyId)
                checkIfUserPostedAReview()
            } catch (e: Exception) {
                Log.e(
                    "CompanyReviewViewModel",
                    "Error getting the list of reviews for company id = $companyId. $e"
                )
            }
        }
    }

    private fun checkIfUserPostedAReview() {
        _hasUserPostedReview.value =
            _listOfReviews.value.find { it.userId == _userId.value } != null
    }

    fun submitReview() {
        viewModelScope.launch {
            _reviewUiState.value = ReviewUiState.Loading
            val review = _newReview.value
            Log.i("CompanyReviewViewModel", review.toString())
            try {
                if (review.companyId != _selectedCompany.value?.id) {
                    Log.e("CompanyReviewViewModel", "Company id ${review.companyId} is invalid")
                    _reviewUiState.value =
                        ReviewUiState.Error("Company id ${review.companyId} is invalid.")
                    return@launch
                }

                if (review.wouldRecommend == 0 || review.culture == 0 || review.benefits == 0 || review.management == 0 || review.salary == 0 || review.review.isEmpty()) {
                    Log.e("CompanyReviewViewModel", "ReviewCategories ratings are not set")
                    _reviewUiState.value =
                        ReviewUiState.Error("Please complete the ratings before submitting.")
                    return@launch
                }

                reviewsRepository.postAReview(review)
                _reviewUiState.value = ReviewUiState.Success("Review added successfully.")
            } catch (e: IOException) {
                Log.e(
                    "CompanyReviewViewModel",
                    "Failed to add a review. Network error has occurred."
                )
                _reviewUiState.value =
                    ReviewUiState.Error("Failed to add a review. Network error has occurred.")
            } catch (e: Exception) {
                Log.e("CompanyReviewViewModel", "Error posting a review. $e")
                _reviewUiState.value = ReviewUiState.Error("Unable to submit the review.")
            }
        }
    }

    fun updateNewReview(newReview: CreateReviewRequest) {
        _newReview.value = newReview
    }

    fun updateCategoryRating(category: ReviewCategories, rating: Int) {
        val currentReview = _newReview.value
        val updatedReview = when (category) {
            ReviewCategories.WOULD_RECOMMEND -> currentReview.copy(wouldRecommend = rating)
            ReviewCategories.MANAGEMENT -> currentReview.copy(management = rating)
            ReviewCategories.CULTURE -> currentReview.copy(culture = rating)
            ReviewCategories.SALARY -> currentReview.copy(salary = rating)
            ReviewCategories.BENEFITS -> currentReview.copy(benefits = rating)
        }
        _newReview.value = updatedReview
    }

    fun resetReviewUiState() {
        _reviewUiState.value = ReviewUiState.Idle
    }
}

sealed interface ReviewUiState {
    data object Idle : ReviewUiState
    data object Loading : ReviewUiState
    data class Success(val message: String) : ReviewUiState
    data class Error(val message: String) : ReviewUiState
}