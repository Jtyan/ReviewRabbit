package learningprogramming.academy.reviewrabbit.data.repository

import learningprogramming.academy.reviewrabbit.data.model.PostReviewRequest
import learningprogramming.academy.reviewrabbit.data.model.ReviewResponse
import learningprogramming.academy.reviewrabbit.data.model.ReviewSummaryResponse
import learningprogramming.academy.reviewrabbit.data.network.ReviewsApiService
import javax.inject.Inject
import javax.inject.Singleton

interface ReviewsRepository {
    suspend fun getAllReviewsByCompanyId(companyId: Long): List<ReviewResponse>
    suspend fun getReviewSummaryByCompanyId(companyId: Long): ReviewSummaryResponse
    suspend fun generateReviewSummaryByCompanyId(companyId: Long): ReviewSummaryResponse
    suspend fun postAReview(postReviewRequest: PostReviewRequest): ReviewResponse
}

@Singleton
class ReviewsRepositoryImpl @Inject constructor(private val reviewsApiService: ReviewsApiService): ReviewsRepository {
    override suspend fun getAllReviewsByCompanyId(companyId: Long): List<ReviewResponse> {
        return reviewsApiService.getAllReviewsByCompanyId(companyId)
    }

    override suspend fun getReviewSummaryByCompanyId(companyId: Long): ReviewSummaryResponse {
        return reviewsApiService.getReviewSummaryByCompanyId(companyId)
    }

    override suspend fun generateReviewSummaryByCompanyId(companyId: Long): ReviewSummaryResponse {
        return reviewsApiService.postReviewSummaryByCompanyId(companyId)
    }

    override suspend fun postAReview(postReviewRequest: PostReviewRequest): ReviewResponse {
        return reviewsApiService.postAReview(postReviewRequest)
    }
}