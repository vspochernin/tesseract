private static Stream<Arguments> validCreatePortfolioRequestArgumentsProvider() {
    return Stream.of(
            Arguments.of(170_00L, 3),
            Arguments.of(5_000_000_00L, 3),
            Arguments.of(10_000_000_00L, 3),
            Arguments.of(5_000_000_00L, 0),
            Arguments.of(5_000_000_00L, 1),
            Arguments.of(5_000_000_00L, 2)
    );
}

@ParameterizedTest
@MethodSource("validCreatePortfolioRequestArgumentsProvider")
@DisplayName("Создание диверсификации с корректными данными")
public void givenValidCreatePortfolioRequest_whenCreatePortfolio_thenSuccess(
        Long amount,
        Integer riskTypeId) throws Exception
{
    HashMap<String, Object> request = new HashMap<>();
    request.put("amount", amount);
    request.put("riskTypeId", riskTypeId);

    checkIfPortfolioWithId3IsNotExists();

    mockMvc.perform(post("/api/v1/portfolios")
                    .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

    mockMvc.perform(get("/api/v1/portfolios/3")
                    .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currentAmount").value(lessThanOrEqualTo(amount.intValue())))
            .andExpect(jsonPath("$.riskTypeId").value(riskTypeId));
}

private static Stream<Arguments> invalidCreatePortfolioRequestArgumentsProvider() {
    return Stream.of(
            Arguments.of(169_99L, 3),
            Arguments.of(10_000_000_01L, 3),
            Arguments.of(5_000_000_00L, -1),
            Arguments.of(5_000_000_00L, 4)
    );
}

@ParameterizedTest
@MethodSource("invalidCreatePortfolioRequestArgumentsProvider")
@DisplayName("Создание диверсификации с некорректными данными")
public void givenInvalidCreatePortfolioRequest_whenCreatePortfolio_thenReturnsExpectedError(
        Long amount,
        Integer riskTypeId) throws Exception
{
    HashMap<String, Object> request = new HashMap<>();
    request.put("amount", amount);
    request.put("riskTypeId", riskTypeId);

    checkIfPortfolioWithId3IsNotExists();

    mockMvc.perform(post("/api/v1/portfolios")
                    .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());

    checkIfPortfolioWithId3IsNotExists();
}
