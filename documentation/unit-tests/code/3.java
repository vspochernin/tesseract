@Test
public void givenVVV_whenValidateFields_thenSuccess() {
    String login = "correctLogin";
    String email = "correct@email.com";
    String password = "qwe123";

    assertThatNoException().isThrownBy(() ->
            FieldValidator.validateFields(login, email, password));
}

@Test
public void givenVII_whenValidateFields_thenThrow() {
    String login = "correctLogin";
    String email = "incorrectemail.com";
    String password = "123";

    assertThatThrownBy(() -> FieldValidator.validateFields(login, email, password))
            .isInstanceOf(TesseractException.class);
}

@Test
public void givenIIV_whenValidateFields_thenThrow() {
    String login = "1";
    String email = "incorrectemail.com";
    String password = "qwe123";

    assertThatThrownBy(() -> FieldValidator.validateFields(login, email, password))
            .isInstanceOf(TesseractException.class);
}

@Test
public void givenIVI_whenValidateFields_thenThrow() {
    String login = "1";
    String email = "correct@email.com";
    String password = "123";

    assertThatThrownBy(() -> FieldValidator.validateFields(login, email, password))
            .isInstanceOf(TesseractException.class);
}
