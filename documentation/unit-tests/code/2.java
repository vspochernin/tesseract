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
