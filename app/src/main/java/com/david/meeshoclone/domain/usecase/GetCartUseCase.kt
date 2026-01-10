package com.david.meeshoclone.domain.usecase

import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.CartDataModels
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(private val repo: Repo) {

    fun getCart(): Flow<ResultState<List<CartDataModels>>> {
        return repo.getCart()
    }
}