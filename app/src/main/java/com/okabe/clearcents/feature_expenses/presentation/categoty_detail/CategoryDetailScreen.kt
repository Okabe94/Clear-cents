package com.okabe.clearcents.feature_expenses.presentation.categoty_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.okabe.clearcents.feature_expenses.domain.model.ExpenseModel
import com.okabe.clearcents.ui.theme.ClearCentsTheme
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Preview
@Composable
private fun Preview() {
    ClearCentsTheme {
        CategoryDetailScreen(
            state = CategoryDetailState(),
            onAction = {}
        )
    }
}

@Composable
fun CategoryDetailRoot(
    viewModel: CategoryDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoryDetailScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    state: CategoryDetailState,
    onAction: (CategoryDetailAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.name) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = {
                            // Open category menu
                        }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Category Options"
                            )
                        }

                        DropdownMenu(
                            expanded = state.showCategoryMenu,
                            onDismissRequest = { /* Close menu */ }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit Category") },
                                onClick = {
                                    // showCategoryMenu = false
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
                                    // showCategoryMenu = false
                                    // showDeleteCategoryDialog = true
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
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add Expense to ${state.name}"
                )
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
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(
                        16.dp
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            state.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(
                            12.dp
                        )
                    )

                    Text(
                        "Total Spent: ${currencyFormat(state.totalSpent)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (state.monthlyBudget > 0) {
                        Text(
                            "Budget: ${currencyFormat(state.monthlyBudget)}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(
                            modifier = Modifier.height(
                                8.dp
                            )
                        )
                        val progress = (state.totalSpent / state.monthlyBudget).toFloat()
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
                            state.totalSpent > state.monthlyBudget ->
                                "Over budget by ${currencyFormat(state.totalSpent - state.monthlyBudget)}"

                            state.totalSpent == state.monthlyBudget -> "Budget reached"
                            else -> "${currencyFormat(state.monthlyBudget - state.totalSpent)} remaining"
                        }

                        Text(
                            budgetStatusText,
                            style = MaterialTheme.typography.bodySmall,
                            color =
                                if (state.totalSpent > state.monthlyBudget) MaterialTheme.colorScheme.error
                                else MaterialTheme.colorScheme.onSurfaceVariant,
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
                "Expenses in ${state.name}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (state.expenses.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No expenses recorded for this category yet.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(
                        8.dp
                    )
                ) {
                    items(
                        state.expenses,
                        key = { expense -> expense.expenseId }
                    ) { expense ->
                        ExpenseDetailsRow(
                            expense = expense,
                            onDeleteClick = {
                                // expenseToDelete = expense
                            } // Show confirmation dialog
                        )
                    }
                }
            }
        }

        // Confirmation Dialog for Deleting Expense
        if (state.expenseToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    // state.expenseToDelete = null
                },
                title = { Text("Delete Expense?") },
                text = {
                    Text(
                        "Are you sure you want to delete the expense: ${
                            state.expenseToDelete!!.description ?: currencyFormat(
                                state.expenseToDelete!!.amount
                            )
                        }?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = { }
                    ) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        // expenseToDelete = null
                    }) { Text("Cancel") }
                }
            )
        }

        // Confirmation Dialog for Deleting Category
        if (state.showDeleteCategoryDialog) {
            AlertDialog(
                onDismissRequest = {
                    // showDeleteCategoryDialog = false
                },
                title = { Text("Delete Category '${state.name}'?") },
                text = { Text("Are you sure you want to delete this category and all its expenses? This action cannot be undone.") },
                confirmButton = {
                    TextButton(
                        onClick = { },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = { }) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
private fun ExpenseDetailsRow(
    expense: ExpenseModel,
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
                currencyFormat(expense.amount),
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

private fun currencyFormat(amount: Long): String {
    val formatter = NumberFormat.getCurrencyInstance()
    return formatter.format(amount)
}