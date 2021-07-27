package com.elnemr.foody.data

import com.elnemr.foody.data.network.ApiInterface
import com.elnemr.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getRecipes(query: HashMap<String, String>): Response<FoodRecipe> =
        apiInterface.fetchRecipes(query)

}