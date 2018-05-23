package io.mateam.playground.ui.main.list

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.mateam.playground.R
import io.mateam.playground.data.repo.model.Cryptocurrency
import io.mateam.playground.ui.main.list.CryptocurrenciesAdapter.CryptocurrencieViewHolder
import io.mateam.playground.utils.Utils
import kotlinx.android.synthetic.main.list_item_cryptocurrency.view.ivCryptoIcon
import kotlinx.android.synthetic.main.list_item_cryptocurrency.view.tvCryptoId
import kotlinx.android.synthetic.main.list_item_cryptocurrency.view.tvUsdPrice
import org.jetbrains.anko.imageResource

class CryptocurrenciesAdapter(
  var utils: Utils
) : PagedListAdapter<Cryptocurrency, CryptocurrencieViewHolder>(REPO_COMPARATOR) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): CryptocurrencieViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(
        R.layout.list_item_cryptocurrency,
        parent, false
    )
    return CryptocurrencieViewHolder(itemView)
  }

  override fun onBindViewHolder(
    holder: CryptocurrencieViewHolder,
    position: Int
  ) {
    val cryptocurrencyItem = getItem(position)
    if (cryptocurrencyItem != null) {
      val cryptoIconId = utils.getRecourseIdByCoinName(cryptocurrencyItem.symbol)
      holder.cryptocurrencyListItem(cryptocurrencyItem, cryptoIconId)
    }
  }

  class CryptocurrencieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvCryptoName = itemView.tvCryptoId
    var tvCryptoPrice = itemView.tvUsdPrice
    var ivCryptoIcon = itemView.ivCryptoIcon

    fun cryptocurrencyListItem(
      cryptocurrencyItem: Cryptocurrency,
      cryptoIconId: Int
    ) {
      tvCryptoName.text = cryptocurrencyItem.name
      tvCryptoPrice.text = cryptocurrencyItem.priceUsd.toString()
      Picasso
          .get()
          .load(cryptoIconId)
          .placeholder(R.drawable.ic_no_icon)
          .into(ivCryptoIcon)

      ivCryptoIcon.imageResource = cryptoIconId
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
