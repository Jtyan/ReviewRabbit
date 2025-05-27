package learningprogramming.academy.reviewrabbit.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import learningprogramming.academy.reviewrabbit.data.model.ReviewSummaryApiResponse
import learningprogramming.academy.reviewrabbit.data.repository.ReviewsRepository
import javax.inject.Inject

@HiltViewModel
class CompanyReviewViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {
    private val initialReviewSummary = ReviewSummaryApiResponse(
        companyId = 0,
        contents = "No generated review summary yet.",
        created = ""
    )
    private val _reviewSummary = MutableStateFlow(initialReviewSummary)
    val reviewSummary: StateFlow<ReviewSummaryApiResponse> = _reviewSummary.asStateFlow()

    fun getReviewSummary(companyId: Long) {
        viewModelScope.launch {
            try {
                _reviewSummary.value = reviewsRepository.getReviewSummaryByCompanyId(companyId)
            } catch (e: Exception) {
                Log.e(
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
}