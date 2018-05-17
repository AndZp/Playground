package io.mateam.playground

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.mateam.playground.di.component.DaggerAppComponent

class App : DaggerApplication() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    val builder = DaggerAppComponent.builder().application(this).build()
    builder.inject(this)
    return builder
  }
}