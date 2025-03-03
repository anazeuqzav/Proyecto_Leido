package com.pmm.proyecto_leido.models

/**
 * Objeto singleton que almacena constantes para los nombres de argumentos.
 * Se usa al pasar datos entre actividades o fragmentos en la aplicación.
 */
object ArgumentsBook {
    const val ARGUMENT_TITLE = "title"    // Clave para el título del libro
    const val ARGUMENT_AUTHOR = "author"  // Clave para el autor del libro
    const val ARGUMENT_YEAR = "year"      // Clave para el año de publicación
    const val ARGUMENT_GENRE = "genre"    // Clave para el género del libro
    const val ARGUMENT_COVER = "cover"    // Clave para la URL o ruta de la portada
}