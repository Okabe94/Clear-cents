package com.okabe.clearcents.di

import androidx.room.Room
import com.okabe.clearcents.feature_expenses.data.ExpenseDatabase
import com.okabe.clearcents.feature_expenses.data.mapper.CategoryExpenseMapper
import com.okabe.clearcents.feature_expenses.data.mapper.CategoryMapper
import com.okabe.clearcents.feature_expenses.data.mapper.ExpenseMapper
import com.okabe.clearcents.feature_expenses.data.repository.CategoryExpenseRepositoryImpl
import com.okabe.clearcents.feature_expenses.data.repository.CategoryRepositoryImpl
import com.okabe.clearcents.feature_expenses.data.repository.ExpenseRepositoryImpl
import com.okabe.clearcents.feature_expenses.data.source.CategoryDataSource
import com.okabe.clearcents.feature_expenses.data.source.CategoryExpenseDataSource
import com.okabe.clearcents.feature_expenses.data.source.ExpenseDataSource
import com.okabe.clearcents.feature_expenses.data.source.local.LocalCategoryDataSource
import com.okabe.clearcents.feature_expenses.data.source.local.LocalCategoryExpenseDataSource
import com.okabe.clearcents.feature_expenses.data.source.local.LocalExpenseDataSource
import com.okabe.clearcents.feature_expenses.domain.repository.CategoryExpenseRepository
import com.okabe.clearcents.feature_expenses.domain.repository.CategoryRepository
import com.okabe.clearcents.feature_expenses.domain.repository.ExpenseRepository
import com.okabe.clearcents.feature_expenses.presentation.create_category.CreateCategoryViewModel
import com.okabe.clearcents.feature_expenses.presentation.create_expense.CreateExpenseViewModel
import com.okabe.clearcents.feature_expenses.presentation.dashboard.DashboardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
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

    // Provides the DataSources
    single<ExpenseDataSource> { LocalExpenseDataSource(get()) }
    single<CategoryDataSource> { LocalCategoryDataSource(get()) }
    single<CategoryExpenseDataSource> { LocalCategoryExpenseDataSource(get()) }

    // Provides the mappers
    single { ExpenseMapper() }
    single { CategoryMapper() }
    single { CategoryExpenseMapper() }

    // Provides the Repositories
    single<ExpenseRepository> { ExpenseRepositoryImpl(get(), get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
    single<CategoryExpenseRepository> { CategoryExpenseRepositoryImpl(get(), get()) }

    // Provides the ViewModels
    viewModelOf(::CreateCategoryViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::CreateExpenseViewModel)
}