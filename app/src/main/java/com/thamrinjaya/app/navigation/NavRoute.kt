package com.thamrinjaya.app.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Admin : Screen("admin")
    data object CompanyProfile : Screen("company/{companyId}?tab={tab}") {
        fun createRoute(companyId: String, tab: Int = 0): String {
            return "company/$companyId?tab=$tab"
        }
    }
}
