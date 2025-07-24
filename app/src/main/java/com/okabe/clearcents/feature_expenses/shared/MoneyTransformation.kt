package com.okabe.clearcents.feature_expenses.shared

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.NumberFormat
import java.util.Locale

class MoneyTransformation : VisualTransformation {
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