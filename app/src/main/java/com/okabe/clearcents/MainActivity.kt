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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.okabe.clearcents.feature_expenses.presentation.categoty_detail.CategoryDetailRoot
import com.okabe.clearcents.feature_expenses.presentation.categoty_detail.CategoryDetailViewModel
import com.okabe.clearcents.feature_expenses.presentation.create_category.CreateCategoryRoot
import com.okabe.clearcents.feature_expenses.presentation.create_category.CreateCategoryViewModel
import com.okabe.clearcents.feature_expenses.presentation.create_expense.CreateExpenseRoot
import com.okabe.clearcents.feature_expenses.presentation.create_expense.CreateExpenseViewModel
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

        composable<Destination.CreateExpenseDestination> {
            CreateExpenseRoot(
                viewModel = koinViewModel<CreateExpenseViewModel>(),
                navController = navController,
            )
        }

        composable<Destination.CategoryDetailDestination> { backStackEntry ->
            val categoryId = backStackEntry.toRoute<Destination.CategoryDetailDestination>().id

            CategoryDetailRoot(
                viewModel = koinViewModel<CategoryDetailViewModel>(),
            )
        }
    }
}