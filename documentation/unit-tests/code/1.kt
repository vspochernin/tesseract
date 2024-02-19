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