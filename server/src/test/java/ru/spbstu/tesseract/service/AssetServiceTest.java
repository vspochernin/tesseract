package ru.spbstu.tesseract.service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import ru.spbstu.tesseract.dto.AssetLongDto;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Company;
import ru.spbstu.tesseract.entity.Operator;
import ru.spbstu.tesseract.entity.Price;
import ru.spbstu.tesseract.entity.RiskType;
import ru.spbstu.tesseract.repository.AssetRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    @Test
    void givenValidPageParams_whenGetAssets_thenReturnListOfAssetShortDto() {
        Asset asset1 = Asset.builder()
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
                                .build()))
                .build();
        Asset asset2 = Asset.builder()
                .id(2)
                .title("Asset title")
                .company(Company.builder()
                        .title("Company title")
                        .build())
                .prices(List.of(
                        Price.builder()
                                .id(1)
                                .price(100)
                                .setDatetime(ZonedDateTime.now())
                                .build()))
                .build();
        Asset mockedAsset1 = Mockito.spy(asset1);
        Mockito.doReturn(false).when(mockedAsset1).isAssetFavourite();
        Asset mockedAsset2 = Mockito.spy(asset2);
        Mockito.doReturn(false).when(mockedAsset2).isAssetFavourite();
        Slice<Asset> assetsSlice = new PageImpl<>(List.of(mockedAsset1, mockedAsset2));
        when(assetRepository.findBy(PageRequest.of(0, 10))).thenReturn(assetsSlice);

        List<AssetShortDto> result = assetService.getAssets(0, 10);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }

    @Test
    void givenNoAssets_whenGetAssets_thenReturnEmptyList() {
        Slice<Asset> assetsSlice = new PageImpl<>(Collections.emptyList());
        when(assetRepository.findBy(PageRequest.of(0, 10))).thenReturn(assetsSlice);

        List<AssetShortDto> result = assetService.getAssets(0, 10);

        assertThat(result).isEmpty();
    }

    @Test
    void givenValidAssetId_whenGetAsset_thenReturnAssetLongDto() {
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
        when(assetRepository.findById(1)).thenReturn(Optional.of(mockedAsset));

        AssetLongDto result = assetService.getAsset(1);

        assertThat(result).isNotNull();
        assertThat(result).extracting(AssetLongDto::getAssetId).isEqualTo(1);
    }

    @Test
    void givenInvalidAssetId_whenGetAsset_thenThrowNoSuchElementException() {
        when(assetRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> assetService.getAsset(999))
                .isInstanceOf(NoSuchElementException.class);
    }
}