package com.okabe.clearcents.feature_expenses.presentation.create_category

data class CreateCategoryState(
    val name: String = "",
    val budget: String = "",
    val readyToSave: Boolean = false
)