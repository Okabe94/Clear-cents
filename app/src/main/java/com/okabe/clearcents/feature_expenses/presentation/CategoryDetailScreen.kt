package com.okabe.clearcents.feature_expenses.presentation

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.okabe.clearcents.ui.theme.ClearCentsTheme
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun CategoryDetailsScreenPreview() {
    val navController = rememberNavController()
    val sampleCategory = ExpenseCategory(
        id = "1",
        name = "Groceries",
        monthlyBudget = 250.0,
        iconName = "Groceries"
    )
    val sampleExpenses = listOf(
        Expense(
            id = "e1",
            amount = 45.99,
            date = Date(),
            description = "Weekly Shopping at Aldi",
            categoryId = "1"
        ),
        Expense(
            id = "e2",
            amount = 12.50,
            date = Date(),
            description = "Milk, Bread, Eggs",
            categoryId = "1"
        ),
        Expense(
            id = "e3",
            amount = 250.0,
            date = Date(),
            description = "Big shop",
            categoryId = "1"
        )
    )
    ClearCentsTheme {
        CategoryDetailsScreen(
            navController = navController,
            category = sampleCategory,
            expenses = sampleExpenses,
            onDeleteExpense = {},
            onDeleteCategory = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDetailsScreenOverBudgetPreview() {
    val navController = rememberNavController()
    val sampleCategory = ExpenseCategory(
        id = "1",
        name = "Entertainment",
        monthlyBudget = 100.0,
        iconName = "Entertainment"
    )
    val sampleExpenses = listOf(
        Expense(
            id = "e1",
            amount = 70.0,
            date = Date(),
            description = "Cinema Tickets",
            categoryId = "1"
        ),
        Expense(
            id = "e2",
            amount = 45.0,
            date = Date(),
            description = "Dinner Out",
            categoryId = "1"
        )
    )
    ClearCentsTheme {
        CategoryDetailsScreen(
            navController = navController,
            category = sampleCategory,
            expenses = sampleExpenses,
            onDeleteExpense = {},
            onDeleteCategory = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDetailsScreenNoBudgetPreview() {
    val navController = rememberNavController()
    val sampleCategory = ExpenseCategory(
        id = "1",
        name = "Miscellaneous",
        monthlyBudget = 0.0,
        iconName = "Default"
    )
    val sampleExpenses = listOf(
        Expense(
            id = "e1",
            amount = 19.99,
            date = Date(),
            description = "Online Subscription",
            categoryId = "1"
        )
    )
    ClearCentsTheme {
        CategoryDetailsScreen(
            navController = navController,
            category = sampleCategory,
            expenses = sampleExpenses,
            onDeleteExpense = {},
            onDeleteCategory = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailsScreen(
    navController: NavController,
    category: ExpenseCategory,
    expenses: List<Expense>,
    onDeleteExpense: (Expense) -> Unit,
    onDeleteCategory: () -> Unit
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val totalSpentInCategory = expenses.sumOf { it.amount }
    var showCategoryMenu by remember { mutableStateOf(false) }
    var showDeleteCategoryDialog by remember { mutableStateOf(false) }
    var expenseToDelete by remember { mutableStateOf<Expense?>(null) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { showCategoryMenu = true }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "Category Options")
                        }
                        DropdownMenu(
                            expanded = showCategoryMenu,
                            onDismissRequest = { showCategoryMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit Category") },
                                onClick = {
                                    showCategoryMenu = false
                                    // TODO: Navigate to an EditCategoryScreen (similar to CreateCategoryScreen)
                                    // navController.navigate("edit_category/${category.id}")
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Edit,
                                        contentDescription = "Edit Category"
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Delete Category") },
                                onClick = {
                                    showCategoryMenu = false
                                    showDeleteCategoryDialog = true
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Delete Category"
                                    )
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navigate to AddExpenseScreen, pre-filling this category
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Expense to ${category.name}")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Category Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = CategoryIcons.getIconByName(category.iconName),
                            contentDescription = category.name,
                            modifier = Modifier.size(50.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            category.name,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Total Spent: ${currencyFormat.format(totalSpentInCategory)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (category.monthlyBudget > 0) {
                        Text(
                            "Budget: ${currencyFormat.format(category.monthlyBudget)}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val progress = (totalSpentInCategory / category.monthlyBudget).toFloat()
                            .coerceIn(0f, 1f)
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = if (progress > 0.8f && progress <= 1.0f) MaterialTheme.colorScheme.errorContainer
                            else if (progress > 1.0f) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                        val budgetStatusText = when {
                            totalSpentInCategory > category.monthlyBudget ->
                                "Over budget by ${currencyFormat.format(totalSpentInCategory - category.monthlyBudget)}"

                            totalSpentInCategory == category.monthlyBudget -> "Budget reached"
                            else -> "${currencyFormat.format(category.monthlyBudget - totalSpentInCategory)} remaining"
                        }
                        Text(
                            budgetStatusText,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (totalSpentInCategory > category.monthlyBudget) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                    } else {
                        Text(
                            "No budget set for this category.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Expenses List
            Text(
                "Expenses in ${category.name}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (expenses.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "No expenses recorded for this category yet.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(expenses, key = { expense -> expense.id }) { expense ->
                        ExpenseDetailsRow(
                            expense = expense,
                            currencyFormat = currencyFormat,
                            onDeleteClick = {
                                expenseToDelete = expense
                            } // Show confirmation dialog
                        )
                    }
                }
            }
        }

        // Confirmation Dialog for Deleting Expense
        if (expenseToDelete != null) {
            AlertDialog(
                onDismissRequest = { expenseToDelete = null },
                title = { Text("Delete Expense?") },
                text = {
                    Text(
                        "Are you sure you want to delete the expense: ${
                            expenseToDelete!!.description ?: currencyFormat.format(
                                expenseToDelete!!.amount
                            )
                        }?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDeleteExpense(expenseToDelete!!)
                            expenseToDelete = null
                        }
                    ) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = { expenseToDelete = null }) { Text("Cancel") }
                }
            )
        }

        // Confirmation Dialog for Deleting Category
        if (showDeleteCategoryDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteCategoryDialog = false },
                title = { Text("Delete Category '${category.name}'?") },
                text = { Text("Are you sure you want to delete this category and all its expenses? This action cannot be undone.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDeleteCategory()
                            showDeleteCategoryDialog = false
                            // Navigation is handled in AppNavigation after onDeleteCategory callback
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteCategoryDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
fun ExpenseDetailsRow(
    expense: Expense,
    currencyFormat: NumberFormat,
    onDeleteClick: () -> Unit
) {
    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    expense.description ?: "Expense",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    dateFormatter.format(expense.date),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                currencyFormat.format(expense.amount),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.error // Typically expenses are shown in a 'negative' color
            )
            IconButton(onClick = onDeleteClick) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete expense",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}