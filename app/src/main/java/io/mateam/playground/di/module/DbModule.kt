package io.mateam.playground.di.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import io.mateam.playground.App
import io.mateam.playground.data.local.CryptocurrenciesDao
import io.mateam.playground.data.local.Database
import io.mateam.playground.data.remote.ApiInterface
import io.mateam.playground.data.repo.CryptoModelMapper
import io.mateam.playground.data.repo.CryptocurrencyRepository
import io.mateam.playground.utils.Constants.Companion.DATABASE_NAME
import io.mateam.playground.utils.Utils
import javax.inject.Singleton

@Module
class DbModule {

  @Provides
  @Singleton
  fun provideCryptocurrenciesDatabase(app: App): Database =
    Room.databaseBuilder(
        app,
        Database::class.java, DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

  @Provides
  @Singleton
  fun provideCryptocurrenciesDao(database: Database): CryptocurrenciesDao = database.cryptocurrenciesDao()

  @Provides
  @Singleton
  fun provideCryptoModelTransformer(utils: Utils): CryptoModelMapper = CryptoModelMapper(utils)

  @Provides
  @Singleton
  fun provideCryptocurrenciesRepository(
    api: ApiInterface,
    cryptoDao: CryptocurrenciesDao,
    mapper: CryptoModelMapper
  ): CryptocurrencyRepository = CryptocurrencyRepository(
      api, cryptoDao, mapper
  )

  @Provides
  @Singleton
  fun provideUtils(context: Context): Utils = Utils(context)
}