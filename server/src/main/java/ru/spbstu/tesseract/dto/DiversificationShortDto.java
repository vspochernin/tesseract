package ru.spbstu.tesseract.dto;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Diversification;

@Data
@Builder
public class DiversificationShortDto {

    public static DiversificationShortDto fromDiversification(Diversification diversification) {
        return DiversificationShortDto.builder()
                .diversificationId(diversification.getId())
                .createDatetime(diversification.convertCreateDatetimeToString())
                .riskTypeId(diversification.getRiskType().ordinal())
                .amount(diversification.getAmount())
                .build();
    }

    private Integer diversificationId;
    private String createDatetime;
    private Integer riskTypeId;
    private Integer amount;
}
