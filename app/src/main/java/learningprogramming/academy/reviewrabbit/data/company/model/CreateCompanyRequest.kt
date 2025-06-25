package learningprogramming.academy.reviewrabbit.data.company.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateCompanyRequest(
    val name: String,
    val url: String,
    val location: String?,
    val country: String?,
    val industry: String?,
    val image: String?,
    val tags: List<String>?
)