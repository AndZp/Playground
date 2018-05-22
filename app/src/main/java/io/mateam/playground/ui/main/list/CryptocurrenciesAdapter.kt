package io.mateam.playground.ui.main.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.mateam.playground.R
import io.mateam.playground.data.local.model.Cryptocurrency
import io.mateam.playground.ui.main.list.CryptocurrenciesAdapter.CryptocurrencieViewHolder
import io.mateam.playground.utils.Utils
import kotlinx.android.synthetic.main.list_item_cryptocurrency.view.cryptocurrency_id
import kotlinx.android.synthetic.main.list_item_cryptocurrency.view.ivCryptoIcon
import kotlinx.android.synthetic.main.list_item_cryptocurrency.view.tvUsdPrice
import org.jetbrains.anko.imageResource
import java.util.ArrayList

class CryptocurrenciesAdapter(
  cryptocurrencies: List<Cryptocurrency>,
  var utils: Utils
) : RecyclerView.Adapter<CryptocurrencieViewHolder>() {

  private var cryptocurrenciesList = ArrayList<Cryptocurrency>()

  init {
    this.cryptocurrenciesList = cryptocurrencies as ArrayList<Cryptocurrency>
  }

  override fun getItemCount(): Int {
    return cryptocurrenciesList.size
  }

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
    val cryptocurrencyItem = cryptocurrenciesList[position]
    val cryptoIconId = utils.getRecourseIdByCoinName(cryptocurrencyItem.symbol)
    holder.cryptocurrencyListItem(cryptocurrencyItem, cryptoIconId)
  }

  fun addCryptocurrencies(cryptocurrencies: List<Cryptocurrency>) {
    val initPosition = cryptocurrenciesList.size
    cryptocurrenciesList.addAll(cryptocurrencies)
    notifyItemRangeInserted(initPosition, cryptocurrenciesList.size)
  }

  class CryptocurrencieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvCryptoName = itemView.cryptocurrency_id
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
}
