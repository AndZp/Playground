package io.mateam.playground.data.remote

import io.mateam.playground.data.remote.model.CryptoApiResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

  /**
  Endpoint: /ticker/
  Method: GET
  Description: This endpoint displays cryptocurrency ticker data in order of rank. The maximum number of results per call is 100. Pagination is possible by using the start and limit parameters.
  (int) start - return results from rank [start] and above (default is 1)
  (int) limit - return a maximum of [limit] results (default is 100; max is 100)
   **/
  @GET("ticker/")
  fun getCryptocurrencies(@Query("start") start: Int, @Query("limit") limit: Int): Observable<CryptoApiResponse>
}