package ru.spbstu.tesseract.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull
    private String login;
    @NotNull
    // @Email TODO: add annotation in prod.
    private String email;
    @NotNull
    private String password;
}
