package io.mateam.playground.data.remote.model

import com.squareup.moshi.Json

data class USD(
  @Json(name = "price") val price: Double,
  @Json(name = "volume_24h") val volume24h: Double,
  @Json(name = "market_cap") val marketCap: Double,
  @Json(name = "percent_change_1h") val percentChange1h: Double,
  @Json(name = "percent_change_24h") val percentChange24h: Double,
  @Json(name = "percent_change_7d") val percentChange7d: Double
)