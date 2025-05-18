package learningprogramming.academy.reviewrabbit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CompanyApiResponse(
    val id: Int = 0,
    val name: String,
    val url: String,
    val location: String = "",
    val country: String = "",
    val industry: String = "",
    val tags: List<String> = emptyList()
)
