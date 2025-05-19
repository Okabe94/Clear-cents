package com.okabe.clearcents.util

import org.junit.Test

class StringUtilsKtTest {

    @Test
    fun `capitalize multiple words`() {
        val input = "hello world"
        val expected = "Hello World"
        val actual = input.capitalizeWords()
        assert(actual == expected)
    }

    @Test
    fun `capitalize one word`() {
        val input = "helloworld"
        val expected = "Helloworld"
        val actual = input.capitalizeWords()
        assert(actual == expected)
    }
}