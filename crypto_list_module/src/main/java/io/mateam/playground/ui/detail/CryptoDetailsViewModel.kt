package io.mateam.playground.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import io.mateam.playground.data.repo.CryptocurrencyRepository
import io.mateam.playground.data.repo.model.CryptoQueryResult
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.utils.LoadingStatus
import javax.inject.Inject

class CryptoDetailsViewModel @Inject constructor(
  private val cryptocurrencyRepository: CryptocurrencyRepository
) : ViewModel() {

  private val cryptoDetailsResult = MutableLiveData<CryptoQueryResult>()

  val cryptoData: LiveData<Cryptocurrency> = Transformations.switchMap(
      cryptoDetailsResult,
      { it -> it.data })
  val networkErrors: LiveData<String> = Transformations.switchMap(cryptoDetailsResult,
      { it -> it.networkErrors })
  val loadingStatus: LiveData<LoadingStatus> = Transformations.switchMap(cryptoDetailsResult,
      { it -> it.loadingStatus })

  /**
   * Load Cryptocurrency from repository by ID
   */
  fun loadCryptocurrencyById(id: Int) {
    cryptoDetailsResult.postValue(cryptocurrencyRepository.getCryptocurrenciesById(id))
  }
}
