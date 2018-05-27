package io.mateam.playground.di.module

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.mateam.playground.di.ViewModelKey
import io.mateam.playground.ui.main.MainActivity
import io.mateam.playground.ui.main.detail.CryptoDetailsFragment
import io.mateam.playground.ui.main.detail.CryptoDetailsViewModel
import io.mateam.playground.ui.main.list.CryptoListFragment
import io.mateam.playground.ui.main.list.CryptoListViewModel

@Module
internal abstract class MainActivityModule {
  @ContributesAndroidInjector
  internal abstract fun mainActivity(): MainActivity


  @ContributesAndroidInjector
  internal abstract fun cryptoListFragment(): CryptoListFragment

  @Binds
  @IntoMap
  @ViewModelKey(CryptoListViewModel::class) internal
  abstract fun bindCryptoListViewModel(viewModel: CryptoListViewModel): ViewModel

  @ContributesAndroidInjector
  internal abstract fun cryptoDetailsFragment(): CryptoDetailsFragment

  @Binds
  @IntoMap
  @ViewModelKey(CryptoDetailsViewModel::class) internal
  abstract fun bindCryptoDetailsViewModel(viewModel: CryptoDetailsViewModel): ViewModel
}