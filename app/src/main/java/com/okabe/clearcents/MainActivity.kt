package com.okabe.clearcents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.okabe.clearcents.feature_expenses.presentation.create_category.CreateCategoryRoot
import com.okabe.clearcents.feature_expenses.presentation.create_category.CreateCategoryViewModel
import com.okabe.clearcents.feature_expenses.presentation.dashboard.DashboardRoot
import com.okabe.clearcents.feature_expenses.presentation.dashboard.DashboardViewModel
import com.okabe.clearcents.feature_expenses.presentation.navigation.Destination
import com.okabe.clearcents.ui.theme.ClearCentsTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClearCentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ExpenseFeatureFlow()
                    }
                }
            }
        }
    }
}


@Composable
fun ExpenseFeatureFlow() {
    val navController = rememberNavController()
    AppNavigation(navController = navController)
}

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.DashboardDestination
    ) {
        composable<Destination.CreateCategoryDestination> {
            CreateCategoryRoot(
                viewModel = koinViewModel<CreateCategoryViewModel>(),
                navController = navController,
            )
        }
        composable<Destination.DashboardDestination> {
            DashboardRoot(
                viewModel = koinViewModel<DashboardViewModel>(),
                navController = navController,
            )
        }
//
//        composable<CreateExpense> {
//            AddExpenseScreen(
//                navController = navController,
//                categories = categories,
//                onAddExpense = { amount, date, descriptionText, categoryId ->
//                    val newExpense = Expense(
//                        amount = amount,
//                        date = date,
//                        description = descriptionText,
//                        categoryId = categoryId
//                    )
//                    onAddExpense(newExpense)
//                    navController.popBackStack()
//                }
//            )
//        }
//
//        composable<CreateCategory> {
//            CreateCategoryScreen(
//                navController = navController,
//                onSaveCategory = { name, budget, iconName ->
//                    val newCategory =
//                        ExpenseCategory(name = name, monthlyBudget = budget, iconName = iconName)
//                    onAddCategory(newCategory)
//                    navController.popBackStack()
//                }
//            )
//        }
//
//        composable<CategoryDetail> { backStackEntry ->
//            val categoryId = backStackEntry.toRoute<CategoryDetail>().id
//            val category = categories.find { it.id == categoryId }
//            val categoryExpenses = expenses[categoryId] ?: emptyList()
//
//            if (category != null) {
//                CategoryDetailsScreen(
//                    navController = navController,
//                    category = category,
//                    expenses = categoryExpenses,
//                    onDeleteExpense = onDeleteExpense,
//                    onDeleteCategory = {
//                        onDeleteCategory(category.id)
//                        navController.popBackStack(Home, false)
//                    }
//                )
//            } else {
//                // Handle category not found, e.g., navigate back or show an error
//                navController.popBackStack()
//            }
//        }
    }
}