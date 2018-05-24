package io.mateam.playground.data.remote.model

import com.squareup.moshi.Json

/**
 * Response class that holds all the information about a cryptocurrency.
 * https://coinmarketcap.com/api/  - CoinMarketCap Public API Documentation Version 2
 * https://api.coinmarketcap.com/v2/ticker/1/ - response example
 * */
data class CryptoApiResponse(
  @Json(name = "data") val data: Map<String, CryptoModel>?,
  @Json(name = "metadata") val metadata: Metadata
)