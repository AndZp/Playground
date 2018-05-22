package io.mateam.playground

import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
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
    initLeakCanary()
  }

  private fun initLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return
    }
    LeakCanary.install(this)
    // Normal app init code...
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