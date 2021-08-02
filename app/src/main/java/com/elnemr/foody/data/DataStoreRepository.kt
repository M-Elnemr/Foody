package com.elnemr.foody.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.elnemr.foody.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME
)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

//    private object PreferenceKey {
//        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
//        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
//        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
//        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
//    }

    suspend fun saveStringData(key: String, value: String) {
        val wrappedKey = stringPreferencesKey(key)
        context.settingsDataStore.edit {
            it[wrappedKey] = value
        }
    }

    suspend fun saveIntData(key: String, value: Int) {
        val wrappedKey = intPreferencesKey(key)
        context.settingsDataStore.edit {
            it[wrappedKey] = value
        }
    }

    suspend fun saveDoubleData(key: String, value: Double) {
        val wrappedKey = doublePreferencesKey(key)
        context.settingsDataStore.edit {
            it[wrappedKey] = value
        }
    }

    suspend fun saveBooleanData(key: String, value: Boolean) {
        val wrappedKey = booleanPreferencesKey(key)
        context.settingsDataStore.edit {
            it[wrappedKey] = value
        }
    }

    suspend fun getStringData(key: String, defaultValue: String = ""): String {
        val wrappedKey = stringPreferencesKey(key)
        val dataFlow: Flow<String> = context.settingsDataStore.data.map {
            it[wrappedKey] ?: defaultValue
        }
        return dataFlow.first()
    }

    suspend fun getIntData(key: String, defaultValue: Int = 0): Int {
        val wrappedKey = intPreferencesKey(key)
        val dataFlow: Flow<Int> = context.settingsDataStore.data.map {
            it[wrappedKey] ?: defaultValue
        }
        return dataFlow.first()
    }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeID: Int,
    val selectedDietType: String,
    val selectedDietTypeID: Int
)