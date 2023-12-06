package ru.tesseract.ui

object Validation {
    private val SpecialSymbols = setOf(
        '!', '@', '#', '$', '%', '&', '*', '(', ')', '-', '_', '+', '=',
        ';', ':', ',', '.', '/', '?', '\\', '|', '[', ']', '{', '}',
    )

    fun isPasswordValid(value: String): Boolean {
        return value.length in 6..30 &&
                value.all { it.isLatinLetter() || it.isDigit() || it.isSpecialSymbol() } &&
                value.any { it.isLatinLetter() } &&
                value.any { it.isDigit() || it.isSpecialSymbol() }
    }

    fun isLoginValid(value: String): Boolean {
        return value.length in 3..16 && value.all { it.isLatinLetter() || it.isDigit() }
    }

    fun isEmailValid(value: String): Boolean {
        return '@' in value
    }

    fun isConfirmPasswordValid(a: String, b: String) = a == b

    private fun Char.isLatinLetter() = this in 'a'..'z' || this in 'A'..'Z'
    private fun Char.isSpecialSymbol() = this in SpecialSymbols
}
