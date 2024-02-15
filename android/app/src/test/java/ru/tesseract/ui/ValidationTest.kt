package ru.tesseract.ui

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationTest {

    @Test
    fun `isPasswordValid should return true for valid passwords`() {
        assertTrue(Validation.isPasswordValid("password123"))
        assertTrue(Validation.isPasswordValid("password!"))
    }

    @Test
    fun `isPasswordValid should return false for passwords with length less than 6`() {
        assertFalse(Validation.isPasswordValid("pa123"))
    }

    @Test
    fun `isPasswordValid should return false for passwords with length more than 30`() {
        assertFalse(Validation.isPasswordValid("password01password01password012"))
    }

    @Test
    fun `isPasswordValid should return false for passwords without digits or special chars`() {
        assertFalse(Validation.isPasswordValid("password"))
    }

    @Test
    fun `isPasswordValid should return false for passwords without letters`() {
        assertFalse(Validation.isPasswordValid("01234567"))
    }

    @Test
    fun `isPasswordValid should return false for passwords with invalid chars`() {
        assertFalse(Validation.isPasswordValid("пароль123"))
    }

    @Test
    fun `isLoginValid should return true for valid login`() {
        assertTrue(Validation.isLoginValid("vspochernin123"))
    }

    @Test
    fun `isLoginValid should return false for logins with length less than 3`() {
        assertFalse(Validation.isLoginValid("vs"))
    }

    @Test
    fun `isLoginValid should return false for logins with length more than 16`() {
        assertFalse(Validation.isLoginValid("vspocherninvspoch"))
    }

    @Test
    fun `isLoginValid should return false for logins with non-alphanumeric chars`() {
        assertFalse(Validation.isLoginValid("vspochernin_"))
    }

    @Test
    fun `isEmailValid should return true for valid emails`() {
        assertTrue(Validation.isEmailValid("vspochernin@gmail.com"))
    }

    @Test
    fun `isEmailValid should return false for emails without @`() {
        assertFalse(Validation.isEmailValid("vspocherningmail.com"))
    }

    @Test
    fun `isEmailValid should return false for emails with spaces`() {
        assertFalse(Validation.isEmailValid("vspochernin@ gmail.com"))
    }

    @Test
    fun `isDiversificationAmountValid should return true for valid sum`() {
        assertTrue(Validation.isDiversificationAmountValid("123"))
    }

    @Test
    fun `isDiversificationAmountValid should return false for big sum`() {
        assertFalse(Validation.isDiversificationAmountValid("100000000000000000"))
    }

    @Test
    fun `isDiversificationAmountValid should return false for negative sum`() {
        assertFalse(Validation.isDiversificationAmountValid("-123"))
    }

    @Test
    fun `isDiversificationAmountValid should return false for not number`() {
        assertFalse(Validation.isDiversificationAmountValid("123a"))
    }

    @Test
    fun `isConfirmPassword valid should return true if passwords are equal`() {
        assertTrue(Validation.isConfirmPasswordValid("password", "password"))
    }

    @Test
    fun `isConfirmPassword valid should return false if passwords are different`() {
        assertFalse(Validation.isConfirmPasswordValid("password", "password "))
    }
}
