package com.okabe.clearcents.feature_expenses.presentation.create_category

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CreateCategoryViewModel : ViewModel() {

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

            CreateCategoryAction.OnGoBack -> TODO()
            is CreateCategoryAction.OnNameChange -> TODO()
            CreateCategoryAction.OnSave -> TODO()
        }
    }


    data class CreateCategoryInternalState(
        val name: String = "",
        val budget: String? = "",
        val budgetValue: Long? = null,
        val readyToSave: Boolean = false
    )
}