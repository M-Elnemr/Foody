package com.elnemr.foody.data

import com.elnemr.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {
    @GET("")
    fun fetchRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>
}