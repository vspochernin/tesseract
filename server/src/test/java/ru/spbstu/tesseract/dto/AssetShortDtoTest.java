package ru.spbstu.tesseract.dto;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Company;
import ru.spbstu.tesseract.entity.Price;

import static org.assertj.core.api.Assertions.assertThat;

class AssetShortDtoTest {

    @Test
    public void givenAsset_whenFromAsset_thenReturnCorrectAssertShortDto() {
        Asset asset = Asset.builder()
                .id(1)
                .title("Asset title")
                .company(Company.builder()
                        .title("Company title")
                        .build())
                .prices(List.of(
                        Price.builder()
                                .id(1)
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build(),
                        Price.builder()
                                .id(2)
                                .price(50)
                                .setDatetime(ZonedDateTime.now().minusDays(1))
                                .build(),
                        Price.builder()
                                .id(3)
                                .price(200)
                                .setDatetime(ZonedDateTime.now().minusDays(2))
                                .build(),
                        Price.builder()
                                .id(4)
                                .price(300)
                                .setDatetime(ZonedDateTime.now().minusDays(3))
                                .build()))
                .build();
        Asset mockedAsset = Mockito.spy(asset);
        Mockito.doReturn(false).when(mockedAsset).isAssetFavourite();

        AssetShortDto actualAssetShortDto = AssetShortDto.fromAsset(mockedAsset);

        AssetShortDto expectedAssetShortDto = AssetShortDto.builder()
                .assetId(1)
                .assetTitle("Asset title")
                .companyTitle("Company title")
                .assetPrice(100)
                .assetPriceDiff(50)
                .favouriteStatus(false)
                .build();
        assertThat(actualAssetShortDto).isEqualTo(expectedAssetShortDto);
    }
}