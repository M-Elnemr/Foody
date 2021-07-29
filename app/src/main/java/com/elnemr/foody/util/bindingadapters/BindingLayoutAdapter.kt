package com.elnemr.foody.util.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.elnemr.foody.R
import com.elnemr.foody.data.database.RecipesEntity
import com.elnemr.foody.models.FoodRecipe
import com.elnemr.foody.util.NetworkResult

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
                error(R.drawable.ic_food_joke)
                placeholder(R.drawable.ic_food_joke)
            }
        }

        @BindingAdapter("readApiResponseImage", "readDatabaseImage", requireAll = true)
        @JvmStatic
        fun errorImageVisibilityRecipes(
            imageView: ImageView,
            response: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (response is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponseText", "readDatabaseText", requireAll = true)
        @JvmStatic
        fun errorTextVisibilityRecipes(
            textView: TextView,
            response: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (response is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = response.message.toString()
            } else {
                textView.visibility = View.INVISIBLE
            }
        }

    }
}