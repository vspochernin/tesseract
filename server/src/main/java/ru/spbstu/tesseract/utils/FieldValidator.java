package ru.spbstu.tesseract.utils;

public class FieldValidator {

    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MAX_PASSWORD_SIZE = 30;
    private static final int MIN_LOGIN_SIZE = 3;
    private static final int MAX_LOGIN_SIZE = 16;
    private static final String SPECIAL_SYMBOLS = "!@#$%&*()-_+=;:,./?\\|[]{}";

    public static boolean isValidPassword(String password) {
        if (password.length() < MIN_PASSWORD_SIZE || password.length() > MAX_PASSWORD_SIZE) {
            return false;
        }

        boolean isThereLetter = false;
        boolean isThereDigit = false;
        boolean isThereSpecialSymbol = false;

        for (char ch : password.toCharArray()) {
            if (Character.isLetter(ch)) {
                isThereLetter = true;
            } else if (Character.isDigit(ch)) {
                isThereDigit = true;
            } else if (isSpecialSymbol(ch)) {
                isThereSpecialSymbol = true;
            } else {
                return false;
            }
        }

        return isThereLetter && (isThereDigit || isThereSpecialSymbol);
    }

    public static boolean isValidLogin(String login) {
        if (login.length() < MIN_LOGIN_SIZE || login.length() > MAX_LOGIN_SIZE) {
            return false;
        }

        for (char  ch : login.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isSpecialSymbol(char ch) {
        return SPECIAL_SYMBOLS.contains(String.valueOf(ch));
    }
}
