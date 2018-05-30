package io.mateam.playground

import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.fabric.sdk.android.Fabric
import io.mateam.playground.di.component.DaggerAppComponent
import io.mateam.playground.utils.CrashReportingTree
import timber.log.Timber

class App : DaggerApplication() {

  override fun onCreate() {
    super.onCreate()
    initStetho()
    initTimberWithCrashlitics()
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

  private fun initTimberWithCrashlitics() {
    Timber.plant(
        if (BuildConfig.DEBUG)
          Timber.DebugTree()
        else
          CrashReportingTree()
    )
    Fabric.with(this, Crashlytics())
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    val builder = DaggerAppComponent.builder().create(this)
    builder.inject(this)
    return builder
  }
}