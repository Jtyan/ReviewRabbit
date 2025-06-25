package learningprogramming.academy.reviewrabbit.data.review.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateReviewRequest(
    val companyId: Int,
    val management: Int,
    val culture: Int,
    val salary: Int,
    val benefits: Int,
    val wouldRecommend: Int,
    val review: String,
)


