package com.david.meeshoclone.domain.usecase

import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.UserDataParent
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repo: Repo) {

    fun getUserById(uid: String): Flow<ResultState<UserDataParent>> {
        return repo.getUserById(uid)
    }
}