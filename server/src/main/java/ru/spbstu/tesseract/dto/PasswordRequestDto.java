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
public class PasswordRequestDto {

    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
