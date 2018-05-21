package io.mateam.playground.di.module

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.mateam.playground.di.ViewModelKey
import io.mateam.playground.ui.main.CryptocurrenciesViewModel
import io.mateam.playground.ui.main.MainActivity

@Module
internal abstract class MainActivityModule {
  @ContributesAndroidInjector
  internal abstract fun mainActivity(): MainActivity

  @Binds
  @IntoMap
  @ViewModelKey(CryptocurrenciesViewModel::class) internal
  abstract fun bindCryptocurrenciesViewModel(viewModel: CryptocurrenciesViewModel): ViewModel
}