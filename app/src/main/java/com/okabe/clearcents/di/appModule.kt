package com.okabe.clearcents.di

import androidx.room.Room
import com.okabe.clearcents.feature_expenses.data.ExpenseDatabase
import com.okabe.clearcents.feature_expenses.data.repository.CategoryExpenseRepository
import com.okabe.clearcents.feature_expenses.data.repository.CategoryRepository
import com.okabe.clearcents.feature_expenses.data.repository.ExpenseRepository
import com.okabe.clearcents.feature_expenses.domain.CategoryExpenseRepositoryImpl
import com.okabe.clearcents.feature_expenses.domain.CategoryRepositoryImpl
import com.okabe.clearcents.feature_expenses.domain.ExpenseRepositoryImpl
import com.okabe.clearcents.feature_expenses.presentation.create_category.CreateCategoryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DB_NAME = "Expense_Database"

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ExpenseDatabase::class.java,
            DB_NAME
        ).build()
    }

    // Provides the DAOs
    single { get<ExpenseDatabase>().expenseDao() }
    single { get<ExpenseDatabase>().categoryDao() }
    single { get<ExpenseDatabase>().categoryExpenseDao() }

    // Provides the Repositories
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<CategoryExpenseRepository> { CategoryExpenseRepositoryImpl(get()) }

    viewModel<CreateCategoryViewModel>()
}