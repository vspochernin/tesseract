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
public class CreateDiversificationRequestDto {

    @NotNull
    private Long amount;
    @NotNull
    private Integer riskTypeId;
}
