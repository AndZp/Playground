package io.mateam.playground.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.mateam.playground.R
import io.mateam.playground.data.CryptocurrencyRepository
import io.mateam.playground.data.model.Cryptocurrency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
  @Inject lateinit var repository: CryptocurrencyRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.container, MainFragment.newInstance())
          .commitNow()
    }
  }

  override fun onResume() {
    super.onResume()
    updateCrypto()
  }

  private fun updateCrypto() {
    val disposableObserver: DisposableObserver<List<Cryptocurrency>> = object : DisposableObserver<List<Cryptocurrency>>() {
      override fun onComplete() {
        Timber.d("getCryptocurrencies: Complete")
      }

      override fun onNext(cryptocurrencies: List<Cryptocurrency>) {
        Timber.d("getCryptocurrencies: onNext - list size = ${cryptocurrencies.size}")
        Timber.d("First received crypto: ${cryptocurrencies.first()}")
      }

      override fun onError(e: Throwable) {
        Timber.e(e)
      }
    }

    repository.getCryptocurrencies(50, 0)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(disposableObserver)
  }

}
