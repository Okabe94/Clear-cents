package com.okabe.clearcents.feature_expenses.presentation.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.okabe.clearcents.ui.theme.ClearCentsTheme
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenPreview() {
    val sampleCategories = listOf(
        DashboardCategory(
            id = "1",
            name = "Groceries",
            monthlyBudget = 3000,
            spentAmount = 200
        ),
        DashboardCategory(
            id = "2",
            name = "Transport",
            monthlyBudget = 1000,
            spentAmount = 499
        ),
        DashboardCategory(
            id = "3",
            name = "Bills",
            spentAmount = 1000
        )
    )
    ClearCentsTheme {
        DashboardScreen(
            state = DashboardState(
                totalExpenses = 23030,
                categories = sampleCategories,
            )
        ) {}
    }
}

@Composable
fun DashboardRoot(
    viewModel: DashboardViewModel = koinViewModel<DashboardViewModel>(),
    navController: NavController = rememberNavController()
) {
//    val state by viewModel.state.collectAsStateWithLifecycle()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Dashboard") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
//                actions = {
//                    IconButton(onClick = { onAction(DashboardAction.OnAddCategory) }) {
//                        Icon(Icons.Filled.Add, contentDescription = "Create Category")
//                    }
//                }
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(
                    onClick = { onAction(DashboardAction.OnAddCategory) },
                    modifier = Modifier.padding(bottom = 8.dp),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Add Category")
                }
                FloatingActionButton(onClick = { onAction(DashboardAction.OnAddExpense) }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Expense")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Total Monthly Expenses", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        state.totalExpenses.toString(),
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Text(
                "Expense Categories",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (state.categories.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No categories yet. Tap the '+' icon to add one!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                return@Scaffold
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.categories, key = { category -> category.id }) { category ->
                    CategoryRow(
                        name = category.name,
                        budget = category.monthlyBudget,
                        spent = category.spentAmount,
                        onClick = { onAction(DashboardAction.OnCategoryDetail(category.id)) },
                        onDeleteCategory = { onAction(DashboardAction.OnDeleteCategory(category.id)) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryRow(
    name: String,
    budget: Long?,
    spent: Long,
    onClick: () -> Unit,
    onDeleteCategory: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Column {
                    Text(name, style = MaterialTheme.typography.titleMedium)
                    Text(
//                        "Spent: ${currencyFormat.format(spent)}",
                        "Spent: $spent",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant// Or MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    budget?.let {
                        Text(
//                            "Budget: ${currencyFormat.format(budget)}",
                            "Budget: $it",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer // Or MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Delete Category") },
                        onClick = {
                            showMenu = false
                            onDeleteCategory()
                        }
                    )
                }
            }
        }

        budget?.let {
            if (budget > 0) {
                val progress = (spent.toFloat() / budget.toFloat()).coerceIn(0f, 1f)
                LinearProgressIndicator(
                    progress = { progress }, // Updated for Material 3
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp) // Make it a bit thicker
                        .padding(horizontal = 16.dp, vertical = 8.dp), // Add padding
                    color = if (progress > 0.8f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.tertiary // Background color for the track
                )
            }
        }
    }
}
