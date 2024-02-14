package ru.tesseract.assets.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import ru.tesseract.api.ApiResponse
import ru.tesseract.assets.api.AssetsApi
import ru.tesseract.util.MainDispatcherRule

class AssetFavoriteButtonViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `AssetFavoriteButtonViewModel should call add`() = runTest {
        val api = mockk<AssetsApi>()
        coEvery { api.addFavorite(any()) } returns ApiResponse.Success(Unit)
        val viewModel = AssetFavoriteButtonViewModel(0, false, api)
        viewModel.onClickFavorite().join()
        coVerify(exactly = 1) { api.addFavorite(0) }
        confirmVerified(api)
    }

    @Test
    fun `AssetFavoriteButtonViewModel should call remove`() = runTest {
        val api = mockk<AssetsApi>()
        coEvery { api.removeFavorite(any()) } returns ApiResponse.Success(Unit)
        val viewModel = AssetFavoriteButtonViewModel(0, true, api)
        viewModel.onClickFavorite().join()
        coVerify(exactly = 1) { api.removeFavorite(0) }
        confirmVerified(api)
    }

    @Test
    fun `AssetFavoriteButtonViewModel should not change on failure`() = runTest {
        val api = mockk<AssetsApi>()
        coEvery { api.addFavorite(any()) } returns ApiResponse.NetworkError(Exception())
        val viewModel = AssetFavoriteButtonViewModel(0, false, api)
        viewModel.onClickFavorite().join()
        coVerify(exactly = 1) { api.addFavorite(0) }
        confirmVerified(api)
    }
}
