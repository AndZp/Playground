package io.mateam.playground.data.repo.model

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

/**
 * RepoSearchResult from a search, which contains LiveData<PagedList<Cryptocurrency>> holding query data,
 * and a LiveData<String> of network error state.
 */
data class CryptoSearchResult(
  val data: LiveData<PagedList<Cryptocurrency>>,
  val networkErrors: LiveData<String>
)