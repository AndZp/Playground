package io.mateam.playground.data.repo.model

import android.arch.lifecycle.LiveData
import io.mateam.playground.utils.LoadingStatus

/**
 * RepoSearchResult from a query, which contains LiveData<Cryptocurrency> holding query data,
 * and a LiveData<String> of network error state.
 */
data class CryptoQueryResult(
  val data: LiveData<Cryptocurrency>,
  val networkErrors: LiveData<String>,
  val loadingStatus: LiveData<LoadingStatus>
)