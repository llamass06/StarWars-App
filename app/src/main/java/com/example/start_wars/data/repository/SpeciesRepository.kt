package com.example.start_wars.data.repository

import com.example.start_wars.data.dao.SpecieDAO
import com.example.start_wars.data.model.Specie
import com.example.start_wars.ui.base.screen.species.list.SortOrder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesRepository @Inject constructor(
    private val speciesDao: SpecieDAO
) {
    //fun getAllSpecies(): Flow<List<Specie>> = speciesDao.getAll()

    fun getAllSpecies(order: SortOrder): Flow<List<Specie>>{
        return when(order){
            SortOrder.NONE -> speciesDao.getAll()
            SortOrder.ASC -> speciesDao.getAllSpeciesASC()
            SortOrder.DESC -> speciesDao.getAllSpeciesDESC()
        }
    }

    fun addSpecies(specie: Specie): Boolean {
        if (!speciesDao.exists(specie.name)){
            speciesDao.insert(specie)
            return true
        }
        return false
    }

    fun updateSpecies(specie: Specie) {
        speciesDao.update(specie)
    }

    fun deleteSpecies(specie: Specie) {
        speciesDao.delete(specie)
    }

    fun getSpecieById(specie: Specie){
        speciesDao.getSpecieById(specie.id)
    }
}