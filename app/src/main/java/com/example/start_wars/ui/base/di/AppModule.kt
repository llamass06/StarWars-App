package com.example.start_wars.ui.base.di

import android.content.Context
import android.content.res.Resources
import com.example.start_wars.data.StarWarsDB
import com.example.start_wars.data.dao.SpecieDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule { // Cambié class a object para optimización (opcional pero recomendado)

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    fun providerSpecieDao(swDB: StarWarsDB): SpecieDAO {
        return swDB.getSpecieDao()
    }
    @Provides
    @Singleton
    fun provideStarWarsDB(@ApplicationContext context: Context): StarWarsDB {
        return StarWarsDB.getDatabase(context)
    }
}