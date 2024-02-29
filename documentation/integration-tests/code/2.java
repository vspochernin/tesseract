@ParameterizedTest
@ValueSource(strings = {
        "",
        "ip1&",
        "incorrectPassword1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&",
        "123&*(",
        "qweqweqwe",
        "qwe123&*(±"})
@DisplayName("Регистрация с некорректным паролем")
public void givenInvalidPassword_whenRegister_thenReturnsExpectedError(String password)
        throws Exception {
    HashMap<String, String> request = new HashMap<>();
    request.put("login", "correctLogin");
    request.put("email", "vspochernin@gmail.com");
    request.put("password", password);

    mockMvc.perform(post("/api/v1/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.id").value(4))
            .andExpect(jsonPath("$.errorType").value("INVALID_PASSWORD"));
}
