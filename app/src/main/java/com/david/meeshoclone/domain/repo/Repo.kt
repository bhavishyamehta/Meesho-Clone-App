package com.david.meeshoclone.domain.repo

import android.net.Uri
import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.BannerDataModels
import com.david.meeshoclone.domain.models.CartDataModels
import com.david.meeshoclone.domain.models.CategoryDataModels
import com.david.meeshoclone.domain.models.ProductsDataModels
import com.david.meeshoclone.domain.models.UserData
import com.david.meeshoclone.domain.models.UserDataParent
import kotlinx.coroutines.flow.Flow

interface Repo {

    fun registerUserWithEmailAndPassword(
        userData: UserData
    ): Flow<ResultState<String>>

    fun loginUserWithEmailAndPassword(
        userData: UserData
    ): Flow<ResultState<String>>

    fun getUserById(
        uid: String
    ): Flow<ResultState<UserDataParent>>

    fun updateUserData(
        userDataParent: UserDataParent
    ): Flow<ResultState<String>>

    fun userProfileImage(
        uri: Uri
    ): Flow<ResultState<String>>

    fun getCategoriesInLimit(): Flow<ResultState<List<CategoryDataModels>>>

    fun getProductsInLimit(): Flow<ResultState<List<ProductsDataModels>>>

    fun getAllCategories(): Flow<ResultState<List<CategoryDataModels>>>

    fun getAllProducts(): Flow<ResultState<List<ProductsDataModels>>>

    fun getProductById(productId: String): Flow<ResultState<ProductsDataModels>>

    fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>>

    fun addToFav(productsDataModels: ProductsDataModels): Flow<ResultState<String>>

    fun getAllFav(): Flow<ResultState<List<ProductsDataModels>>>

    fun getCart(): Flow<ResultState<List<CartDataModels>>>

    fun getCheckOut(productId: String): Flow<ResultState<ProductsDataModels>>

    fun getBanner(): Flow<ResultState<List<BannerDataModels>>>

    fun getSpecificCategoryItem(categoryName: String): Flow<ResultState<List<ProductsDataModels>>>

    fun getAllSuggestedProducts(): Flow<ResultState<List<ProductsDataModels>>>

}