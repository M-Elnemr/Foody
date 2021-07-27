package com.elnemr.foody.models

data class FoodRecipe(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)

data class Result(
    val calories: Int,
    val carbs: String,
    val fat: String,
    val id: Int,
    val image: String,
    val imageType: String,
    val protein: String,

    val vegan: Boolean,
    val readyInMinutes: Int,
    val aggregateLikes: Int,
    val title: String,
    val summary: String
)