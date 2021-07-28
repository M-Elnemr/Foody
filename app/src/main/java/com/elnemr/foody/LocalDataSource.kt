package com.elnemr.foody

import com.elnemr.foody.data.database.RecipesDao
import com.elnemr.foody.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {

    suspend fun insertData(recipesEntity: RecipesEntity) =
        recipesDao.insertRecipes(recipesEntity)


    fun fetchData(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()

}