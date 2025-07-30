package com.okabe.clearcents.di

import androidx.room.Room
import com.okabe.clearcents.feature_expenses.data.ExpenseDatabase
import com.okabe.clearcents.feature_expenses.data.entity.CategoryEntity
import com.okabe.clearcents.feature_expenses.data.entity.CategoryExpenseEntity
import com.okabe.clearcents.feature_expenses.data.entity.ExpenseEntity
import com.okabe.clearcents.feature_expenses.data.mapper.CategoryExpenseMapper
import com.okabe.clearcents.feature_expenses.data.mapper.CategoryMapper
import com.okabe.clearcents.feature_expenses.data.mapper.ExpenseMapper
import com.okabe.clearcents.feature_expenses.domain.mapper.Mapper
import com.okabe.clearcents.feature_expenses.data.repository.CategoryExpenseRepositoryImpl
import com.okabe.clearcents.feature_expenses.data.repository.CategoryRepositoryImpl
import com.okabe.clearcents.feature_expenses.data.repository.ExpenseRepositoryImpl
import com.okabe.clearcents.feature_expenses.data.source.CategoryDataSource
import com.okabe.clearcents.feature_expenses.data.source.CategoryExpenseDataSource
import com.okabe.clearcents.feature_expenses.data.source.ExpenseDataSource
import com.okabe.clearcents.feature_expenses.data.source.local.LocalCategoryDataSource
import com.okabe.clearcents.feature_expenses.data.source.local.LocalCategoryExpenseDataSource
import com.okabe.clearcents.feature_expenses.data.source.local.LocalExpenseDataSource
import com.okabe.clearcents.feature_expenses.domain.mapper.ListMapper
import com.okabe.clearcents.feature_expenses.domain.model.CategoryExpenseModel
import com.okabe.clearcents.feature_expenses.domain.model.CategoryModel
import com.okabe.clearcents.feature_expenses.domain.model.ExpenseModel
import com.okabe.clearcents.feature_expenses.domain.repository.CategoryExpenseRepository
import com.okabe.clearcents.feature_expenses.domain.repository.CategoryRepository
import com.okabe.clearcents.feature_expenses.domain.repository.ExpenseRepository
import com.okabe.clearcents.feature_expenses.presentation.categoty_detail.CategoryDetailViewModel
import com.okabe.clearcents.feature_expenses.presentation.create_category.CreateCategoryViewModel
import com.okabe.clearcents.feature_expenses.presentation.create_expense.CreateExpenseViewModel
import com.okabe.clearcents.feature_expenses.presentation.dashboard.DashboardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val DB_NAME = "Expense_Database"

val featureExpenseModule = module {

    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = ExpenseDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    // Provides the DAOs
    single { get<ExpenseDatabase>().expenseDao() }
    single { get<ExpenseDatabase>().categoryDao() }
    single { get<ExpenseDatabase>().categoryExpenseDao() }

    // Provides the DataSources
    singleOf(::LocalExpenseDataSource) {
        bind<ExpenseDataSource>()
    }
    singleOf(::LocalCategoryDataSource) {
        bind<CategoryDataSource>()
    }
    singleOf(::LocalCategoryExpenseDataSource) {
        bind<CategoryExpenseDataSource>()
    }

    // Provides the mappers
    singleOf(::ExpenseMapper) {
        bind<ListMapper<ExpenseEntity, ExpenseModel>>()
    }
    singleOf(::CategoryMapper) {
        bind<Mapper<CategoryEntity, CategoryModel>>()
    }
    singleOf(::CategoryExpenseMapper) {
        bind<Mapper<CategoryExpenseEntity, CategoryExpenseModel>>()
    }

    // Provides the Repositories
    singleOf(::ExpenseRepositoryImpl) {
        bind<ExpenseRepository>()
    }
    singleOf(::CategoryRepositoryImpl) {
        bind<CategoryRepository>()
    }
    singleOf(::CategoryExpenseRepositoryImpl) {
        bind<CategoryExpenseRepository>()
    }

    // Provides the ViewModels
    viewModelOf(::DashboardViewModel)
    viewModelOf(::CreateExpenseViewModel)
    viewModelOf(::CreateCategoryViewModel)
    viewModelOf(::CategoryDetailViewModel)
}