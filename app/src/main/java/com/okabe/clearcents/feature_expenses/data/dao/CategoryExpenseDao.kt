package com.okabe.clearcents.feature_expenses.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.okabe.clearcents.feature_expenses.data.entity.CategoryExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryExpenseDao {
    @Transaction
    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    suspend fun getCategoryWithExpenses(categoryId: Long): CategoryExpenseEntity?

    @Transaction
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategoriesWithExpenses(): Flow<List<CategoryExpenseEntity>>

    @Query(
        """
        SELECT SUM(amount)
        FROM expenses
        WHERE categoryIdForeign = :categoryId
          AND strftime('%Y-%m', date / 1000, 'unixepoch') = :yearMonth
    """
    )
    suspend fun getCategorySumForMonth(
        categoryId: Long,
        yearMonth: String
    ): Double?

    @Query(
        """
        SELECT SUM(amount)
        FROM expenses
        WHERE strftime('%Y-%m', date / 1000, 'unixepoch') = :yearMonth
    """
    )
    suspend fun getTotalExpensesForMonth(yearMonth: String): Double?
}