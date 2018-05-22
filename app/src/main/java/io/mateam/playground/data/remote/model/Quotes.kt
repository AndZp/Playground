package io.mateam.playground.data.remote.model

import com.squareup.moshi.Json

data class Quotes(
  @Json(name = "USD") val uSD: USD?
)