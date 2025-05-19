package com.okabe.clearcents.feature_expenses.presentation.dashboard

// import androidx.compose.material.icons.filled.Settings // If you want a settings/add category icon in top bar
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.okabe.clearcents.feature_expenses.presentation.CategoryIcons
import com.okabe.clearcents.feature_expenses.presentation.Expense
import com.okabe.clearcents.feature_expenses.presentation.ExpenseCategory
import com.okabe.clearcents.feature_expenses.presentation.navigation.Screen
import com.okabe.clearcents.ui.theme.ClearCentsTheme
import java.text.NumberFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    categories: List<ExpenseCategory>,
    expenses: Map<String, List<Expense>>,
    onDeleteCategory: (String) -> Unit // Callback to delete a category
) {
    val totalExpenses = expenses.values.flatten().sumOf { it.amount }
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Dashboard") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                // Optional: Add action to navigate to Create Category
                // actions = {
                //     IconButton(onClick = { navController.navigate(AppDestinations.CREATE_CATEGORY_ROUTE) }) {
                //         Icon(Icons.Filled.AddCircleOutline, contentDescription = "Create Category")
                //     }
                // }
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.CreateCategory) },
                    modifier = Modifier.padding(bottom = 8.dp),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Add Category")
                }
                FloatingActionButton(onClick = { navController.navigate(Screen.CreateExpense) }) {
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
                        currencyFormat.format(totalExpenses),
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

            if (categories.isEmpty()) {
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
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories, key = { category -> category.id }) { category ->
                        val categoryExpensesSum = expenses[category.id]?.sumOf { it.amount } ?: 0.0
                        CategoryRow(
                            category = category,
                            spentAmount = categoryExpensesSum,
                            currencyFormat = currencyFormat,
                            onClick = { navController.navigate(Screen.CategoryDetail(category.id)) },
                            onDeleteCategory = {
                                // Consider adding a confirmation dialog here
                                onDeleteCategory(category.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryRow(
    category: ExpenseCategory,
    spentAmount: Double,
    currencyFormat: NumberFormat,
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
                Icon(
                    imageVector = CategoryIcons.getIconByName(category.iconName),
                    contentDescription = category.name,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(category.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Spent: ${currencyFormat.format(spentAmount)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray // Or MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (category.monthlyBudget > 0) {
                        Text(
                            "Budget: ${currencyFormat.format(category.monthlyBudget)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.DarkGray // Or MaterialTheme.colorScheme.onSurfaceVariant
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
        if (category.monthlyBudget > 0) {
            val progress = (spentAmount / category.monthlyBudget).toFloat().coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progress }, // Updated for Material 3
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp) // Make it a bit thicker
                    .padding(horizontal = 16.dp, vertical = 8.dp), // Add padding
                color = if (progress > 0.8f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant // Background color for the track
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    val sampleCategories = listOf(
        ExpenseCategory(
            id = "1",
            name = "Groceries",
            monthlyBudget = 300.0,
            iconName = "Groceries"
        ),
        ExpenseCategory(
            id = "2",
            name = "Transport",
            monthlyBudget = 100.0,
            iconName = "Transport"
        ),
        ExpenseCategory(
            id = "3",
            name = "Bills",
            monthlyBudget = 0.0,
            iconName = "Home"
        ) // No budget
    )
    val sampleExpenses = mapOf(
        "1" to listOf(
            Expense(
                amount = 75.50,
                date = Date(),
                description = "Aldi",
                categoryId = "1"
            )
        ),
        "2" to listOf(
            Expense(
                amount = 120.0,
                date = Date(),
                description = "Train pass",
                categoryId = "2"
            )
        )
    )
    ClearCentsTheme {
        DashboardScreen(
            navController = navController,
            categories = sampleCategories,
            expenses = sampleExpenses,
            onDeleteCategory = {}
        )
    }
}