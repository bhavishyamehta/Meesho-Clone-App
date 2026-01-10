package com.david.meeshoclone.domain.usecase

import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.UserData
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repo: Repo) {

    fun loginUser(userData: UserData): Flow<ResultState<String>> {
        return repo.loginUserWithEmailAndPassword(userData)
    }
}