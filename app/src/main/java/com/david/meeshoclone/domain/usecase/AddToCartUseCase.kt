package com.david.meeshoclone.domain.usecase

import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.CartDataModels
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val repo: Repo) {

    fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>> {
        return repo.addToCart(cartDataModels)
    }
}