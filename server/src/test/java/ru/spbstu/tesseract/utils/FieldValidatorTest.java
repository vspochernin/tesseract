package ru.spbstu.tesseract.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class FieldValidatorTest {

    @Test
    public void givenValidPassword_whenIsValidPassword_thenReturnTrue() {
        String validPassword = "qwe123&*(";

        boolean actualIsValidPassword = FieldValidator.isValidPassword(validPassword);

        assertThat(actualIsValidPassword).isTrue();
    }

    @Test
    public void givenTooShortPassword_whenIsValidPassword_thenReturnFalse() {
        String tooShortPassword = "qw1&";

        boolean actualIsValidPassword = FieldValidator.isValidPassword(tooShortPassword);

        assertThat(actualIsValidPassword).isFalse();
    }

    @Test
    public void givenTooLongPassword_whenIsValidPassword_thenReturnFalse() {
        String tooLongPassword = "qwe123%^&qwe123%^&qwe123%^&qwe123%^&qwe123%^&qwe123%^&qwe123%^&";

        boolean actualIsValidPassword = FieldValidator.isValidPassword(tooLongPassword);

        assertThat(actualIsValidPassword).isFalse();
    }

    @Test
    void givenPasswordWithNoLetters_whenIsValidPassword_thenReturnFalse() {
        String passWordWithNoLetters = "123&*(";

        boolean actualIsValidPassword = FieldValidator.isValidPassword(passWordWithNoLetters);

        assertThat(actualIsValidPassword).isFalse();
    }

    @Test
    void givenPasswordWithNoDigitsOrSpecialSymbols_whenIsValidPassword_thenReturnFalse() {
        String passwordWithNoDigitsOrSpecialSymbols = "qweqweqwe";

        boolean actualIsValidPassword = FieldValidator.isValidPassword(passwordWithNoDigitsOrSpecialSymbols);

        assertThat(actualIsValidPassword).isFalse();
    }

    @Test
    void givenPasswordWithUnknownSymbol_whenIsValidPassword_thenReturnFalse() {
        String passwordWithUnknownSymbol = "qwe123&*(±";

        boolean actualIsValidPassword = FieldValidator.isValidPassword(passwordWithUnknownSymbol);

        assertThat(actualIsValidPassword).isFalse();
    }

    @Test
    void givenEmptyPassword_whenIsValidPassword_thenReturnFalse() {
        String emptyPassword = "";

        boolean actualIsValidPassword = FieldValidator.isValidPassword(emptyPassword);

        assertThat(actualIsValidPassword).isFalse();
    }
}