package io.mateam.playground.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import io.mateam.playground.App
import io.mateam.playground.di.module.AppModule
import io.mateam.playground.di.module.MainActivityModule
import io.mateam.playground.di.module.NetModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, MainActivityModule::class, NetModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

  fun inject(app: App)

  override fun inject(instance: DaggerApplication)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }
}