package com.david.meeshoclone.domain.usecase

import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.CategoryDataModels
import com.david.meeshoclone.domain.models.ProductsDataModels
import com.david.meeshoclone.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecificCategoryItems @Inject constructor(private val repo: Repo) {

    fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductsDataModels>>> {
        return repo.getSpecificCategoryItem(categoryName)
    }
}