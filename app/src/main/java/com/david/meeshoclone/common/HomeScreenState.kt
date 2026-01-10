package com.david.meeshoclone.common

import com.david.meeshoclone.domain.models.BannerDataModels
import com.david.meeshoclone.domain.models.CategoryDataModels
import com.david.meeshoclone.domain.models.ProductsDataModels

data class HomeScreenState (
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val categories: List<CategoryDataModels>? = null,
    val products: List<ProductsDataModels>? = null,
    val banner: List<BannerDataModels>? = null,
)