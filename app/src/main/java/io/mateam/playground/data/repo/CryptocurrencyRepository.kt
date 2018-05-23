package io.mateam.playground.data.repo

import android.arch.paging.LivePagedListBuilder
import io.mateam.playground.data.local.CryptocurrenciesDao
import io.mateam.playground.data.remote.ApiInterface
import io.mateam.playground.data.repo.model.CryptoSearchResult
import io.mateam.playground.utils.Constants.Companion.DATABASE_PAGE_SIZE
import timber.log.Timber
import javax.inject.Inject

class CryptocurrencyRepository @Inject constructor(
  private val apiInterface: ApiInterface,
  private val cryptocurrenciesDao: CryptocurrenciesDao,
  private val mapper: CryptoModelMapper
) {

  /**
   * Search repositories whose names match the query.
   */
  fun getCryptoData(): CryptoSearchResult {
    Timber.d("New query")
    // Get data source factory from the local cache
    val dataSourceFactory = cryptocurrenciesDao.queryCryptocurrencies()

    // Construct the boundary callback
    val boundaryCallback = RepoBoundaryCallback(apiInterface, cryptocurrenciesDao, mapper)
    val networkErrors = boundaryCallback.networkErrors

    // Get the paged list
    val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
        .setBoundaryCallback(boundaryCallback)
        .build()

    // Get the network errors exposed by the boundary callback
    return CryptoSearchResult(data, networkErrors)
  }

}