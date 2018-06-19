package io.mateam.playground.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.annotation.DrawableRes
import io.mateam.playground.R
import javax.inject.Inject

class Utils @Inject constructor(private val context: Context) {

  fun isConnectedToInternet(): Boolean {
    val connectivity = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager?
    if (connectivity != null) {
      val info = connectivity.allNetworkInfo
      if (info != null)
        for (i in info.indices)
          if (info[i].state == NetworkInfo.State.CONNECTED) {
            return true
          }
    }
    return false
  }

  @DrawableRes fun getRecourseIdByCoinName(name: String?): Int {
    val resIconName = name?.trim()?.toLowerCase()
    var iconId: Int = context.resources.getIdentifier(resIconName, "drawable", context.packageName)
    if (iconId == 0) {
      iconId = R.drawable.ic_no_icon
    }
    return iconId
  }
}