package com.example.start_wars.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.start_wars.data.dao.SpecieDAO
import com.example.start_wars.data.model.Specie
import com.example.start_wars.ui.Converters
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import kotlin.jvm.java

@Database(
    version = 5,
    entities = [Specie::class],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StarWarsDB : RoomDatabase() {

    abstract fun getSpecieDao(): SpecieDAO

    companion object {
        /**
         * La variable se guarda en memoria. Cualquier cambio realizado en la variable por un hilo
         * se refleja de inmediado y es visible al resto de hilos. No hay copias antiguas o nulas.
         */
        @Volatile
        private var INSTANCE: StarWarsDB? = null

        fun getDatabase(context: Context): StarWarsDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StarWarsDB::class.java,
                    "starwars_database.db"
                )
                    // 2. CAMBIO IMPORTANTE: Permitir migración destructiva
                    // Si la versión del dispositivo es menor que la versión del código (2),
                    // y no hay una migración manual definida, Room borrará la base de datos
                    // y la creará de nuevo.
                    .fallbackToDestructiveMigration()
                    // Callback para pre-poblar la base de datos
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Se utiliza un executor para realizar la inserción en un hilo de fondo
                            //Las tareas se ejecutan de forma secuencial en un hilo/s
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let { database ->
                                    prepopulateDatabase(database)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun prepopulateDatabase(database: StarWarsDB) {
            val specieDAO = database.getSpecieDao()

            runBlocking {
                // 1. Humano (Ya lo tenías, completado)
                specieDAO.insert(
                    Specie(
                        name = "Human",
                        classification = "Mammal",
                        designation = "Sentient",
                        average_height = "180",
                        average_lifespan = "120",
                        eye_colors = "brown, blue, green, hazel, grey, amber",
                        hair_colors = "blonde, brown, black, red",
                        skin_colors = "caucasian, black, asian, hispanic",
                        language = "Galactic Basic",
                        homeworld = "Coruscant",
                        people = listOf("Luke Skywalker", "Han Solo", "Leia Organa"),
                        films = listOf("A New Hope", "Empire Strikes Back"),
                        is_artificial = false,
                        created = "2024-12-10"
                    )
                )

                // 2. Droide (Ya lo tenías, completado)
                specieDAO.insert(
                    Specie(
                        name = "Droid",
                        classification = "Artificial",
                        designation = "Sentient",
                        average_height = "variable",
                        average_lifespan = "indefinite",
                        eye_colors = "variable",
                        hair_colors = "none",
                        skin_colors = "gold, silver, white, black, blue, red",
                        language = "Binary",
                        homeworld = "Unknown", // Muchos son fabricados en distintos lugares
                        people = listOf("C-3PO", "R2-D2", "BB-8"),
                        films = listOf("The Phantom Menace", "Attack of the Clones"),
                        is_artificial = true,
                        created = "2024-12-09"
                    )
                )

                // 3. Wookiee
                specieDAO.insert(
                    Specie(
                        name = "Wookiee",
                        classification = "Mammal",
                        designation = "Sentient",
                        average_height = "210",
                        average_lifespan = "400",
                        eye_colors = "blue, green, golden, brown, hazel",
                        hair_colors = "black, brown",
                        skin_colors = "gray",
                        language = "Shyriiwook",
                        homeworld = "Kashyyyk",
                        people = listOf("Chewbacca", "Tarfful"),
                        films = listOf("Revenge of the Sith", "A New Hope"),
                        is_artificial = false,
                        created = "2024-12-11"
                    )
                )

                // 4. Hutt
                specieDAO.insert(
                    Specie(
                        name = "Hutt",
                        classification = "Gastropod",
                        designation = "Sentient",
                        average_height = "175",
                        average_lifespan = "1000",
                        eye_colors = "orange, red",
                        hair_colors = "none",
                        skin_colors = "green, brown, tan",
                        language = "Huttese",
                        homeworld = "Nal Hutta",
                        people = listOf("Jabba Desilijic Tiure"),
                        films = listOf("Return of the Jedi", "The Phantom Menace"),
                        is_artificial = false,
                        created = "2024-12-12"
                    )
                )

                // 5. Yoda's species
                specieDAO.insert(
                    Specie(
                        name = "Yoda's species",
                        classification = "Mammal",
                        designation = "Sentient",
                        average_height = "66",
                        average_lifespan = "900",
                        eye_colors = "brown, green, yellow",
                        hair_colors = "brown, white",
                        skin_colors = "green, yellow",
                        language = "Galactic Basic",
                        homeworld = "Unknown",
                        people = listOf("Yoda", "Grogu"),
                        films = listOf("The Empire Strikes Back", "Return of the Jedi"),
                        is_artificial = false,
                        created = "2024-12-13"
                    )
                )

                // 6. Trandoshan (Buen contraste para 'classification' Reptile)
                specieDAO.insert(
                    Specie(
                        name = "Trandoshan",
                        classification = "Reptile",
                        designation = "Sentient",
                        average_height = "200",
                        average_lifespan = "unknown",
                        eye_colors = "yellow, orange",
                        hair_colors = "none",
                        skin_colors = "brown, green",
                        language = "Dosh",
                        homeworld = "Trandosha",
                        people = listOf("Bossk"),
                        films = listOf("The Empire Strikes Back"),
                        is_artificial = false,
                        created = "2024-12-14"
                    )
                )

                // 7. Mon Calamari
                specieDAO.insert(
                    Specie(
                        name = "Mon Calamari",
                        classification = "Amphibian",
                        designation = "Sentient",
                        average_height = "160",
                        average_lifespan = "variable",
                        eye_colors = "yellow",
                        hair_colors = "none",
                        skin_colors = "red, blue, brown, magenta",
                        language = "Mon Calamarian",
                        homeworld = "Mon Cala",
                        people = listOf("Gial Ackbar"),
                        films = listOf("Return of the Jedi"),
                        is_artificial = false,
                        created = "2024-12-15"
                    )
                )

                // 8. Ewok
                specieDAO.insert(
                    Specie(
                        name = "Ewok",
                        classification = "Mammal",
                        designation = "Sentient",
                        average_height = "100",
                        average_lifespan = "variable",
                        eye_colors = "orange, black",
                        hair_colors = "brown, black, grey",
                        skin_colors = "brown",
                        language = "Ewokese",
                        homeworld = "Endor",
                        people = listOf("Wicket Systri Warrick"),
                        films = listOf("Return of the Jedi"),
                        is_artificial = false,
                        created = "2024-12-16"
                    )
                )

                // 9. Sullustan
                specieDAO.insert(
                    Specie(
                        name = "Sullustan",
                        classification = "Mammal",
                        designation = "Sentient",
                        average_height = "160",
                        average_lifespan = "variable",
                        eye_colors = "black",
                        hair_colors = "none",
                        skin_colors = "pale",
                        language = "Sullustese",
                        homeworld = "Sullust",
                        people = listOf("Nien Nunb"),
                        films = listOf("Return of the Jedi"),
                        is_artificial = false,
                        created = "2024-12-17"
                    )
                )

                // 10. Geonosian (Insectoid)
                specieDAO.insert(
                    Specie(
                        name = "Geonosian",
                        classification = "Insectoid",
                        designation = "Sentient",
                        average_height = "178",
                        average_lifespan = "unknown",
                        eye_colors = "green, hazel",
                        hair_colors = "none",
                        skin_colors = "green, brown",
                        language = "Geonosian",
                        homeworld = "Geonosis",
                        people = listOf("Poggle the Lesser"),
                        films = listOf("Attack of the Clones"),
                        is_artificial = false,
                        created = "2024-12-18"
                    )
                )
            }
        }
    }

}