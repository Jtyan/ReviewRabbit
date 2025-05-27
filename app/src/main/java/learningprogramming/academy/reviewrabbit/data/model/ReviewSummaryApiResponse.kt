package learningprogramming.academy.reviewrabbit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewSummaryApiResponse(
    val companyId: Int,
    val contents: String,
    val created: String
)
