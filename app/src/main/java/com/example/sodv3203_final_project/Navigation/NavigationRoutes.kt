package com.example.sodv3203_final_project.Navigation

object NavigationRoutes {
    const val LoadingPage = "LoadingPage"
    const val LoginPage = "LoginPage"
    const val RegisterPage = "register"
    const val HomePage = "home"
    const val ProductPageWithCategory = "product_page/{categoryName}"
    const val ProductInsight = "productInsight"
    const val Checkout = "checkout"
    const val AddCard = "add_card"

    fun productPageWithCategory(categoryName: String) = "product_page/$categoryName"

    fun productInsightRoute(menuItemId: Int, userId: Int) = "productInsight/$menuItemId/$userId"
}

