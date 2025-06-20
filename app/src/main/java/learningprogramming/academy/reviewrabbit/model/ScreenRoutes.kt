package learningprogramming.academy.reviewrabbit.model

object ScreenRoutes {
    const val HOME = "home_route"
    const val CREATE_NEW_COMPANY = "new_company_route"
    const val USER_SETTINGS = "settings_route"
    const val COMPANY_PAGE = "company_page/{companyId}"
    const val LOGIN_PAGE = "login_route"
    const val LOGIN_PAGE_AFTER_RESET = "login_route/{reset}"
    const val LOGOUT_PAGE = "logged_out_route"
    const val FORGOT_PASSWORD = "forgot_password_route"
    const val RESET_PASSWORD = "reset_password_route"
    const val CHANGE_PASSWORD = "change_password_route"
    fun companyPageWithArg(companyId: Int) = "company_page/$companyId"
    fun loginPageWithArg(notification: String) = "login_route/$notification"
}