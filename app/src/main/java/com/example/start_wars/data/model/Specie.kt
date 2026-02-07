package com.example.start_wars.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Specie(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String = "",
    @NonNull
    var classification: String = "",
    var designation: String = "",
    var average_height: String = "",
    var average_lifespan: String = "",
    var eye_colors: String = "",
    var hair_colors: String = "",
    var skin_colors: String = "",
    var language: String = "",
    var homeworld: String = "",
    var people: List<String> = emptyList(),
    var films: List<String> = emptyList(),
    var discovery_date: String = "",
    var is_artificial: Boolean = false,
    val url: String = "",
    val created: String = "",
    var edited: String = ""
): Parcelable