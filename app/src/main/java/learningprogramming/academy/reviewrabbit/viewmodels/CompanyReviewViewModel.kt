package learningprogramming.academy.reviewrabbit.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.data.model.PostReviewApi
import learningprogramming.academy.reviewrabbit.data.model.ReviewApiResponse
import learningprogramming.academy.reviewrabbit.data.model.ReviewSummaryApiResponse
import learningprogramming.academy.reviewrabbit.data.repository.CompanyRepository
import learningprogramming.academy.reviewrabbit.data.repository.ReviewsRepository
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class CompanyReviewViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
    private val companyRepository: CompanyRepository
) : ViewModel() {
    private val initialReviewSummary = ReviewSummaryApiResponse(
        companyId = 0,
        contents = "No generated review summary yet.",
        created = ""
    )
    private val _selectedCompany = MutableStateFlow<CompanyApiResponse?>(null)
    val selectedCompany: StateFlow<CompanyApiResponse?> = _selectedCompany.asStateFlow()
    private val _reviewSummary = MutableStateFlow(initialReviewSummary)
    val reviewSummary: StateFlow<ReviewSummaryApiResponse> = _reviewSummary.asStateFlow()
    private val _listOfReviews = MutableStateFlow<List<ReviewApiResponse>>(emptyList())
    val listOfReviews: StateFlow<List<ReviewApiResponse>> = _listOfReviews.asStateFlow()

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
            } catch (e: Exception) {
                Log.e(
                    "CompanyReviewViewModel",
                    "Error getting the list of reviews for company id = $companyId. $e"
                )
            }
        }
    }

    fun submitReview(review: PostReviewApi) {
        viewModelScope.launch {
            try {
                reviewsRepository.postAReview(review)
            } catch (e: Exception) {
                Log.e(
                    "CompanyReviewViewModel",
                    "Error posting a review. $e"
                )
            }
        }
    }
}