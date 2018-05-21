package io.mateam.playground

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.mateam.playground.di.component.DaggerAppComponent

class App : DaggerApplication() {

  override fun onCreate() {
    super.onCreate()
    Stetho.initializeWithDefaults(this)

  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    val builder = DaggerAppComponent.builder().application(this).build()
    builder.inject(this)
    return builder
  }
}