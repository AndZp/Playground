package io.mateam.playground.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.mateam.playground.App
import io.mateam.playground.di.ViewModelBuilder
import io.mateam.playground.di.module.AppModule
import io.mateam.playground.di.module.DbModule
import io.mateam.playground.di.module.MainActivityModule
import io.mateam.playground.di.module.NetModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      AndroidSupportInjectionModule::class,
      AppModule::class,
      MainActivityModule::class,
      NetModule::class,
      ViewModelBuilder::class,
      DbModule::class]
)

interface AppComponent : AndroidInjector<App> {

  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<App>()
}