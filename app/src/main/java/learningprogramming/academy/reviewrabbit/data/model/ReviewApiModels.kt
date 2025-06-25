package learningprogramming.academy.reviewrabbit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PostReviewRequest(
    val companyId: Int,
    val management: Int,
    val culture: Int,
    val salary: Int,
    val benefits: Int,
    val wouldRecommend: Int,
    val review: String,
)

@Serializable
data class ReviewResponse(
    val id: Long,
    val companyId: Long,
    val userId: Long,
    val management: Int,
    val culture: Int,
    val salary: Int,
    val benefits: Int,
    val wouldRecommend: Int,
    val review: String,
    val created: String,
    val updated: String
)

@Serializable
data class ReviewSummaryResponse(
    val companyId: Int,
    val contents: String,
    val created: String
)
