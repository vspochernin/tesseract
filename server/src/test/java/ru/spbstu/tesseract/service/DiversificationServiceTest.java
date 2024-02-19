package ru.spbstu.tesseract.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.spbstu.tesseract.entity.*;
import ru.spbstu.tesseract.dto.CreateDiversificationRequestDto;
import ru.spbstu.tesseract.repository.AssetRepository;
import ru.spbstu.tesseract.repository.DiversificationRepository;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static ru.spbstu.tesseract.entity.User.getCurrentUser;

@ExtendWith(MockitoExtension.class)
class DiversificationServiceTest {

    @Mock
    private DiversificationRepository diversificationRepository;

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private DiversificationService diversificationService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = User.builder().id(1).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUser, null);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void whenCreateDiversification_thenDiversificationIsCreated() {
        // Given
        CreateDiversificationRequestDto request = new CreateDiversificationRequestDto();
        request.setAmount(500_000); // Example amount
        request.setRiskTypeId(RiskType.COMBINED.ordinal()); // Assuming COMBINED is a valid RiskType

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

        when(assetRepository.findAll()).thenReturn(List.of(asset1, asset2));

        // When
        diversificationService.createDiversification(request);

        // Then
        verify(diversificationRepository).save(any(Diversification.class));
    }
}
