package io.mateam.playground.data.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import io.mateam.playground.data.local.CryptocurrenciesDao
import io.mateam.playground.data.remote.ApiInterface
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.utils.Constants.Companion.NETWORK_PAGE_SIZE
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RepoBoundaryCallback(
  private val apiInterface: ApiInterface,
  private val cache: CryptocurrenciesDao,
  private val mapper: CryptoModelMapper

) : PagedList.BoundaryCallback<Cryptocurrency>() {

  // keep the last requested page.
// When the request is successful, increment the page number.
  private var lastRequestedPage = 0

  private val _networkErrors = MutableLiveData<String>()
  // LiveData of network errors.
  val networkErrors: LiveData<String>
    get() = _networkErrors

  // avoid triggering multiple requests in the same time
  private var isRequestInProgress = false

  override fun onZeroItemsLoaded() {
    requestAndSaveData()
  }

  override fun onItemAtEndLoaded(itemAtEnd: Cryptocurrency) {
    requestAndSaveData()
  }

  private fun requestAndSaveData() {
    if (isRequestInProgress) return

    isRequestInProgress = true

    apiInterface
        .getCryptocurrencies(
            lastRequestedPage * NETWORK_PAGE_SIZE,
            NETWORK_PAGE_SIZE
        )
        .subscribeOn(Schedulers.newThread())
        .map { mapper.toDbFormatFromApi(it) }
        .subscribe(object : DisposableObserver<List<Cryptocurrency>>() {
          override fun onComplete() {
            Timber.d("API Request complete")
          }

          override fun onNext(cryptocurrencies: List<Cryptocurrency>) {
            Timber.d("API Request onNext* ${cryptocurrencies.size})")
            cache.insertAllCryptocurrencies(cryptocurrencies)
            lastRequestedPage++
            isRequestInProgress = false
          }

          override fun onError(e: Throwable) {
            Timber.e(e)
            _networkErrors.postValue(e.message)
            isRequestInProgress = false
          }
        })
  }
}