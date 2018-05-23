package io.mateam.playground.data.repo

import io.mateam.playground.data.remote.model.CryptoApiResponse
import io.mateam.playground.data.repo.model.Cryptocurrency

/**
 * Mapping API response to db models
 */
class CryptoModelMapper {
  fun toDbFormatFromApi(cryptoApiResponse: CryptoApiResponse): List<Cryptocurrency>? {
    return cryptoApiResponse.data?.values?.map { cryptoModel ->
      Cryptocurrency(
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
    }
  }
}