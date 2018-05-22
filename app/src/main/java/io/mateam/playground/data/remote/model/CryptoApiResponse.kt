package io.mateam.playground.data.remote.model

import com.squareup.moshi.Json

data class CryptoApiResponse(
  @Json(name = "data") val data: Map<String, CryptoModel>?,
  @Json(name = "metadata") val metadata: Metadata
)