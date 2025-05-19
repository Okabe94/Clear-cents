package com.okabe.clearcents.util

fun String.capitalizeWords(): String = this.split(" ")
    .joinToString(" ") { word ->
        word.replaceFirstChar { char -> char.uppercase() }
    }