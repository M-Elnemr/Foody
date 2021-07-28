package com.elnemr.foody.data.database

import androidx.room.TypeConverter
import com.elnemr.foody.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipe: FoodRecipe): String = gson.toJson(foodRecipe)

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }
}