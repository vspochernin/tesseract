package ru.spbstu.tesseract.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotNull
    private String login;
    @NotNull
    // @Email TODO: add annotation in prod.
    private String email;
    @NotNull
    private String password;
}
