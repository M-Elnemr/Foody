package com.elnemr.foody.util.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.elnemr.foody.R

class BindingLayoutAdapter {
    companion object {
        @BindingAdapter("textInt")
        @JvmStatic
        fun setIntText(textView: TextView, intText: Int) {
            textView.text = intText.toString()
        }

        @BindingAdapter("backgroundBooleanGreen")
        @JvmStatic
        fun changeBackgroundColor(view: View, data: Boolean) {

            if (data) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }

        }

        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
            }

        }
    }
}