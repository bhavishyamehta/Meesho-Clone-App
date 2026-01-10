package com.david.meeshoclone.domain.models

data class CartDataModels(
    val productId : String = "",
    val name : String = "",
    val image : String = "",
    val price : String = "",
    val quantity : String = "",
    var cartId : String = "",
    val size : String = "",
    val description : String = "",
    val category : String = "",
)
