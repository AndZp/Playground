package io.mateam.playground.ui.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.mateam.playground.R
import io.mateam.playground.ui.main.detail.CryptoDetailsFragment
import io.mateam.playground.ui.main.list.CryptoListFragment

class MainActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.container, CryptoListFragment.newInstance())
          .commitNow()
    }
  }

  /** Shows the cryptocurrency detail fragment */
  fun show(cryptoId: Int) {
    val cryptoDetailsFragment = CryptoDetailsFragment.newInstance(cryptoId)

    supportFragmentManager
        .beginTransaction()
        .addToBackStack("project")
        .replace(
            R.id.container,
            cryptoDetailsFragment, null
        ).commit()
  }
}
