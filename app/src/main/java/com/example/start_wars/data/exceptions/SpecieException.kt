package com.example.start_wars.data.exceptions

sealed class SpecieException(mensaje: String) : Exception(mensaje) {
    data object NotFound: SpecieException(mensaje = "La especie no existe.")
    data class Exists(var email: String): SpecieException(mensaje = "La especie ya existe.")
    data object None: SpecieException(mensaje = "No hay errores.")
}