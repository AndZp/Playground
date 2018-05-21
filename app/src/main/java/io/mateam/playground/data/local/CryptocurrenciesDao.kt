package io.mateam.playground.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.mateam.playground.data.model.Cryptocurrency
import io.reactivex.Single

@Dao
interface CryptocurrenciesDao {

  @Query("SELECT * FROM cryptocurrencies ORDER BY rank limit :limit offset :offset")
  fun queryCryptocurrencies(
    limit: Int,
    offset: Int
  ): Single<List<Cryptocurrency>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCryptocurrency(cryptocurrency: Cryptocurrency)

  @Insert(
      onConflict = OnConflictStrategy.REPLACE
  )
  fun insertAllCryptocurrencies(cryptocurrencies: List<Cryptocurrency>)
}