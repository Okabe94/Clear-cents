package com.okabe.clearcents.feature_expenses.data.repository

import com.okabe.clearcents.feature_expenses.data.mapper.CategoryMapper
import com.okabe.clearcents.feature_expenses.data.source.CategoryDataSource
import com.okabe.clearcents.feature_expenses.domain.model.CategoryModel
import com.okabe.clearcents.feature_expenses.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val dataSource: CategoryDataSource,
    private val mapper: CategoryMapper
) : CategoryRepository {

    override suspend fun getAllCategories(): List<CategoryModel> {
        return dataSource.getAllCategories().map { mapper.toDomain(it) }
    }

    override suspend fun getCategoryById(id: Long): CategoryModel? {
        return dataSource.getCategoryById(id)?.let { mapper.toDomain(it) }
    }

    override suspend fun insertCategory(categoryModel: CategoryModel) {
        dataSource.insertCategory(mapper.toData(categoryModel))
    }

    override suspend fun deleteCategory(categoryId: Long) {
        dataSource.deleteCategory(categoryId)
    }

    override suspend fun deleteCategory(categoryModel: CategoryModel) {
        dataSource.deleteCategory(mapper.toData(categoryModel))
    }

}