package io.mateam.playground.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.mateam.playground.data.model.Cryptocurrency

@Database(entities = arrayOf(Cryptocurrency::class), version = 1)
abstract class Database : RoomDatabase() {
  abstract fun cryptocurrenciesDao(): CryptocurrenciesDao
}