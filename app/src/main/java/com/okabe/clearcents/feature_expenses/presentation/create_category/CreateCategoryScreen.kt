package com.okabe.clearcents.feature_expenses.presentation.create_category

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.okabe.clearcents.R
import com.okabe.clearcents.ui.theme.ClearCentsTheme
import com.okabe.clearcents.util.capitalizeWords
import java.text.NumberFormat
import java.util.Locale

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Preview() {
    ClearCentsTheme {
        CreateCategoryScreen(
            state = CreateCategoryState(),
            onAction = {}
        )
    }
}

@Composable
fun CreateCategoryRoot(
    viewModel: CreateCategoryViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CreateCategoryScreen(
        state = state,
        onAction = {
            when (it) {
                is CreateCategoryAction.OnGoBack -> navController.popBackStack()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategoryScreen(
    state: CreateCategoryState,
    onAction: (CreateCategoryAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.create_new_category).capitalizeWords()) },
                navigationIcon = {
                    IconButton(onClick = { onAction(CreateCategoryAction.OnGoBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                onClick = { onAction(CreateCategoryAction.OnSave) },
                containerColor =
                    if (state.readyToSave) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            ) {
                Icon(
                    Icons.Filled.Check, contentDescription = "Save Category",
                    tint =
                        if (state.readyToSave) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
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
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                singleLine = true,
                label = { Text(stringResource(R.string.category_name).capitalizeWords()) },
                onValueChange = { onAction(CreateCategoryAction.OnNameChange(it)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.budget,
                singleLine = true,
                onValueChange = { onAction(CreateCategoryAction.OnBudgetChange(it)) },
                label = { Text(stringResource(R.string.monthly_budget_optional).capitalizeWords()) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                visualTransformation = NumberCommaTransformation()
            )
        }
    }
}

class NumberCommaTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            text = AnnotatedString(text.text.toLongOrNull().formatWithComma()),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int =
                    text.text.toLongOrNull().formatWithComma().length

                override fun transformedToOriginal(offset: Int): Int = text.length
            }
        )
    }

    private fun Long?.formatWithComma(): String {
        return if (this == null) ""
        else NumberFormat.getNumberInstance(Locale.getDefault()).format(this)
    }
}