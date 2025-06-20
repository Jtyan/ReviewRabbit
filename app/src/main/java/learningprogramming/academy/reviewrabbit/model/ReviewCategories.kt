package learningprogramming.academy.reviewrabbit.model

enum class ReviewCategories(val id: String, val label: String) {
    WOULD_RECOMMEND(id = "wouldRecommend", label = "Would Recommend"),
    MANAGEMENT(id = "management", label = "Management"),
    CULTURE(id = "culture", label = "Culture"),
    SALARY(id = "salary", label = "Salary"),
    BENEFITS(id = "benefits", label = "Benefits")
}

data class ReviewCategoriesAndStars(
    val label: String,
    val stars: Int
)

