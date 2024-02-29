package ru.spbstu.tesseract.controller;

import java.util.HashMap;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.spbstu.tesseract.utils.Secrets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class AuthenticationControllerTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.7")
            .withDatabaseName("tesseract")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.username", postgres::getUsername);
    }

    @BeforeEach
    void setup() {
        Flyway flyway = Flyway.configure()
                .dataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())
                .cleanDisabled(false)
                .load();

        flyway.clean();
        flyway.migrate();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @DisplayName("Ошибка регистрации: пользователь уже существует")
    public void givenCredentialsWithExistingLogin_whenRegister_thenReturnsExpectedError() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("login", "vspochernin");
        request.put("email", "correctEmail@gmail.com");
        request.put("password", "correctPassword123");


        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.errorType").value("LOGIN_EXISTS"));
    }

    @Test
    @DisplayName("Ошибка регистрации: email уже существует")
    public void givenCredentialsWithExistingEmail_whenRegister_thenReturnsExpectedError() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("login", "correctLogin");
        request.put("email", "vspochernin@gmail.com");
        request.put("password", "correctPassword123");


        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.errorType").value("EMAIL_EXISTS"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "il",
            "incorrectLoginnnnnnnnnnnnnnnnnnnnnnnnnnnn",
            "incorrect-login"})
    @DisplayName("Регистрация с некорректным логином")
    public void givenInvalidLogin_whenRegister_thenReturnsExpectedError(String login) throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("login", login);
        request.put("email", "vspochernin@gmail.com");
        request.put("password", "correctPassword123");

        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.errorType").value("INVALID_LOGIN"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "ip1&",
            "incorrectPassword1&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&",
            "123&*(",
            "qweqweqwe",
            "qwe123&*(±"})
    @DisplayName("Регистрация с некорректным паролем")
    public void givenInvalidPassword_whenRegister_thenReturnsExpectedError(String password) throws Exception {
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

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "incorrectEmailgmail.com",
            "incorrectEmail@gmailcom",
            "incorrectEmail@gmail."})
    @DisplayName("Регистрация с некорректными email")
    public void givenInvalidEmail_whenRegister_thenReturnsExpectedError(String email) throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("login", "correctLogin");
        request.put("email", email);
        request.put("password", "correctPassword123");

        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.errorType").value("INVALID_EMAIL"));
    }

    @Test
    @DisplayName("Аутентификация с корректными данными")
    public void givenCorrectCredentials_whenLogin_thenReturnsToken() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("login", "vrazukrantov");
        request.put("password", "qwe123");


        mockMvc.perform(post("/api/v1/login/tesseract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    @DisplayName("Аутентификация с несуществующим логином")
    public void givenNonExistentLogin_whenLogin_thenReturnsExpectedError() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("login", "qwadasiudasklj");
        request.put("password", "qwe123");


        mockMvc.perform(post("/api/v1/login/tesseract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.errorType").value("BAD_CREDENTIALS"));
    }

    @Test
    @DisplayName("Аутентификация с несуществующим паролем")
    public void givenNonExistentPassword_whenLogin_thenReturnsExpectedError() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("login", "vrazukrantov");
        request.put("password", "aidasahduhdwjkhda");


        mockMvc.perform(post("/api/v1/login/tesseract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.errorType").value("BAD_CREDENTIALS"));
    }

    @Test
    @DisplayName("Корректное изменение пароля")
    public void givenCorrectRequest_whenChangePassword_thenSuccess() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("oldPassword", "qwe123");
        request.put("newPassword", "newQwe123");

        mockMvc.perform(put("/api/v1/password")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        HashMap<String, String> incorrectAuthRequest = new HashMap<>();
        incorrectAuthRequest.put("login", "vrazukrantov");
        incorrectAuthRequest.put("password", "qwe123");


        mockMvc.perform(post("/api/v1/login/tesseract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectAuthRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.errorType").value("BAD_CREDENTIALS"));

        HashMap<String, String> correctAuthRequest = new HashMap<>();
        correctAuthRequest.put("login", "vrazukrantov");
        correctAuthRequest.put("password", "newQwe123");


        mockMvc.perform(post("/api/v1/login/tesseract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(correctAuthRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    @DisplayName("Изменение пароля, когда старый пароль не совпадает с тем, что лежит в базе")
    public void givenNonExistentOldPassword_whenChangePassword_thenReturnsExpectedError() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("oldPassword", "incorrectQwe123");
        request.put("newPassword", "newQwe123");

        mockMvc.perform(put("/api/v1/password")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.errorType").value("PASSWORD_DOES_NOT_MATCH"));
    }

    @Test
    @DisplayName("Изменение пароля, когда новый пароль некорректный")
    public void givenIncorrectNewPassword_whenChangePassword_thenReturnsExpectedError() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("oldPassword", "qwe123");
        request.put("newPassword", "incorrectPassword");

        mockMvc.perform(put("/api/v1/password")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.errorType").value("INVALID_PASSWORD"));
    }
}
