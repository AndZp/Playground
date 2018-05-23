package io.mateam.playground.ui.main.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import io.mateam.playground.R.layout
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.ui.main.CryptocurrenciesViewModel
import io.mateam.playground.utils.Utils
import kotlinx.android.synthetic.main.fragment_crypto_list.rvCryptos
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber
import javax.inject.Inject

class CryptoListFragment : Fragment() {
  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  @Inject lateinit var utils: Utils

  private lateinit var viewModel: CryptocurrenciesViewModel

  private lateinit var cryptocurrenciesAdapter: CryptocurrenciesAdapter

  companion object {
    fun newInstance() = CryptoListFragment()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    AndroidSupportInjection.inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    cryptocurrenciesAdapter = CryptocurrenciesAdapter(utils)
    return inflater.inflate(layout.fragment_crypto_list, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(CryptocurrenciesViewModel::class.java)
    subscribeToViewModel()
    initializeRecycler()
    viewModel.loadCryptocurrencies()
  }

  private fun initializeRecycler() {
    val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    rvCryptos.addItemDecoration(decoration)
    rvCryptos.adapter = cryptocurrenciesAdapter
    rvCryptos.layoutManager = LinearLayoutManager(context)
  }

  private fun subscribeToViewModel() {
    viewModel.repos.observe(this, Observer<PagedList<Cryptocurrency>> {
      Timber.d("viewModel.repos.observe - list: ${it?.size}")
      showEmptyList(it?.size == 0)
      cryptocurrenciesAdapter.submitList(it)
    })

    viewModel.networkErrors.observe(this, Observer<String> {
      toast("\uD83D\uDE28 Wooops $it")
    })
  }

  private fun showEmptyList(show: Boolean) {

  }

}
