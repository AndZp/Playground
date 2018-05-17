package io.mateam.playground.di.module

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.mateam.playground.di.ViewModelFactory
import io.mateam.playground.ui.main.MainActivity

@Module
internal abstract class MainActivityModule {
  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @ContributesAndroidInjector
/*(modules = [JobsFragmentModule::class])*/
  internal abstract fun contributeMainActivity(): MainActivity
}