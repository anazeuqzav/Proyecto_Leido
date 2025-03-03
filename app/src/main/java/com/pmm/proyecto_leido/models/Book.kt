package com.pmm.proyecto_leido.models

/**
 * Clase de datos que representa un libro en la aplicación.
 * Se usa en la base de datos Firebase Firestore.
 */
data class Book (
    var id: String = "",       // ID único del libro en Firestore
    var title: String = "",    // Título del libro
    var author: String = "",   // Autor del libro
    var year: Int = 0,         // Año de publicación
    var genrer: String = "",   // Género del libro (posible error de escritura, debería ser "genre")
    var cover: String = ""     // URL o ruta de la portada del libro
) {
    /**
     * Constructor vacío requerido por Firebase para la conversión de documentos.
     * Asigna valores por defecto para evitar errores al recuperar datos.
     */
    constructor() : this("", "", "", 0)

    /**
     * Representación en texto del objeto `Book`, útil para logs y depuración.
     * Excluye la portada para evitar grandes volúmenes de datos en la salida.
     */
    override fun toString(): String {
        return "Book(id = $id, title='$title', author='$author', year='$year', genrer='$genrer')"
    }
}
