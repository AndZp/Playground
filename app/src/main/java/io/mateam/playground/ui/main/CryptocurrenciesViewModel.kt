package io.mateam.playground.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import io.mateam.playground.data.repo.CryptocurrencyRepository
import io.mateam.playground.data.repo.model.CryptoSearchResult
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.utils.LoadingStatus
import javax.inject.Inject

class CryptocurrenciesViewModel @Inject constructor(
  private val cryptocurrencyRepository: CryptocurrencyRepository
) : ViewModel() {

  private val repoResult = MutableLiveData<CryptoSearchResult>()

  val crypocurrencies: LiveData<PagedList<Cryptocurrency>> = Transformations.switchMap(
      repoResult,
      { it -> it.data })
  val networkErrors: LiveData<String> = Transformations.switchMap(repoResult,
      { it -> it.networkErrors })
  val loadingStatus: LiveData<LoadingStatus> = Transformations.switchMap(repoResult,
      { it -> it.loadingStatus })

  /**
   * Load Cryptocurrencies from repository
   */
  fun loadCryptocurrencies() {
    repoResult.postValue(cryptocurrencyRepository.getCryptocurrenciesSearchResult())
  }
}