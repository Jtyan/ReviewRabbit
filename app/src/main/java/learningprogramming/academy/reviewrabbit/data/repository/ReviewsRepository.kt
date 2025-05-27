package learningprogramming.academy.reviewrabbit.data.repository

import kotlinx.coroutines.flow.Flow
import learningprogramming.academy.reviewrabbit.data.model.ReviewApiResponse
import learningprogramming.academy.reviewrabbit.data.model.ReviewSummaryApiResponse
import learningprogramming.academy.reviewrabbit.data.network.ReviewsApiService
import javax.inject.Inject
import javax.inject.Singleton

interface ReviewsRepository {
    fun getAllReviewsByCompanyId(companyId: String): Flow<List<ReviewApiResponse>>
    suspend fun getReviewSummaryByCompanyId(companyId: Long): ReviewSummaryApiResponse
    suspend fun generateReviewSummaryByCompanyId(companyId: Long): ReviewSummaryApiResponse
}

@Singleton
class ReviewsRepositoryImpl @Inject constructor(private val reviewsApiService: ReviewsApiService): ReviewsRepository {
    override fun getAllReviewsByCompanyId(companyId: String): Flow<List<ReviewApiResponse>> {
        return reviewsApiService.getAllReviewsByCompanyId(companyId)
    }

    override suspend fun getReviewSummaryByCompanyId(companyId: Long): ReviewSummaryApiResponse {
        return reviewsApiService.getReviewSummaryByCompanyId(companyId)
    }

    override suspend fun generateReviewSummaryByCompanyId(companyId: Long): ReviewSummaryApiResponse {
        return reviewsApiService.postReviewSummaryByCompanyId(companyId)
    }
}