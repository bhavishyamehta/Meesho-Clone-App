package com.david.meeshoclone.domain.usecase

import android.net.Uri
import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileImageUseCase @Inject constructor(private val repo: Repo) {

    fun userProfile(uri: Uri): Flow<ResultState<String>> {
        return repo.userProfileImage(uri)
    }
}