package com.okabe.clearcents.feature_expenses.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okabe.clearcents.feature_expenses.data.entity.CategoryExpenseEntity
import com.okabe.clearcents.feature_expenses.data.entity.ExpenseEntity
import com.okabe.clearcents.feature_expenses.data.repository.CategoryExpenseRepository
import com.okabe.clearcents.feature_expenses.data.repository.CategoryRepository
import com.okabe.clearcents.feature_expenses.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class DashboardViewModel(
    private val categoryExpenseRepository: CategoryExpenseRepository,
    private val categoryRepository: CategoryRepository,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    val state = categoryExpenseRepository.getAllCategoriesWithExpenses()
        .map { entities -> entities.mapToState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = DashboardState()
        )

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.OnCategoryDetail -> {
                viewModelScope.launch {
                    expenseRepository.insertExpense(
                        ExpenseEntity(
                            categoryIdForeign = action.categoryId,
                            amount = 100000L,
                            date = Date(),
                            description = null
                        )
                    )
                }
            }

            is DashboardAction.OnDeleteCategory -> {
                viewModelScope.launch {
                    categoryRepository.getCategoryById(action.categoryId)?.let {
                        categoryRepository.deleteCategory(it)
                    }
                }
            }

            else -> Unit
        }

    }

    private fun List<CategoryExpenseEntity>.mapToState(): DashboardState {
        var totalAmount = 0L
        val categories = this.map {
            val spent = it.expenses
                .map { expense -> expense.amount }
                .reduceOrNull { acc, amount -> acc + amount } ?: 0L

            totalAmount += spent

            DashboardCategory(
                id = it.categoryEntity.categoryId,
                name = it.categoryEntity.name,
                monthlyBudget = it.categoryEntity.monthlyBudget,
                spentAmount = spent
            )
        }

        return DashboardState(totalAmount, categories)
    }
}