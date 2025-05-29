package learningprogramming.academy.reviewrabbit.model

object ScreenRoutes {
    const val HOME = "home_route"
    const val CREATE_NEW_COMPANY = "new_company_route"
    const val USER_SETTINGS = "settings_route"
    const val COMPANY_PAGE = "company_page/{companyId}"
    const val LOGIN_PAGE = "login_route"
    fun companyPageWithArg(companyId: Int) = "company_page/$companyId"
}