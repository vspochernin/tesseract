@Test
@DisplayName("Регистрация с корректными данными")
public void givenValidCredentials_whenRegister_thenSuccess() throws Exception {
    HashMap<String, String> request = new HashMap<>();
    request.put("login", "correctLogin");
    request.put("email", "correctEmail@gmail.com");
    request.put("password", "correctPassword123");


    mockMvc.perform(post("/api/v1/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.token").isString());
}

private static Stream<Arguments> invalidRegisterRequestArgumentsProvider() {
    return Stream.of(
            Arguments.of("correctLogin", "incorrectEmail.com", "incorrectPassword"),
            Arguments.of("il", "incorrectEmail.com", "correctPassword123"),
            Arguments.of("il", "correctEmail@gmail.com", "incorrectPassword"));
}

@ParameterizedTest
@DisplayName("Регистрация с некорректными данными")
@MethodSource("invalidRegisterRequestArgumentsProvider")
public void givenInvalidRegisterRequest_whenRegister_thenBadRequest(String login, String email,
        String password) throws Exception
{
    HashMap<String, String> request = new HashMap<>();
    request.put("login", login);
    request.put("email", email);
    request.put("password", password);

    mockMvc.perform(post("/api/v1/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
}
