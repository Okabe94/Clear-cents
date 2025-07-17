package com.okabe.clearcents.feature_expenses.presentation.create_expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.okabe.clearcents.ui.theme.ClearCentsTheme
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Preview
@Composable
private fun Preview() {
    ClearCentsTheme {
        CreateExpenseScreen(
            state = CreateExpenseState(),
            onAction = {}
        )
    }
}

@Composable
fun CreateExpenseRoot(
    viewModel: CreateExpenseViewModel = koinViewModel(),
    navController: NavController = rememberNavController()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CreateExpenseScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExpenseScreen(
    state: CreateExpenseState,
    onAction: (CreateExpenseAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Expense") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val dateFormatter = remember { SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()) }
            OutlinedTextField(
                value = dateFormatter.format(state.date),
                onValueChange = { /* Read Only */ },
                label = { Text("Date") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        Icons.Filled.DateRange,
                        "Select Date",
                        Modifier.clickable { onAction(CreateExpenseAction.OnDatePickerShow) }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (state.showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = state.date.time,
                    // You can set yearRange and other constraints if needed
                )
                DatePickerDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        TextButton(onClick = {
                            onAction(CreateExpenseAction.OnDatePickerHide)
                            onAction(CreateExpenseAction.OnDateChange(datePickerState.selectedDateMillis.toString()))
                        }) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            onAction(CreateExpenseAction.OnDatePickerHide)
                        }) { Text("Cancel") }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.categorySelected.orEmpty(),
                    onValueChange = { /* Read Only */ },
                    label = { Text("Category") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector =
                                if (state.showCategoryPicker) Icons.Filled.KeyboardArrowUp
                                else Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Select Category",
                            modifier = Modifier.clickable {
                                onAction(CreateExpenseAction.OnDropDownToggle)
                            }
                        )
                    },
                )
                DropdownMenu(
                    expanded = state.showCategoryPicker && state.categories.isNotEmpty(),
                    onDismissRequest = { onAction(CreateExpenseAction.OnDropDownToggle) },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    state.categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = { onAction(CreateExpenseAction.OnCategorySelected(category)) }
                        )
                    }
                    if (state.categories.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No categories available. Create one first.") },
                            onClick = { onAction(CreateExpenseAction.OnBlockDropDown) }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = state.amount.toString(),
                onValueChange = { value ->
                    onAction(CreateExpenseAction.OnAmountChange(value))
                },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Text(
                        text = NumberFormat.getCurrencyInstance(
                            Locale.getDefault()
                        ).currency?.symbol ?: "$"
                    )
                }
            )

            OutlinedTextField(
                value = state.description.toString(),
                onValueChange = { onAction(CreateExpenseAction.OnDescriptionChange(it)) },
                label = { Text("Description (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Button(
                enabled = state.readyToSave,
                onClick = { onAction(CreateExpenseAction.OnSave) },
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Create", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.width(8.dp))

                Icon(Icons.Filled.Check, contentDescription = "Save Expense")
            }
        }
    }
}