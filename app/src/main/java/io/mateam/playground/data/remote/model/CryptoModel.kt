package io.mateam.playground.data.remote.model

import com.squareup.moshi.Json
import java.io.Serializable

data class CryptoModel(
  @Json(name = "id") val id: Int,
  @Json(name = "name") val name: String,
  @Json(name = "symbol") val symbol: String,
  @Json(name = "website_slug") val websiteSlug: String,
  @Json(name = "rank") val rank: Int,
  @Json(name = "circulating_supply") val circulatingSupply: Double,
  @Json(name = "total_supply") val totalSupply: Double,
  @Json(name = "max_supply") val maxSupply: Double?,
  @Json(name = "quotes") val quotes: Quotes?,
  @Json(name = "last_updated") val lastUpdated: Long
) : Serializable



