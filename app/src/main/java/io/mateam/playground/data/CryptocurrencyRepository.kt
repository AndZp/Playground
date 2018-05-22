package io.mateam.playground.data

import io.mateam.playground.data.local.CryptocurrenciesDao
import io.mateam.playground.data.local.model.Cryptocurrency
import io.mateam.playground.data.remote.ApiInterface
import io.mateam.playground.utils.Utils
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class CryptocurrencyRepository @Inject constructor(
  private val apiInterface: ApiInterface,
  private val cryptocurrenciesDao: CryptocurrenciesDao,
  private val transformer: CryptoModelTransformer,
  private val utils: Utils
) {

  fun getCryptocurrencies(
    limit: Int,
    offset: Int
  ): Observable<List<Cryptocurrency>> {
    val hasConnection = utils.isConnectedToInternet()
    var observableFromApi: Observable<List<Cryptocurrency>>? = null
    Timber.d("getCryptocurrencies call, has network  = $hasConnection")

    if (hasConnection) {
      observableFromApi = getCryptocurrenciesFromApi(limit, offset)
    }
    val observableFromDb = getCryptocurrenciesFromDb(limit, offset)

    return if (hasConnection) {
      Observable.concatArrayEager(observableFromApi, observableFromDb)
    } else observableFromDb
  }

  fun getCryptocurrenciesFromApi(
    limit: Int,
    offset: Int
  ): Observable<List<Cryptocurrency>> {
    return apiInterface.getCryptocurrencies(offset, limit)
        .map { transformer.toDbFormatFromApi(it) }
        .doOnNext {
          Timber.d("REPOSITORY API * ${it.size})")
          for (item in it) {
            cryptocurrenciesDao.insertCryptocurrency(item)
          }
        }
  }

  fun getCryptocurrenciesFromDb(
    limit: Int,
    offset: Int
  ): Observable<List<Cryptocurrency>> {
    return cryptocurrenciesDao.queryCryptocurrencies(limit, offset)
        .toObservable()
        .doOnNext {
          Timber.d("REPOSITORY DB * ${it.size})")
        }
  }
}