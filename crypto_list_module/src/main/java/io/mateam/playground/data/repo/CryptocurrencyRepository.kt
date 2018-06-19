package io.mateam.playground.data.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import io.mateam.playground.data.local.CryptocurrenciesDao
import io.mateam.playground.data.remote.ApiInterface
import io.mateam.playground.data.repo.model.CryptoListQueryResult
import io.mateam.playground.data.repo.model.CryptoQueryResult
import io.mateam.playground.utils.Constants.Companion.DATABASE_PAGE_SIZE
import io.mateam.playground.utils.LoadingStatus
import timber.log.Timber
import javax.inject.Inject

class CryptocurrencyRepository @Inject constructor(
  private val apiInterface: ApiInterface,
  private val cryptocurrenciesDao: CryptocurrenciesDao,
  private val mapper: CryptoModelMapper
) {

  /**
   * Get cryptocurrencies from Repository
   */
  fun queryCryptocurrenciesByName(query: String): CryptoListQueryResult {
    Timber.d("New Query: Get queryCryptocurrenciesByName($query)")
    // Get data source factory from the local cache
    val dataSourceFactory = cryptocurrenciesDao.queryCryptocurrenciesByName(query.toQueryPattern())

    // Construct the boundary callback
    val boundaryCallback = RepoBoundaryCallback(apiInterface, cryptocurrenciesDao, mapper)
    val networkErrors = boundaryCallback.networkErrors
    val loadingStatus = boundaryCallback.loadingStatus

    // Get the paged list
    val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
        .setBoundaryCallback(boundaryCallback)
        .build()

    // Get the network errors exposed by the boundary callback
    return CryptoListQueryResult(data, networkErrors, loadingStatus)
  }

  /**
   * Get cryptocurrencies from Repository
   */
  fun getCryptocurrenciesById(id: Int): CryptoQueryResult {
    Timber.d("Get getCryptocurrenciesById for id = $id")

    // Get the paged list
    val data = cryptocurrenciesDao.queryCryptocurrencyById(id)

    // Get the network errors exposed by the boundary callback
    return CryptoQueryResult(data, MutableLiveData<String>(), MutableLiveData<LoadingStatus>())
  }

  fun String.toQueryPattern(): String {
    // appending '%' so we can allow other characters to be before and after the query string
    return "%${this.replace(' ', '%')}%"
  }

}