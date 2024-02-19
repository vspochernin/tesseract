@Test
void givenTooLittleAmount_whenCreateDiversification_thenTooLittleAmountThrown() {
    CreateDiversificationRequestDto request = reqeustWithAmount(99L);
    List<Asset> assets = getAssetsWithMinPrice100();
    when(assetRepository.findAll()).thenReturn(assets);

    assertThatThrownBy(() -> diversificationService.createDiversification(request))
            .isInstanceOf(TesseractException.class)
            .extracting("errorType")
            .isEqualTo(TesseractErrorType.TOO_LITTLE_AMOUNT);
}

@Test
void givenMinPossibleAmount_whenCreateDiversification_thenDiversificationIsCreated() {
    CreateDiversificationRequestDto request = reqeustWithAmount(100L);
    List<Asset> assets = getAssetsWithMinPrice100();
    when(assetRepository.findAll()).thenReturn(assets);

    diversificationService.createDiversification(request);

    verify(diversificationRepository).save(any(Diversification.class));
}

@Test
void givenCorrectAmount_whenCreateDiversification_thenDiversificationIsCreated() {
    CreateDiversificationRequestDto request = reqeustWithAmount(500_000L);
    List<Asset> assets = getAssetsWithMinPrice100();
    when(assetRepository.findAll()).thenReturn(assets);

    diversificationService.createDiversification(request);

    verify(diversificationRepository).save(any(Diversification.class));
}

@Test
void givenMaxPossibleAmount_whenCreateDiversification_thenDiversificationIsCreated() {
    CreateDiversificationRequestDto request = reqeustWithAmount(1_000_000_000L);
    List<Asset> assets = getAssetsWithMinPrice100();
    when(assetRepository.findAll()).thenReturn(assets);

    diversificationService.createDiversification(request);

    verify(diversificationRepository).save(any(Diversification.class));
}

@Test
void givenTooBigAmount_whenCreateDiversification_thenTooBigAmountThrown() {
    CreateDiversificationRequestDto request = reqeustWithAmount(1_000_000_001L);
    List<Asset> assets = getAssetsWithMinPrice100();
    when(assetRepository.findAll()).thenReturn(assets);

    assertThatThrownBy(() -> diversificationService.createDiversification(request))
            .isInstanceOf(TesseractException.class)
            .extracting("errorType")
            .isEqualTo(TesseractErrorType.TOO_BIG_AMOUNT);
}
