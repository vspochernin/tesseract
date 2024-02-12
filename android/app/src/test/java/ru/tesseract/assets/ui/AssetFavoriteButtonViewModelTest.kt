package ru.tesseract.assets.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Test
import ru.tesseract.api.ApiResponse
import ru.tesseract.assets.api.AssetsApi
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class AssetFavoriteButtonViewModelTest {
    @Test
    fun `AssetFavoriteButtonViewModel should call add`() {
        val api = mockk<AssetsApi>()
        coEvery { api.addFavorite(any()) } returns ApiResponse.Success(Unit)
        val viewModel = AssetFavoriteButtonViewModel(0, false, api)
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onClickFavorite().join()
        }
        coVerify(exactly = 1) { api.addFavorite(0) }
        confirmVerified(api)
    }

    @Test
    fun `AssetFavoriteButtonViewModel should call remove`() {
        val api = mockk<AssetsApi>()
        coEvery { api.removeFavorite(any()) } returns ApiResponse.Success(Unit)
        val viewModel = AssetFavoriteButtonViewModel(0, true, api)
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onClickFavorite().join()
        }
        coVerify(exactly = 1) { api.removeFavorite(0) }
        confirmVerified(api)
    }

    @Test
    fun `AssetFavoriteButtonViewModel should not change on failure`() {
        val api = mockk<AssetsApi>()
        coEvery { api.addFavorite(any()) } returns ApiResponse.NetworkError(Exception())
        val viewModel = AssetFavoriteButtonViewModel(0, false, api)
        Dispatchers.setMain(Dispatchers.Default)
        runTest {
            viewModel.onClickFavorite().join()
        }
        coVerify(exactly = 1) { api.addFavorite(0) }
        confirmVerified(api)
    }
}
