package com.okabe.clearcents.feature_expenses.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryExpenseEntity(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryIdForeign"
    )
    val expenses: List<ExpenseEntity>
)
