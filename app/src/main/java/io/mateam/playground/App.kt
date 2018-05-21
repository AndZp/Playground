package io.mateam.playground

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.mateam.playground.di.component.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : DaggerApplication() {

  override fun onCreate() {
    super.onCreate()
    initStetho()
    initTimberLog()
  }

  private fun initStetho() {
    Stetho.initializeWithDefaults(this)
  }

  private fun initTimberLog() {
    if (BuildConfig.DEBUG)
      Timber.plant(DebugTree())
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    val builder = DaggerAppComponent.builder().create(this)
    builder.inject(this)
    return builder
  }
}