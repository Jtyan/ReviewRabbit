package learningprogramming.academy.reviewrabbit.data.review

import learningprogramming.academy.reviewrabbit.data.review.model.CreateReviewRequest
import learningprogramming.academy.reviewrabbit.data.review.model.ReviewResponse
import learningprogramming.academy.reviewrabbit.data.review.model.ReviewSummaryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewsApiService {
    @GET("reviews/company/{id}")
    suspend fun getAllReviewsByCompanyId(@Path("id") companyId: Long): List<ReviewResponse>

    @POST("reviews")
    suspend fun postAReview(@Body createReviewRequest: CreateReviewRequest): ReviewResponse

    @GET("reviews/company/{id}/summary")
    suspend fun getReviewSummaryByCompanyId(@Path("id") companyId: Long): ReviewSummaryResponse

    @POST("reviews/company/{id}/summary")
    suspend fun postReviewSummaryByCompanyId(@Path("id") companyId: Long): ReviewSummaryResponse
}