package ru.tesseract.ui

object Validation {
    const val MaxDiversificationAmountRubles = 10_000_000L

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
        return Regex("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}\$", RegexOption.IGNORE_CASE).matches(value)
    }

    fun isDiversificationAmountValid(value: String): Boolean {
        val amount = value.toLongOrNull() ?: return false
        return amount in 1..MaxDiversificationAmountRubles
    }

    fun isConfirmPasswordValid(a: String, b: String) = a == b

    private fun Char.isLatinLetter() = this in 'a'..'z' || this in 'A'..'Z'
    private fun Char.isSpecialSymbol() = this in SpecialSymbols
}
