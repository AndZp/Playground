package io.mateam.playground.ui.main

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.mateam.playground.R
import io.mateam.playground.data.model.Cryptocurrency
import io.mateam.playground.data.remote.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var context: Context
  @Inject lateinit var api: ApiInterface

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.container, MainFragment.newInstance())
          .commitNow()
    }

    api.getCryptocurrencies("a").subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .debounce(400, MILLISECONDS)
        .subscribe(object : DisposableObserver<List<Cryptocurrency>>() {
          override fun onComplete() {
            Timber.d("Complete")

          }

          override fun onNext(cryptocurrencies: List<Cryptocurrency>) {
            cryptocurrencies.forEach { Timber.d("Received: $it") }
          }

          override fun onError(e: Throwable) {
            Timber.e(e)
          }
        })

  }

}
