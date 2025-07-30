package com.okabe.clearcents.feature_expenses.presentation.categoty_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okabe.clearcents.feature_expenses.domain.repository.CategoryExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CategoryDetailViewModel(
    private val categoryId: Long,
    private val categoryExpenseRepository: CategoryExpenseRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryDetailState())
    val state = _state
        .onStart {
            val newState =
                categoryExpenseRepository.getCategoryWithExpenses(categoryId)?.let { category ->
                    CategoryDetailState(
                        name = category.name,
                        monthlyBudget = category.monthlyBudget,
                        totalSpent = category.expenses.sumOf { expense -> expense.amount },
                        expenses = category.expenses
                    )
                } ?: CategoryDetailState()

            _state.update { newState }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CategoryDetailState()
        )

    fun onAction(action: CategoryDetailAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }
}