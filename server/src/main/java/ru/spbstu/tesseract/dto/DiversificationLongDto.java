package ru.spbstu.tesseract.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Diversification;

@Data
@Builder
public class DiversificationLongDto {

    private String createDateTime;
    private int currentAmount;
    private int riskTypeId;
    private List<AssetDiversificationDto> assetList;
    private int amountDiff;

    public static DiversificationLongDto fromDiversification(Diversification diversification) {
        List<AssetDiversificationDto> assets = diversification.getDiversificationAssetSet().stream()
                .map(AssetDiversificationDto::fromDiversificationAsset)
                .toList();

        return DiversificationLongDto.builder()
                .createDateTime(diversification.getCreateDatetime().toString())
                .currentAmount(diversification.getCurrentAmount())
                .riskTypeId(diversification.getRiskType().ordinal())
                .assetList(assets)
                .amountDiff(diversification.getCurrentAmount() - diversification.getAmount())
                .build();
    }
}
