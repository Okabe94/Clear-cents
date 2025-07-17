package com.okabe.clearcents.feature_expenses.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okabe.clearcents.feature_expenses.domain.model.CategoryExpenseModel
import com.okabe.clearcents.feature_expenses.domain.model.ExpenseModel
import com.okabe.clearcents.feature_expenses.domain.repository.CategoryExpenseRepository
import com.okabe.clearcents.feature_expenses.domain.repository.CategoryRepository
import com.okabe.clearcents.feature_expenses.domain.repository.ExpenseRepository
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
                        ExpenseModel(
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

    private fun List<CategoryExpenseModel>.mapToState(): DashboardState {
        var totalAmount = 0L
        val categories = this.map {
            val spent = it.expenses
                .map { expense -> expense.amount }
                .reduceOrNull { acc, amount -> acc + amount } ?: 0L

            totalAmount += spent

            DashboardCategory(
                id = it.categoryId,
                name = it.name,
                monthlyBudget = it.monthlyBudget,
                spentAmount = spent
            )
        }

        return DashboardState(totalAmount, categories)
    }
}