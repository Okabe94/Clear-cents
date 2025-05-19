package com.okabe.clearcents.feature_expenses.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.Date

data class Expense(
    val id: String = java.util.UUID.randomUUID().toString(),
    val amount: Double,
    val date: Date,
    val description: String?,
    val categoryId: String
)

data class ExpenseCategory(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val monthlyBudget: Double,
    val iconName: String // Using string to map to Material Icons
)

object CategoryIcons {
    val Default = Icons.Filled.ShoppingCart // Default icon
    val Groceries = Icons.Filled.ShoppingCart
    val Transport = Icons.Filled.LocationOn
    val Entertainment = Icons.Filled.Face
    val Home = Icons.Filled.Home

    fun getIconByName(name: String): ImageVector {
        return when (name) {
            "Groceries" -> Groceries
            "Transport" -> Transport
            "Entertainment" -> Entertainment
            "Home" -> Home
            else -> Default
        }
    }
    val AllIcons = mapOf(
        "Groceries" to Groceries,
        "Transport" to Transport,
        "Entertainment" to Entertainment,
        "Home" to Home,
        "Default" to Default
    )
}