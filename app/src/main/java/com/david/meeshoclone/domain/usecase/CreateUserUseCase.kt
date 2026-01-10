package com.david.meeshoclone.domain.usecase

import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.ProductsDataModels
import com.david.meeshoclone.domain.models.UserData
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repo: Repo) {

    fun createUser(userData: UserData): Flow<ResultState<String>> {
        return repo.registerUserWithEmailAndPassword(userData)
    }
}