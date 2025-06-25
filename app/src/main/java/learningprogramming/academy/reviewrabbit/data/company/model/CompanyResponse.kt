package learningprogramming.academy.reviewrabbit.data.company.model

import kotlinx.serialization.Serializable

@Serializable
data class CompanyResponse(
    val id: Int = 0,
    val name: String,
    val url: String,
    val image: String? = null,
    val location: String = "",
    val country: String = "",
    val industry: String = "",
    val tags: List<String> = emptyList()
)


