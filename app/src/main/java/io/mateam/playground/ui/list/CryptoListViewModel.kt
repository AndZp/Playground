package io.mateam.playground.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import io.mateam.playground.data.repo.CryptocurrencyRepository
import io.mateam.playground.data.repo.model.CryptoListQueryResult
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.utils.LoadingStatus
import javax.inject.Inject

class CryptoListViewModel @Inject constructor(
  private val cryptocurrencyRepository: CryptocurrencyRepository
) : ViewModel() {

  private val queryLiveData = MutableLiveData<String>()

  private val cryptoQueryResults: LiveData<CryptoListQueryResult> = Transformations.map(queryLiveData, {
    cryptocurrencyRepository.queryCryptocurrenciesByName(it)
  })

  val crypocurrencies: LiveData<PagedList<Cryptocurrency>> =
    Transformations.switchMap(cryptoQueryResults, { it -> it.data })

  val networkErrors: LiveData<String> = Transformations.switchMap(
      cryptoQueryResults,
      { it -> it.networkErrors })
  val loadingStatus: LiveData<LoadingStatus> = Transformations.switchMap(
      cryptoQueryResults,
      { it -> it.loadingStatus })

  /**
   * Load Cryptocurrencies from repository
   */
  fun loadCryptocurrencies(query: String) {
    if (!query.equals(queryLiveData.value, true)) {
      queryLiveData.postValue(query)
    }
  }

  /**
   * Get the last query value.
   */
  fun lastQueryValue(): String? {
    val value = queryLiveData.value
    return value
  }


}