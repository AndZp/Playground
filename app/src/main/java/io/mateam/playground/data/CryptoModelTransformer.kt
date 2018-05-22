package io.mateam.playground.data

import io.mateam.playground.data.local.model.Cryptocurrency
import io.mateam.playground.data.remote.model.CryptoApiResponse

class CryptoModelTransformer {
  fun toDbFormatFromApi(cryptoApiResponse: CryptoApiResponse): List<Cryptocurrency> {
    val resultList = arrayListOf<Cryptocurrency>()

    cryptoApiResponse.data?.forEach {
      val cryptoModel = it.value
      val cryptocurrency = Cryptocurrency(
          id = cryptoModel.id,
          name = cryptoModel.name,
          symbol = cryptoModel.symbol,
          rank = cryptoModel.rank,
          priceUsd = cryptoModel.quotes?.uSD?.price,
          volumeUsd24h = cryptoModel.quotes?.uSD?.volume24h,
          marketCapUsd = cryptoModel.quotes?.uSD?.marketCap,
          circulatingSupply = cryptoModel.circulatingSupply,
          totalSupply = cryptoModel.totalSupply,
          maxSupply = cryptoModel.maxSupply,
          percentChange1h = cryptoModel.quotes?.uSD?.percentChange1h,
          percentChange24h = cryptoModel.quotes?.uSD?.percentChange24h,
          percentChange7d = cryptoModel.quotes?.uSD?.percentChange7d,
          lastUpdated = cryptoModel.lastUpdated
      )
      resultList.add(cryptocurrency)
    }
    return resultList
  }
}