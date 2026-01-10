package com.david.meeshoclone.domain.usecase

import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.CategoryDataModels
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(private val repo: Repo) {

    fun getAllCategoriesUseCase(): Flow<ResultState<List<CategoryDataModels>>> {
        return repo.getAllCategories()
    }
}