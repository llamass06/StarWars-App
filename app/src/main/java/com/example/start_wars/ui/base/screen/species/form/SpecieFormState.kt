package com.example.start_wars.ui.base.screen.species.form

data class SpecieFormState(
    val name: String = "",
    val isNameError: Boolean = false,
    val classification: String = "",
    val designation: String = "",
    val average_height: String = "",
    val average_lifespan: String = "",
    val eye_colors: String = "",
    val hair_colors: String = "",
    val skin_colors: String = "",
    val language: String = "",
    val homeworld: String = "",
    val people: String = "",
    val films: String = "",
    val discovery_date: String = "",
    val is_artificial: Boolean = false,
    val url: String = "",
    val created: String = "",
    val edited: String = ""
)