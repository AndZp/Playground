package io.mateam.playground.di.module

import android.content.Context
import dagger.Binds
import dagger.Module
import io.mateam.playground.App

@Module
abstract class AppModule {

  @Binds
  abstract fun provideContext(application: App): Context

}
