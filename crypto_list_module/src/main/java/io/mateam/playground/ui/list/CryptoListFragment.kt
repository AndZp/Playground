package io.mateam.playground.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.SearchView.OnQueryTextListener
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.AndroidSupportInjection
import io.mateam.playground.R
import io.mateam.playground.R.id.rvCryptos
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.ui.detail.CryptoDetailsFragment
import io.mateam.playground.utils.LoadingStatus.ERROR
import io.mateam.playground.utils.LoadingStatus.LOADING
import io.mateam.playground.utils.LoadingStatus.SUCCESS
import io.mateam.playground.utils.Utils
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber
import javax.inject.Inject

class CryptoListFragment : Fragment() {
  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  @Inject lateinit var utils: Utils

  private lateinit var viewModel: CryptoListViewModel
  private lateinit var cryptocurrenciesAdapter: CryptocurrenciesAdapter

  override fun onDestroyView() {
    super.onDestroyView()
    Timber.d("onDestroyView")
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    AndroidSupportInjection.inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    cryptocurrenciesAdapter = CryptocurrenciesAdapter(utils)
    return inflater.inflate(R.layout.fragment_crypto_list, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(CryptoListViewModel::class.java)
    subscribeToViewModel()
    initializeRecycler()
    viewModel.loadCryptocurrencies(viewModel.lastQueryValue() ?: "")
  }

  override fun onCreateOptionsMenu(
    menu: Menu?,
    inflater: MenuInflater?
  ) {
    inflater?.inflate(R.menu.menu_list, menu)

    val item = menu?.findItem(R.id.action_search)
    val searchView = item?.actionView as SearchView
    searchView.setOnQueryTextListener(object : OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        Timber.d("SearchView: onQueryTextChange = $query")
        query?.let { viewModel.loadCryptocurrencies(it) }
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        return true
      }
    })

    item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
      override fun onMenuItemActionExpand(p0: MenuItem?): Boolean = true
      override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean = true
    })

    if (!viewModel.lastQueryValue().isNullOrEmpty()) {
      item.expandActionView()
      searchView.setQuery(viewModel.lastQueryValue(), false)
    }
  }

  private fun initializeRecycler() {
    val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    rvCryptos.addItemDecoration(decoration)
    rvCryptos.adapter = cryptocurrenciesAdapter
    rvCryptos.layoutManager = LinearLayoutManager(context)
    cryptocurrenciesAdapter.onItemClick = { cryptocurrency ->
      val bundle = bundleOf(CryptoDetailsFragment.KEY_CRYPTO_ID to cryptocurrency.id)
      NavHostFragment.findNavController(this).navigate(R.id.action_cryptoListFragment_to_cryptoDetailsFragment, bundle)
    }
  }

  private fun subscribeToViewModel() {
    viewModel.crypocurrencies.observe(this, Observer<PagedList<Cryptocurrency>> {
      Timber.d("viewModel.cryptoData.observe - list: ${it?.size}")
      showEmptyState(it?.size == 0)
      cryptocurrenciesAdapter.submitList(it)
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

  private fun showEmptyState(isShowEmptyState: Boolean) {
    if (isShowEmptyState) {
      rvCryptos.visibility = View.GONE
      tvEmptyState.visibility = View.VISIBLE
    } else {
      rvCryptos.visibility = View.VISIBLE
      tvEmptyState.visibility = View.GONE
    }
  }
}
