package com.elnemr.foody.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class FoodRecipe(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)

@Parcelize
data class ExtendedIngredients(
    val amount: Double,
    val consistency: String,
    val image: String,
    val name: String,
    val original: String,
    val unit: String
): Parcelable

@Parcelize
data class Result(
    val aggregateLikes: Int,
    val cheap: Boolean,
    val dairyFree: Boolean,
    val extendedIngredients: List<ExtendedIngredients>,
    val glutenFree: Boolean,
    val id: Int,
    val image: String,
    val readyInMinutes: Int,
    val sourceName: String?,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
//
//    val calories: Int,
//    val carbs: String,
//    val fat: String,
//    val imageType: String,
//    val protein: String,

) : Parcelable