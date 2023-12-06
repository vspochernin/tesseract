package ru.spbstu.tesseract.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private int amount;
    @NotNull
    @Min(0)
    @Max(3)
    private int riskTypeId;
}
