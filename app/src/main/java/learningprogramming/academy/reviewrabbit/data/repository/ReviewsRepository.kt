package learningprogramming.academy.reviewrabbit.data.repository

import learningprogramming.academy.reviewrabbit.data.model.PostReviewApi
import learningprogramming.academy.reviewrabbit.data.model.ReviewApiResponse
import learningprogramming.academy.reviewrabbit.data.model.ReviewSummaryApiResponse
import learningprogramming.academy.reviewrabbit.data.network.ReviewsApiService
import javax.inject.Inject
import javax.inject.Singleton

interface ReviewsRepository {
    suspend fun getAllReviewsByCompanyId(companyId: Long): List<ReviewApiResponse>
    suspend fun getReviewSummaryByCompanyId(companyId: Long): ReviewSummaryApiResponse
    suspend fun generateReviewSummaryByCompanyId(companyId: Long): ReviewSummaryApiResponse
    suspend fun postAReview(review: PostReviewApi): ReviewApiResponse
}

@Singleton
class ReviewsRepositoryImpl @Inject constructor(private val reviewsApiService: ReviewsApiService): ReviewsRepository {
    override suspend fun getAllReviewsByCompanyId(companyId: Long): List<ReviewApiResponse> {
        return reviewsApiService.getAllReviewsByCompanyId(companyId)
    }

    override suspend fun getReviewSummaryByCompanyId(companyId: Long): ReviewSummaryApiResponse {
        return reviewsApiService.getReviewSummaryByCompanyId(companyId)
    }

    override suspend fun generateReviewSummaryByCompanyId(companyId: Long): ReviewSummaryApiResponse {
        return reviewsApiService.postReviewSummaryByCompanyId(companyId)
    }

    override suspend fun postAReview(review: PostReviewApi): ReviewApiResponse {
        return reviewsApiService.postAReview(review)
    }
}