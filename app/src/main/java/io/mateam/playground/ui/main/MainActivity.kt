package io.mateam.playground.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.mateam.playground.R
import io.mateam.playground.utils.Constants
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  lateinit var viewModel: CryptocurrenciesViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(CryptocurrenciesViewModel::class.java)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.container, MainFragment.newInstance())
          .commitNow()
    }
    subscribeToViewModel()
  }

  override fun onResume() {
    super.onResume()
    loadData()
  }

  private fun subscribeToViewModel() {
    viewModel.cryptocurrenciesResult.observe(this, Observer {
      Timber.d("cryptocurrenciesResult.observe: list size = ${it?.size}")
    })

    viewModel.cryptocurrenciesError.observe(this, Observer {
      Timber.e("cryptocurrenciesError.observe: $it")
    })

    viewModel.cryptocurrenciesLoader().observe(this, Observer<Boolean> {
      Timber.e("cryptocurrenciesLoader().observe: isLoading = $it")
    })
  }

  private fun loadData() {
    viewModel.loadCryptocurrencies(Constants.LIMIT, 0 * Constants.OFFSET)
  }

  override fun onDestroy() {
    viewModel.disposeElements()
    super.onDestroy()
  }

}
