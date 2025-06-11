package com.okabe.clearcents.feature_expenses.presentation.create_category

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okabe.clearcents.feature_expenses.data.dao.CategoryDao
import com.okabe.clearcents.feature_expenses.data.entity.CategoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateCategoryViewModel(
    private val categoryDao: CategoryDao
) : ViewModel() {

    private val _state = MutableStateFlow(CreateCategoryInternalState())
    val state = _state.map {
        CreateCategoryState(
            name = it.name,
            budget = if (it.budgetValue == null) "" else it.budgetValue.toString(),
            readyToSave = it.readyToSave
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CreateCategoryState()
    )

    fun onAction(action: CreateCategoryAction) {
        when (action) {
            is CreateCategoryAction.OnBudgetChange -> {
                if (action.budget.isBlank()) {
                    _state.update {
                        it.copy(
                            budgetValue = null,
                            readyToSave = it.name.isNotBlank()
                        )
                    }
                    return
                }

                if (!action.budget.isDigitsOnly()) return

                val doubleValue = action.budget.toLongOrNull()
                if (doubleValue != null) {
                    _state.update {
                        it.copy(
                            budgetValue = doubleValue,
                            readyToSave = it.name.isNotBlank()
                        )
                    }
                    return
                }

            }

            is CreateCategoryAction.OnNameChange -> _state.update {
                it.copy(
                    name = action.name,
                    readyToSave = action.name.isNotBlank()
                )
            }

            CreateCategoryAction.OnSave -> {
                viewModelScope.launch {
                    val category = _state.value
                    categoryDao.insertCategory(
                        CategoryEntity(
                            name = category.name,
                            monthlyBudget = category.budgetValue
                        )
                    )

                    _state.update { CreateCategoryInternalState() }
                }
            }

            else -> Unit
        }
    }


    data class CreateCategoryInternalState(
        val name: String = "",
        val budget: String? = "",
        val budgetValue: Long? = null,
        val readyToSave: Boolean = false
    )
}