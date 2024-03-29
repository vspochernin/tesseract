package ru.spbstu.tesseract.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.spbstu.tesseract.dto.CreatePortfolioRequestDto;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Company;
import ru.spbstu.tesseract.entity.Portfolio;
import ru.spbstu.tesseract.entity.Operator;
import ru.spbstu.tesseract.entity.Price;
import ru.spbstu.tesseract.entity.RiskType;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.exception.TesseractErrorType;
import ru.spbstu.tesseract.exception.TesseractException;
import ru.spbstu.tesseract.repository.AssetRepository;
import ru.spbstu.tesseract.repository.PortfolioRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    @BeforeEach
    void setUp() {
        User mockUser = User.builder().id(1).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUser, null);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void givenTooLittleAmount_whenCreatePortfolio_thenTooLittleAmountThrown() {
        CreatePortfolioRequestDto request = reqeustWithAmount(99L);
        List<Asset> assets = getAssetsWithMinPrice100();
        when(assetRepository.findAll()).thenReturn(assets);

        assertThatThrownBy(() -> portfolioService.createPortfolio(request))
                .isInstanceOf(TesseractException.class)
                .extracting("errorType")
                .isEqualTo(TesseractErrorType.TOO_LITTLE_AMOUNT);
    }

    @Test
    void givenMinPossibleAmount_whenCreatePortfolio_thenPortfolioIsCreated() {
        CreatePortfolioRequestDto request = reqeustWithAmount(100L);
        List<Asset> assets = getAssetsWithMinPrice100();
        when(assetRepository.findAll()).thenReturn(assets);

        portfolioService.createPortfolio(request);

        verify(portfolioRepository).save(any(Portfolio.class));
    }

    @Test
    void givenCorrectAmount_whenCreatePortfolio_thenPortfolioIsCreated() {
        CreatePortfolioRequestDto request = reqeustWithAmount(500_000L);
        List<Asset> assets = getAssetsWithMinPrice100();
        when(assetRepository.findAll()).thenReturn(assets);

        portfolioService.createPortfolio(request);

        verify(portfolioRepository).save(any(Portfolio.class));
    }

    @Test
    void givenMaxPossibleAmount_whenCreatePortfolio_thenPortfolioIsCreated() {
        CreatePortfolioRequestDto request = reqeustWithAmount(1_000_000_000L);
        List<Asset> assets = getAssetsWithMinPrice100();
        when(assetRepository.findAll()).thenReturn(assets);

        portfolioService.createPortfolio(request);

        verify(portfolioRepository).save(any(Portfolio.class));
    }

    @Test
    void givenTooBigAmount_whenCreatePortfolio_thenTooBigAmountThrown() {
        CreatePortfolioRequestDto request = reqeustWithAmount(1_000_000_001L);
        List<Asset> assets = getAssetsWithMinPrice100();
        when(assetRepository.findAll()).thenReturn(assets);

        assertThatThrownBy(() -> portfolioService.createPortfolio(request))
                .isInstanceOf(TesseractException.class)
                .extracting("errorType")
                .isEqualTo(TesseractErrorType.TOO_BIG_AMOUNT);
    }

    private static List<Asset> getAssetsWithMinPrice100() {
        Asset asset1 = Asset.builder()
                .id(1)
                .title("Asset title 1")
                .releaseDatetime(ZonedDateTime.now())
                .description("Asset description 1")
                .interest(10.0)
                .company(Company.builder()
                        .title("Company title 1")
                        .foundationDatetime(ZonedDateTime.now())
                        .description("Company description 1")
                        .build())
                .operator(Operator.builder()
                        .title("Operator title 1")
                        .inclusionDatetime(ZonedDateTime.now())
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
                                .build()))
                .build();

        Asset asset2 = Asset.builder()
                .id(2)
                .title("Asset title 2")
                .releaseDatetime(ZonedDateTime.now())
                .description("Asset description 2")
                .interest(20.0)
                .company(Company.builder()
                        .title("Company title 2")
                        .foundationDatetime(ZonedDateTime.now())
                        .description("Company description 2")
                        .build())
                .operator(Operator.builder()
                        .title("Operator title 2")
                        .inclusionDatetime(ZonedDateTime.now())
                        .build())
                .prices(List.of(
                        Price.builder()
                                .id(3)
                                .price(200)
                                .setDatetime(ZonedDateTime.now())
                                .build(),
                        Price.builder()
                                .id(4)
                                .price(150)
                                .setDatetime(ZonedDateTime.now().minusDays(1))
                                .build()))
                .build();
        return List.of(asset1, asset2);
    }

    private static CreatePortfolioRequestDto reqeustWithAmount(long amount) {
        CreatePortfolioRequestDto request = new CreatePortfolioRequestDto();
        request.setAmount(amount);
        request.setRiskTypeId(RiskType.COMBINED.ordinal());
        return request;
    }
}
