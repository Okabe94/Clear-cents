package com.okabe.clearcents.feature_expenses.domain

import com.okabe.clearcents.feature_expenses.data.dao.CategoryDao
import com.okabe.clearcents.feature_expenses.data.entity.CategoryEntity
import com.okabe.clearcents.feature_expenses.data.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override suspend fun getAllCategories() = categoryDao.getAllCategories()
    override suspend fun getCategoryById(id: Long) = categoryDao.getCategoryById(id)
    override suspend fun insertCategory(categoryEntity: CategoryEntity) =
        categoryDao.insertCategory(categoryEntity)

    override suspend fun deleteCategory(categoryEntity: CategoryEntity) =
        categoryDao.deleteCategory(categoryEntity)

}