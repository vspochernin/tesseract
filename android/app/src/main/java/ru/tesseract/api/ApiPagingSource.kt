package ru.tesseract.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.tesseract.KoverIgnore

@KoverIgnore
class ApiPagingSource<Data: Any>(
    private val fetch: suspend (page: Int) -> ApiResponse<List<Data>>
) : PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val page = params.key ?: 0
        return when (val response = fetch(page)) {
            is ApiResponse.Success -> {
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (page == 0) null else page.minus(1),
                    nextKey = if (response.data.isEmpty()) null else page.plus(1),
                )
            }

            is ApiResponse.NetworkError -> {
                LoadResult.Error(response.exception)
            }

            is ApiResponse.ApiError -> {
                LoadResult.Invalid()
            }
        }
    }
}
