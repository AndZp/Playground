package io.mateam.playground.data.remote.model

import com.squareup.moshi.Json

data class Metadata(
  @Json(name = "timestamp") val timestamp: Long,
  @Json(name = "num_cryptocurrencies") val numCryptocurrencies: Int?,
  @Json(name = "error") val error: String?
)