package io.mateam.playground.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.mateam.playground.data.CryptocurrencyRepository
import io.mateam.playground.data.local.model.Cryptocurrency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class CryptocurrenciesViewModel @Inject constructor(
  private val cryptocurrencyRepository: CryptocurrencyRepository
) : ViewModel() {

  var cryptocurrenciesResult: MutableLiveData<List<Cryptocurrency>> = MutableLiveData()
  var cryptocurrenciesError: MutableLiveData<String> = MutableLiveData()
  var cryptocurrenciesLoader: MutableLiveData<Boolean> = MutableLiveData()

  lateinit var disposableObserver: DisposableObserver<List<Cryptocurrency>>

  fun loadCryptocurrencies(
    limit: Int,
    offset: Int
  ) {

    disposableObserver = object : DisposableObserver<List<Cryptocurrency>>() {
      override fun onComplete() {

      }

      override fun onNext(cryptocurrencies: List<Cryptocurrency>) {
        cryptocurrenciesResult.postValue(cryptocurrencies)
        cryptocurrenciesLoader.postValue(false)
      }

      override fun onError(e: Throwable) {
        Timber.e(e)
        cryptocurrenciesError.postValue(e.message)
        cryptocurrenciesLoader.postValue(false)
      }
    }

    cryptocurrencyRepository.getCryptocurrencies(limit, offset)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .debounce(400, MILLISECONDS)
        .subscribe(disposableObserver)
  }

  fun disposeElements() {
    if (disposableObserver != null && !disposableObserver.isDisposed) disposableObserver.dispose()
  }

}