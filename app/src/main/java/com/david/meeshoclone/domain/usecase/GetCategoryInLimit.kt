package com.david.meeshoclone.domain.usecase

import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.CategoryDataModels
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryInLimit @Inject constructor(private val repo: Repo) {

    fun getCategoryInLimited(): Flow<ResultState<List<CategoryDataModels>>> {
        return repo.getCategoriesInLimit()
    }
}