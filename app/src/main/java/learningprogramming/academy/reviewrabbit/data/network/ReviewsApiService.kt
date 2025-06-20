package learningprogramming.academy.reviewrabbit.data.network

import learningprogramming.academy.reviewrabbit.data.model.PostReviewApi
import learningprogramming.academy.reviewrabbit.data.model.ReviewApiResponse
import learningprogramming.academy.reviewrabbit.data.model.ReviewSummaryApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewsApiService {
    @GET("reviews/company/{id}")
    suspend fun getAllReviewsByCompanyId(@Path("id") companyId: Long): List<ReviewApiResponse>

    @POST("reviews")
    suspend fun postAReview(@Body postReviewApi: PostReviewApi): ReviewApiResponse

    @GET("reviews/company/{id}/summary")
    suspend fun getReviewSummaryByCompanyId(@Path("id") companyId: Long): ReviewSummaryApiResponse

    @POST("reviews/company/{id}/summary")
    suspend fun postReviewSummaryByCompanyId(@Path("id") companyId: Long): ReviewSummaryApiResponse
}