package com.pmm.proyecto_leido.dao

import com.pmm.proyecto_leido.interfaces.InterfaceDao
import com.pmm.proyecto_leido.models.Book
import com.pmm.proyecto_leido.object_models.Repository

class DaoBooks private constructor(): InterfaceDao {

    companion object {
        // Implementación del Singleton: 'lazy' asegura que se cree solo cuando se acceda a él por primera vez
        val myDao: DaoBooks by lazy {
            DaoBooks() // Se crea una única instancia de DaoBooks
        }
    }

    // Método que accede a los datos (en este caso, libros) y los devuelve
    override fun getDataBook(): List<Book> = Repository().getBooks()
}