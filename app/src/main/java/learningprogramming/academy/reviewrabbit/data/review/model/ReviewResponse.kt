package learningprogramming.academy.reviewrabbit.data.review.model

import kotlinx.serialization.Serializable

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