package io.mateam.playground.di.module

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import io.mateam.playground.data.local.CryptocurrenciesDao
import io.mateam.playground.data.local.Database
import javax.inject.Singleton

@Module
class DbModule() {

  @Provides
  @Singleton
  fun provideCryptocurrenciesDatabase(app: Application): Database = Room.databaseBuilder(
      app,
      Database::class.java, "cryptocurrencies_db"
  ).fallbackToDestructiveMigration()
      .build()

  @Provides
  @Singleton
  fun provideCryptocurrenciesDao(database: Database): CryptocurrenciesDao = database.cryptocurrenciesDao()
}