package io.mateam.playground.ui.main.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dagger.android.support.AndroidSupportInjection
import io.mateam.playground.R
import io.mateam.playground.R.string
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.utils.LoadingStatus.ERROR
import io.mateam.playground.utils.LoadingStatus.LOADING
import io.mateam.playground.utils.LoadingStatus.SUCCESS
import io.mateam.playground.utils.Utils
import kotlinx.android.synthetic.main.fragment_crypto_details.ivCryptoIcon
import kotlinx.android.synthetic.main.fragment_crypto_details.tv24hVolumeVal
import kotlinx.android.synthetic.main.fragment_crypto_details.tvChange1hVal
import kotlinx.android.synthetic.main.fragment_crypto_details.tvChange24hVal
import kotlinx.android.synthetic.main.fragment_crypto_details.tvChange7dVal
import kotlinx.android.synthetic.main.fragment_crypto_details.tvCryptoName
import kotlinx.android.synthetic.main.fragment_crypto_details.tvCryptoSymbol
import kotlinx.android.synthetic.main.fragment_crypto_details.tvMarketCapVal
import kotlinx.android.synthetic.main.fragment_crypto_details.tvPriceUsdVal
import kotlinx.android.synthetic.main.fragment_crypto_list.pbLoading
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColorResource
import timber.log.Timber
import javax.inject.Inject

class CryptoDetailsFragment : Fragment() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  @Inject lateinit var utils: Utils
  private lateinit var viewModel: CryptoDetailsViewModel

  companion object {
    const val KEY_CRYPTO_ID: String = "key_crypto_id"
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    AndroidSupportInjection.inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_crypto_details, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = android.arch.lifecycle.ViewModelProviders.of(this, viewModelFactory).get(CryptoDetailsViewModel::class.java)
    subscribeToViewModel()
    val cryptoId = arguments?.getCryptoId() ?: throw IllegalStateException("Cryptocurrency ID can't be null")
    viewModel.loadCryptocurrencyById(cryptoId)
  }

  private fun subscribeToViewModel() {
    viewModel.cryptoData.observe(this, Observer<Cryptocurrency> { cryptocurrency ->
      Timber.d("viewModel.cryptoData.observe : Name - ${cryptocurrency?.name}, id -  ${cryptocurrency?.id}")
      cryptocurrency?.let {
        showCryptoDetails(it)
      }
    })

    viewModel.networkErrors.observe(this, Observer<String> {
      toast("\uD83D\uDE28 Wooops $it")
    })

    viewModel.loadingStatus.observe(this, Observer { result ->
      Timber.d("loadingStatus.observe changed - $result")
      when (result) {
        SUCCESS, ERROR -> pbLoading.visibility = View.GONE
        LOADING -> pbLoading.visibility = View.VISIBLE
        null -> pbLoading.visibility = View.GONE
      }
    })
  }

  private fun showCryptoDetails(cryptocurrency: Cryptocurrency) {
    val coinIconResId = utils.getRecourseIdByCoinName(cryptocurrency.symbol)
    ivCryptoIcon.imageResource = coinIconResId


    with(cryptocurrency) {
      tvCryptoName.text = name
      tvCryptoSymbol.text = symbol
      tvPriceUsdVal.text = priceUsd.toString()
      tv24hVolumeVal.text = volumeUsd24h.toString()
      tvMarketCapVal.text = marketCapUsd.toString()
      tvChange1hVal.setPercentValue(percentChange1h)
      tvChange24hVal.setPercentValue(percentChange24h)
      tvChange7dVal.setPercentValue(percentChange7d)
    }
  }

  @SuppressLint("SetTextI18n")
  private fun TextView.setPercentValue(value: Double?) {
    if (value != null) {
      if (value > 0.0) {
        this.textColorResource = R.color.positive_green
      } else {
        this.textColorResource = R.color.negative_red
      }
      this.text = "$value %"
    } else {
      this.text = context.getString(string.not_available)
    }
  }
}

private fun Bundle.getCryptoId(): Int? {
  //Check if fragment was opened from CryptoListFragment
  var cryptoId: Int? = null
  val intentCryptoId: Int? = this.getInt(CryptoDetailsFragment.KEY_CRYPTO_ID, -1)
  if (intentCryptoId != null && intentCryptoId > 0) {
    Timber.d("Fragment opened from CryptoListFragment. Crypto id = $intentCryptoId")
    cryptoId = intentCryptoId
  }
  // Check if fragment was opened via deep link
  val stringCryptoId: String? = this.getString(CryptoDetailsFragment.KEY_CRYPTO_ID)
  val deepLinkCryptoId: Int? = stringCryptoId?.toIntOrNull()
  if (deepLinkCryptoId != null && deepLinkCryptoId > 0) {
    Timber.d("Fragment opened via deep link. Crypto id = $deepLinkCryptoId")
    cryptoId = deepLinkCryptoId
  }

  return cryptoId
}

