package io.mateam.playground.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import io.mateam.playground.R
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.databinding.FragmentCryptoDetailsBinding
import io.mateam.playground.utils.LoadingStatus.ERROR
import io.mateam.playground.utils.LoadingStatus.LOADING
import io.mateam.playground.utils.LoadingStatus.SUCCESS
import io.mateam.playground.utils.Utils
import org.jetbrains.anko.support.v4.toast
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
    viewModel = android.arch.lifecycle.ViewModelProviders.of(this, viewModelFactory).get(CryptoDetailsViewModel::class.java)

    val dataBinding: FragmentCryptoDetailsBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_crypto_details,
        container,
        false
    )
    dataBinding.let {
      it.setLifecycleOwner(this@CryptoDetailsFragment)
      it.viewmodel = viewModel
    }
    return dataBinding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    subscribeToViewModel()
    val cryptoId = arguments?.getCryptoId() ?: 1 //throw IllegalStateException("Cryptocurrency ID can't be null")
    viewModel.loadCryptocurrencyById(cryptoId)
  }

  private fun subscribeToViewModel() {
    viewModel.cryptoData.observe(this, Observer<Cryptocurrency> { cryptocurrency ->
      Timber.d("viewModel.cryptoData.observe : Name - ${cryptocurrency?.name}, id -  ${cryptocurrency?.id}")
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

}