package com.elnemr.foody.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elnemr.foody.models.FoodRecipe
import com.elnemr.foody.util.Constants.Companion.TABLE

@Entity(tableName = TABLE)
class RecipesEntity(
   var foodRecipe: FoodRecipe
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

