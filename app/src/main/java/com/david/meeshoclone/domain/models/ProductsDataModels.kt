package com.david.meeshoclone.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductsDataModels(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val finalPrice: String = "",
    val category: String = "",
    val image: String = "",
    val createBy: String = "",
    val availableUnits: Int = 0,
    var productId: String = ""
)
