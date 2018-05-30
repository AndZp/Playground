package io.mateam.playground.utils.ui.binding

import android.annotation.SuppressLint
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.mateam.playground.R
import io.mateam.playground.R.string
import org.jetbrains.anko.textColorResource

object DataBindingAdapter {

  @JvmStatic
  @SuppressLint("SetTextI18n")
  @BindingAdapter("percentValue")
  fun TextView.setColorPercentValue(value: Double?) {
    if (value != null) {
      if (value > 0.0) {
        this.textColorResource = R.color.positive_green
      } else {
        this.textColorResource = R.color.negative_red
      }
      this.text = "$value%"
    } else {
      this.text = context.getString(string.not_available)
    }
  }

  @JvmStatic
  @BindingAdapter("android:src")
  fun setImageResource(
    view: ImageView,
    src: Int?
  ) {
    if (src != null) {
      Glide.with(view).load(src).into(view)
    }
  }

  @BindingAdapter("android:src")
  fun setImageUri(
    view: ImageView,
    imageUri: String?
  ) {
    if (imageUri == null) {
      view.setImageURI(null)
    } else {
      Glide.with(view).load(Uri.parse(imageUri)).into(view)
    }
  }

  @BindingAdapter("android:src")
  fun setImageUri(
    view: ImageView,
    imageUri: Uri
  ) {
    Glide.with(view).load(imageUri).into(view)
  }

  @BindingAdapter("android:src")
  fun setImageDrawable(
    view: ImageView,
    drawable: Drawable
  ) {
    Glide.with(view).load(drawable).into(view)
  }
}


