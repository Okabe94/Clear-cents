package com.okabe.clearcents.feature_expenses.presentation.categoty_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(CategoryDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: CategoryDetailAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }
}