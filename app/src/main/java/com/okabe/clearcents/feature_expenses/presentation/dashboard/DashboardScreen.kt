package com.okabe.clearcents.feature_expenses.presentation.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.okabe.clearcents.feature_expenses.presentation.navigation.Destination
import com.okabe.clearcents.ui.theme.ClearCentsTheme
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenPreview() {
    val sampleCategories = listOf(
        DashboardCategory(
            id = 1,
            name = "Groceries",
            monthlyBudget = 1000000,
            spentAmount = 240000
        ),
        DashboardCategory(
            id = 2,
            name = "Transport",
            monthlyBudget = 150000,
            spentAmount = 140000
        ),
        DashboardCategory(
            id = 3,
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
    val state by viewModel.state.collectAsStateWithLifecycle()
    DashboardScreen(
        state = state,
        onAction = {
            viewModel.onAction(it)

            when (it) {
                DashboardAction.OnCreateCategory ->
                    navController.navigate(Destination.CreateCategoryDestination)

                DashboardAction.OnCreateExpense ->
                    navController.navigate(Destination.CreateExpenseDestination)

                is DashboardAction.OnCategoryDetail -> navController.navigate(
                    Destination.CategoryDetailDestination(it.categoryId)
                )

                else -> Unit
            }
        }
    )
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
                title = { Text("My Dashboard", style = MaterialTheme.typography.headlineSmall) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
            )
        },
        floatingActionButton = {
            Row(horizontalArrangement = Arrangement.End) {
                FloatingActionButton(
                    onClick = { onAction(DashboardAction.OnCreateCategory) },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Add Category")
                }

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(onClick = { onAction(DashboardAction.OnCreateExpense) }) {
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
                contentPadding = PaddingValues(bottom = 70.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(state.categories) { category ->
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
    val progress =
        if (budget == null) 1f
        else (spent.toFloat() / budget.toFloat()).coerceIn(0f, 1f)

    Card(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (cProgress, cValues) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxSize(progress)
                    .background(
                        when {
                            budget == null -> MaterialTheme.colorScheme.surfaceContainerLowest
                            progress < 0.85 -> MaterialTheme.colorScheme.primaryContainer
                            progress < 0.99 -> MaterialTheme.colorScheme.tertiaryContainer
                            else -> MaterialTheme.colorScheme.errorContainer
                        }
                    )
                    .constrainAs(cProgress) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .padding(16.dp)
                    .constrainAs(cValues) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)

                        width = Dimension.fillToConstraints
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            budget?.let {
                                Text(
//                            "Budget: ${currencyFormat.format(budget)}",
                                    text = "$$it",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )

                            }

                            Text(
//                        "Spent: ${currencyFormat.format(spent)}",
                                text = "${progress * 100}%",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }


                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            budget?.let {
                                Text(
//                        "Spent: ${currencyFormat.format(spent)}",
                                    text = "Left: $${it - spent}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            Text(
//                        "Spent: ${currencyFormat.format(spent)}",
                                text = "Spent: $$spent",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = onDeleteCategory) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete Category")
                        }
                    }
                }
            }
        }
    }
}
