package io.mateam.playground.ui.list

import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.mateam.playground.BR
import io.mateam.playground.R
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.ui.list.CryptocurrenciesAdapter.CryptocurrencieViewHolder
import io.mateam.playground.utils.Utils

class CryptocurrenciesAdapter(
  var utils: Utils
) : PagedListAdapter<Cryptocurrency, CryptocurrencieViewHolder>(REPO_COMPARATOR) {

  var onItemClick: (Cryptocurrency) -> Unit = {}

  override fun submitList(pagedList: PagedList<Cryptocurrency>?) {
    super.submitList(pagedList)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): CryptocurrencieViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)

    val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_cryptocurrency, parent, false)

    return CryptocurrencieViewHolder(binding)
  }

  override fun onBindViewHolder(
    holder: CryptocurrencieViewHolder,
    position: Int
  ) {
    val cryptocurrency = getItem(position)
    if (cryptocurrency != null) {
      holder.bind(cryptocurrency)
      holder.itemView.setOnClickListener { onItemClick(cryptocurrency) }
    }
  }

  class CryptocurrencieViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cryptocurrency: Cryptocurrency) {
      binding.setVariable(BR.crypto, cryptocurrency)
      binding.executePendingBindings()
    }
  }

  companion object {
    private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Cryptocurrency>() {
      override fun areItemsTheSame(
        oldItem: Cryptocurrency,
        newItem: Cryptocurrency
      ): Boolean =
        oldItem.id == newItem.id

      override fun areContentsTheSame(
        oldItem: Cryptocurrency,
        newItem: Cryptocurrency
      ): Boolean =
        oldItem == newItem
    }
  }
}
