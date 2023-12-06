package ru.tesseract.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import ru.tesseract.api.ApiPagingSource
import ru.tesseract.api.ApiResponse

private val DefaultPagingConfig = PagingConfig(pageSize = 10)

private fun <Key : Any, Value : Any> pager(
    pagingSourceFactory: () -> PagingSource<Key, Value>
) = Pager(
    config = DefaultPagingConfig,
    pagingSourceFactory = pagingSourceFactory,
)

fun <Value : Any> ViewModel.paginate(fetch: suspend (Int) -> ApiResponse<List<Value>>) =
    pager { ApiPagingSource(fetch) }.flow.cachedIn(viewModelScope)
