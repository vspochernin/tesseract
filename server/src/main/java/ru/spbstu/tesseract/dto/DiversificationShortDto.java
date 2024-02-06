package ru.spbstu.tesseract.dto;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Diversification;

@Data
@Builder
public class DiversificationShortDto {

    private int diversificationId;
    private String createDatetime;
    private int riskTypeId;
    private int currentAmount;
    private int amountDiff;

    public static DiversificationShortDto fromDiversification(Diversification diversification) {
        return DiversificationShortDto.builder()
                .diversificationId(diversification.getId())
                .createDatetime(diversification.getCreateDatetime().toString())
                .riskTypeId(diversification.getRiskType().ordinal())
                .currentAmount(diversification.getCurrentAmount())
                .amountDiff(diversification.getCurrentAmount() - diversification.getAmount())
                .build();
    }
}
