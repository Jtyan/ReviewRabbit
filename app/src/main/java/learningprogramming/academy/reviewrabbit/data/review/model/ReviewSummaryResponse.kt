package learningprogramming.academy.reviewrabbit.data.review.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewSummaryResponse(
    val companyId: Int,
    val contents: String,
    val created: String
)