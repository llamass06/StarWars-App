package com.example.start_wars.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.start_wars.data.model.Specie
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecieDAO {

    @Insert()
    fun insert(specie: Specie)

    @Query("SELECT * FROM specie")
    fun getAll(): Flow<List<Specie>>

    @Delete
    fun delete(specie: Specie)

    @Query("SELECT EXISTS(SELECT * FROM specie WHERE specie.name = :name)")
    fun exists(name: String): Boolean

    @Update
    fun update(specie: Specie)

    @Query("SELECT * FROM specie WHERE id = :id")
    fun getSpecieById(id: Int): Specie?

    @Query("SELECT * FROM specie ORDER BY name ASC")
    fun getAllSpeciesASC(): Flow<List<Specie>>
    @Query("SELECT * FROM specie ORDER BY name DESC")
    fun getAllSpeciesDESC(): Flow<List<Specie>>
}