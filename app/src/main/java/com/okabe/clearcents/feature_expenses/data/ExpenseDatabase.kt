package com.okabe.clearcents.feature_expenses.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.okabe.clearcents.feature_expenses.data.converter.Converters
import com.okabe.clearcents.feature_expenses.data.dao.CategoryDao
import com.okabe.clearcents.feature_expenses.data.dao.CategoryExpenseDao
import com.okabe.clearcents.feature_expenses.data.dao.ExpenseDao
import com.okabe.clearcents.feature_expenses.data.entity.CategoryEntity
import com.okabe.clearcents.feature_expenses.data.entity.ExpenseEntity

@Database(
    entities = [CategoryEntity::class, ExpenseEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
    abstract fun categoryExpenseDao(): CategoryExpenseDao
}
