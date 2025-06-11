package com.okabe.clearcents.feature_expenses.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0L,
    val name: String,
    val monthlyBudget: Long?
)