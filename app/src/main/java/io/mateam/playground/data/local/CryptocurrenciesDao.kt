package io.mateam.playground.data.local

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.mateam.playground.data.repo.model.Cryptocurrency

/**
 * Room data access object for accessing the [Cryptocurrency] table.
 */
@Dao
interface CryptocurrenciesDao {

  @Query("SELECT * FROM cryptocurrencies ORDER BY rank")
  fun queryCryptocurrencies(): DataSource.Factory<Int, Cryptocurrency>

  @Query("SELECT * FROM cryptocurrencies WHERE id = :id ")
  fun queryCryptocurrencyById(id: Int): LiveData<Cryptocurrency>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCryptocurrency(cryptocurrency: Cryptocurrency)

  @Insert(
      onConflict = OnConflictStrategy.REPLACE
  )
  fun insertAllCryptocurrencies(cryptocurrencies: List<Cryptocurrency>)
}