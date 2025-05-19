package com.okabe.clearcents.feature_expenses.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.okabe.clearcents.ui.theme.ClearCentsTheme
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    navController: NavController,
    categories: List<ExpenseCategory>,
    selectedCategoryId: String? = null, // Optional pre-selected category ID
    onAddExpense: (amount: Double, date: Date, description: String?, categoryId: String) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Initialize selectedCategory based on selectedCategoryId
    var selectedCategory by remember {
        mutableStateOf(categories.find { it.id == selectedCategoryId })
    }
    var selectedDate by remember { mutableStateOf(Date()) } // Default to today

    var showCategoryDropdown by remember { mutableStateOf(false) }
    val showDatePickerDialog = remember { mutableStateOf(false) }

    val isFormValid = amount.isNotBlank() &&
            amount.toDoubleOrNull() != null && amount.toDoubleOrNull()!! > 0 &&
            selectedCategory != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Expense") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isFormValid) {
                        onAddExpense(
                            amount.toDouble(),
                            selectedDate,
                            description.ifBlank { null },
                            selectedCategory!!.id // Safe due to isFormValid check
                        )
                    }
                },
                containerColor = if (isFormValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.12f
                )
            ) {
                Icon(
                    Icons.Filled.Check, contentDescription = "Save Expense",
                    tint = if (isFormValid) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.38f
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = { value ->
                    // Allow only digits and a single decimal point
                    if (value.isEmpty() || value.matches(Regex("^\\d*\\.?\\d*\$"))) {
                        amount = value
                    }
                },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Text(
                        text = NumberFormat.getCurrencyInstance(Locale.getDefault()).currency?.symbol
                            ?: "$"
                    )
                }
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedCategory?.name ?: "Select Category",
                    onValueChange = { /* Read Only */ },
                    label = { Text("Category") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            if (showCategoryDropdown) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            "Select Category",
                            Modifier.clickable { showCategoryDropdown = !showCategoryDropdown }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showCategoryDropdown = true }
                )
                DropdownMenu(
                    expanded = showCategoryDropdown && categories.isNotEmpty(),
                    onDismissRequest = { showCategoryDropdown = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                selectedCategory = category
                                showCategoryDropdown = false
                            }
                        )
                    }
                    if (categories.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No categories available. Create one first.") },
                            onClick = { showCategoryDropdown = false }
                        )
                    }
                }
            }

            val dateFormatter = remember { SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()) }
            OutlinedTextField(
                value = dateFormatter.format(selectedDate),
                onValueChange = { /* Read Only */ },
                label = { Text("Date") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        Icons.Filled.DateRange,
                        "Select Date",
                        Modifier.clickable { showDatePickerDialog.value = true }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (showDatePickerDialog.value) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = selectedDate.time,
                    // You can set yearRange and other constraints if needed
                )
                DatePickerDialog(
                    onDismissRequest = { showDatePickerDialog.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showDatePickerDialog.value = false
                            datePickerState.selectedDateMillis?.let {
                                selectedDate = Date(it)
                            }
                        }) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDatePickerDialog.value = false
                        }) { Text("Cancel") }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddExpenseScreenPreview() {
    val navController = rememberNavController()
    val sampleCategories = listOf(
        ExpenseCategory(id = "1", name = "Food", monthlyBudget = 0.0, iconName = "Default"),
        ExpenseCategory(id = "2", name = "Transport", monthlyBudget = 0.0, iconName = "Default")
    )
    ClearCentsTheme {
        AddExpenseScreen(
            navController = navController,
            categories = sampleCategories,
            onAddExpense = { _, _, _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddExpenseScreenWithPreselectedCategoryPreview() {
    val navController = rememberNavController()
    val catId = "1"
    val sampleCategories = listOf(
        ExpenseCategory(
            id = catId,
            name = "Groceries",
            monthlyBudget = 200.0,
            iconName = "Groceries"
        ),
        ExpenseCategory(id = "2", name = "Transport", monthlyBudget = 50.0, iconName = "Transport")
    )
    ClearCentsTheme {
        AddExpenseScreen(
            navController = navController,
            categories = sampleCategories,
            selectedCategoryId = catId, // Pre-select "Groceries"
            onAddExpense = { _, _, _, _ -> }
        )
    }
}