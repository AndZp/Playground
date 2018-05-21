package io.mateam.playground.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelBuilder {

  @Binds
  internal abstract fun bindViewModelFactory(factoryCrypto: CryptoViewModelFactory):
      ViewModelProvider.Factory
}