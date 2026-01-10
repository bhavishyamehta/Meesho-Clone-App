package com.david.meeshoclone.presentation.navigation

import kotlinx.serialization.Serializable

sealed class SubNavigation {
    @Serializable
    object LoginSignUpScreen : SubNavigation()

    @Serializable
    object MainHomeScreen : SubNavigation()
}

sealed class Routes {
    @Serializable
    object LoginScreen

    @Serializable
    object SignUpScreen

    @Serializable
    object HomeScreen

    @Serializable
    object CategoryScreen

    @Serializable
    object MeeshoMallScreen

    @Serializable
    object MyOrdersScreen

    @Serializable
    object ProfileScreen

    @Serializable
    object WishlistScreen

    @Serializable
    object CartScreen

    @Serializable
    object SearchBarScreen

    @Serializable
    data class CheckOutScreen(
        val productId: String
    )

    @Serializable
    object PayScreen

    @Serializable
    object SeeAllProductsScreen

    @Serializable
    data class EachProductDetailsScreen(
        val productId: String
    )

    @Serializable
    object AllCategoriesScreen

    @Serializable
    data class EachCategoryItemScreen(
        val categoryName: String
    )

}