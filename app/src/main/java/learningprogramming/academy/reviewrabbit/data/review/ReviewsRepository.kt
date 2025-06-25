package learningprogramming.academy.reviewrabbit.data.review

import learningprogramming.academy.reviewrabbit.data.review.model.CreateReviewRequest
import learningprogramming.academy.reviewrabbit.data.review.model.ReviewResponse
import learningprogramming.academy.reviewrabbit.data.review.model.ReviewSummaryResponse
import javax.inject.Inject
import javax.inject.Singleton

interface ReviewsRepository {
    suspend fun getAllReviewsByCompanyId(companyId: Long): List<ReviewResponse>
    suspend fun getReviewSummaryByCompanyId(companyId: Long): ReviewSummaryResponse
    suspend fun generateReviewSummaryByCompanyId(companyId: Long): ReviewSummaryResponse
    suspend fun postAReview(createReviewRequest: CreateReviewRequest): ReviewResponse
}

@Singleton
class ReviewsRepositoryImpl @Inject constructor(private val reviewsApiService: ReviewsApiService):
    ReviewsRepository {
    override suspend fun getAllReviewsByCompanyId(companyId: Long): List<ReviewResponse> {
        return reviewsApiService.getAllReviewsByCompanyId(companyId)
    }

    override suspend fun getReviewSummaryByCompanyId(companyId: Long): ReviewSummaryResponse {
        return reviewsApiService.getReviewSummaryByCompanyId(companyId)
    }

    override suspend fun generateReviewSummaryByCompanyId(companyId: Long): ReviewSummaryResponse {
        return reviewsApiService.postReviewSummaryByCompanyId(companyId)
    }

    override suspend fun postAReview(createReviewRequest: CreateReviewRequest): ReviewResponse {
        return reviewsApiService.postAReview(createReviewRequest)
    }
}