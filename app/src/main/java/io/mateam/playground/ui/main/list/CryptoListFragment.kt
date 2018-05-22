package io.mateam.playground.ui.main.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import io.mateam.playground.R
import io.mateam.playground.R.layout
import io.mateam.playground.ui.main.CryptocurrenciesViewModel
import io.mateam.playground.utils.Constants
import io.mateam.playground.utils.Utils
import io.mateam.playground.utils.ui.InfiniteScrollListener
import kotlinx.android.synthetic.main.main_fragment.pbLoading
import kotlinx.android.synthetic.main.main_fragment.rvCryptos
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber
import javax.inject.Inject

class CryptoListFragment : Fragment() {
  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  @Inject lateinit var utils: Utils

  private lateinit var viewModel: CryptocurrenciesViewModel

  private lateinit var cryptocurrenciesAdapter: CryptocurrenciesAdapter
  var currentPage = 0

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
    cryptocurrenciesAdapter = CryptocurrenciesAdapter(ArrayList(), utils)

    return inflater.inflate(layout.main_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(CryptocurrenciesViewModel::class.java)
    subscribeToViewModel()
    initializeRecycler()
    loadData()
  }

  private fun initializeRecycler() {
    val gridLayoutManager = GridLayoutManager(context, 1)
    gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
    rvCryptos.apply {
      setHasFixedSize(true)
      layoutManager = gridLayoutManager
      addOnScrollListener(InfiniteScrollListener({ loadData() }, gridLayoutManager))
    }
  }

  private fun subscribeToViewModel() {
    viewModel.cryptocurrenciesResult.observe(this, Observer {
      Timber.d("cryptocurrenciesResult.observe: list size = ${it?.size}")
      if (it != null) {
        val position = cryptocurrenciesAdapter.itemCount
        cryptocurrenciesAdapter.addCryptocurrencies(it)
        rvCryptos.adapter = cryptocurrenciesAdapter
        rvCryptos.scrollToPosition(position - Constants.LIST_SCROLLING)
      }
    })

    viewModel.cryptocurrenciesError.observe(this, Observer {
      Timber.e("cryptocurrenciesError.observe: $it")
      if (it != null) {
        toast(resources.getString(R.string.cryptocurrency_error_message) + it)
      }
    })

    viewModel.cryptocurrenciesLoader.observe(this, Observer<Boolean> {
      Timber.e("cryptocurrenciesLoader().observe: isLoading = $it")
      if (it == false) pbLoading.visibility = View.GONE
    })
  }

  private fun loadData() {
    pbLoading.visibility = View.VISIBLE
    viewModel.loadCryptocurrencies(Constants.LIMIT, currentPage * Constants.OFFSET)
    currentPage++
  }

  override fun onDestroyView() {
    viewModel.disposeElements()
    super.onDestroyView()
  }

}
