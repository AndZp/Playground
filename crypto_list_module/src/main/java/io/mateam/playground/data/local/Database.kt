package io.mateam.playground.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.mateam.playground.data.repo.model.Cryptocurrency

/**
 * Database schema that holds the list of [Cryptocurrency].
 */
@Database(
    entities = [(Cryptocurrency::class)],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
  abstract fun cryptocurrenciesDao(): CryptocurrenciesDao
}