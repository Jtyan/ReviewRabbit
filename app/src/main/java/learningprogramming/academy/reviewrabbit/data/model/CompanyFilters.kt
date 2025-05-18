package learningprogramming.academy.reviewrabbit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CompanyFilters(
    val locations: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
    val industries: List<String> = emptyList(),
    val tags: List<String> = emptyList()
)
