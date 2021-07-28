package com.elnemr.foody.di

import android.content.Context
import androidx.room.Room
import com.elnemr.foody.data.database.RecipesDao
import com.elnemr.foody.data.database.RecipesDatabase
import com.elnemr.foody.util.Constants
import com.elnemr.foody.util.Constants.Companion.DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RecipesDatabase=
        Room.databaseBuilder(context, RecipesDatabase::class.java, DATABASE).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase): RecipesDao =
        database.recipesDao()

}