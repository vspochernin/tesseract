package ru.spbstu.tesseract.dto;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Company;
import ru.spbstu.tesseract.entity.Operator;
import ru.spbstu.tesseract.entity.Price;
import ru.spbstu.tesseract.entity.RiskType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author pochernin-vla
 */
class AssetLongDtoTest {

    @Test
    public void givenAsset_whenFromAsset_thenReturnCorrectAssetLongDto() {
        Asset asset = Asset.builder()
                .id(1)
                .title("Asset title")
                .description("Asset description")
                .company(Company.builder()
                        .title("Company title")
                        .description("Company description")
                        .build())
                .operator(Operator.builder()
                        .title("Operator title")
                        .build())
                .prices(List.of(
                        Price.builder()
                                .id(1)
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build(),
                        Price.builder()
                                .id(1)
                                .price(50)
                                .setDatetime(ZonedDateTime.now().minusDays(1))
                                .build()))
                .build();
        Asset mockedAsset = Mockito.spy(asset);
        Mockito.doReturn(false).when(mockedAsset).isAssetFavourite();
        Mockito.doReturn(RiskType.COMBINED).when(mockedAsset).getRiskType();

        AssetLongDto actualAssetLongDto = AssetLongDto.fromAsset(mockedAsset);

        AssetLongDto expectedAssetLongDto = AssetLongDto.builder()
                .assetId(1)
                .assetTitle("Asset title")
                .assetDescription("Asset description")
                .assetPrice(100)
                .assetPriceDiff(50)
                .companyTitle("Company title")
                .companyDescription("Company description")
                .riskTypeId(3)
                .operatorTitle("Operator title")
                .build();
        assertThat(actualAssetLongDto).isEqualTo(expectedAssetLongDto);
    }
}