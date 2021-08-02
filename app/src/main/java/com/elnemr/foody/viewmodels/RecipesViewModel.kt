package com.elnemr.foody.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.elnemr.foody.data.DataStoreRepository
import com.elnemr.foody.util.Constants.Companion.API_KEY
import com.elnemr.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.elnemr.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.elnemr.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.elnemr.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.elnemr.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.elnemr.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.elnemr.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.elnemr.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.elnemr.foody.util.Constants.Companion.QUERY_API_KEY
import com.elnemr.foody.util.Constants.Companion.QUERY_DIET
import com.elnemr.foody.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.elnemr.foody.util.Constants.Companion.QUERY_NUMBER
import com.elnemr.foody.util.Constants.Companion.QUERY_SEARCH
import com.elnemr.foody.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataSourceRepository: DataStoreRepository
) : AndroidViewModel(application) {

    var networkStatus : Boolean = false
    set(value) {
        return showNetworkStatus(value)
    }

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    fun saveData(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch {
            dataSourceRepository.saveStringData(PREFERENCES_MEAL_TYPE, mealType)
            dataSourceRepository.saveStringData(PREFERENCES_DIET_TYPE, dietType)
            dataSourceRepository.saveIntData(PREFERENCES_MEAL_TYPE_ID, mealTypeId)
            dataSourceRepository.saveIntData(PREFERENCES_DIET_TYPE_ID, dietTypeId)
        }

    fun readStringData(key: String): LiveData<String> {
        val liveData = MutableLiveData<String>()
        viewModelScope.launch {
            liveData.postValue(dataSourceRepository.getStringData(key))
        }
        return liveData
    }

    fun readIntData(key: String): LiveData<Int> {
        val liveData = MutableLiveData<Int>()
        viewModelScope.launch {
            liveData.postValue(dataSourceRepository.getIntData(key))
        }
        return liveData
    }

    private fun readData() {
        viewModelScope.launch {
            mealType = dataSourceRepository.getStringData(PREFERENCES_MEAL_TYPE, DEFAULT_MEAL_TYPE)
            dietType = dataSourceRepository.getStringData(PREFERENCES_DIET_TYPE, DEFAULT_DIET_TYPE)
        }
    }

    fun applyQueries(): HashMap<String, String> {
        readData()
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun applySearchQuery(searchValue: String): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchValue
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    private fun showNetworkStatus(network: Boolean){
        if (!network){
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }
}