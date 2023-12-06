package ru.spbstu.tesseract.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import ru.spbstu.tesseract.entity.Diversification;

@Data
@Builder
public class DiversificationLongDto {

    public static DiversificationLongDto fromDiversification(Diversification diversification) {
        List<AssetDiversificationDto> assets = diversification.getDiversificationAssetSet().stream()
                .map(AssetDiversificationDto::fromDiversificationAsset)
                .toList();

        return DiversificationLongDto.builder()
                .createDateTime(diversification.getCreateDatetime().toString())
                .amount(diversification.getAmount())
                .riskTypeId(diversification.getRiskType().ordinal())
                .assetList(assets)
                .build();
    }

    private String createDateTime;
    private int amount;
    private int riskTypeId;
    private List<AssetDiversificationDto> assetList;
}
