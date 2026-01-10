package com.david.meeshoclone.domain.usecase

import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.ProductsDataModels
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToFavUseCase @Inject constructor(private val repo: Repo) {

    fun addToFav(productsDataModels: ProductsDataModels): Flow<ResultState<String>> {
        return repo.addToFav(productsDataModels)
    }
}