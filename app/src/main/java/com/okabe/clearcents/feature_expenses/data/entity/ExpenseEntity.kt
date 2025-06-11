package com.okabe.clearcents.feature_expenses.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryIdForeign"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryIdForeign"])]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long = 0L,
    val categoryIdForeign: Long,
    val amount: Long,
    val date: Date,
    val description: String? = null
)