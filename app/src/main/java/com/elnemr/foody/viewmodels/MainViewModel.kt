package com.elnemr.foody.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.elnemr.foody.data.Repository
import com.elnemr.foody.data.database.RecipesEntity
import com.elnemr.foody.models.FoodRecipe
import com.elnemr.foody.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM DATABASE*/
    fun readRecipes(): LiveData<List<RecipesEntity>> =
        repository.localDataSource.fetchData().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.localDataSource.insertData(recipesEntity)
        }

    /** RETROFIT*/
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchRecipesResponse : MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getSearchRecipes(queries: HashMap<String, String>) = viewModelScope.launch {
        gatSearchRecipesSafeCall(queries)
    }

    private suspend fun gatSearchRecipesSafeCall(queries: HashMap<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = repository.remoteDataSource.searchRecipes(queries)
                searchRecipesResponse.value = handleRecipesFoodResponse(response)
            }catch (e: Exception){
                searchRecipesResponse.value = NetworkResult.Error("Bad Internet2")
            }
        }else{
            searchRecipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    fun getRecipes(queries: HashMap<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: HashMap<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = repository.remoteDataSource.getRecipes(queries)
                recipesResponse.value = handleRecipesFoodResponse(response)

                offlineCacheRecipes(recipesResponse.value!!.data)

            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Bad Internet1")
            }
        }else{
            searchRecipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe?) {
        if (foodRecipe != null) {
            val recipesEntity = RecipesEntity(foodRecipe)
            insertRecipes(recipesEntity)
        }
    }

    private fun handleRecipesFoodResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("TimeOut")
            response.code() == 402 -> NetworkResult.Error("API Key Limited.")
            response.body()!!.results.isNullOrEmpty() -> NetworkResult.Error("No Recipes Found.")
            response.isSuccessful -> NetworkResult.Success(response.body()!!)
            else -> NetworkResult.Error(response.message())
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}