package io.mateam.playground.ui

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import dagger.android.support.DaggerAppCompatActivity
import io.mateam.playground.R

class MainActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
  }

  override fun onSupportNavigateUp() = findNavController(this, R.id.my_nav_host_fragment).navigateUp()
}
