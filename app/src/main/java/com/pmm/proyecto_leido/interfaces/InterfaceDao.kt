package com.pmm.proyecto_leido.interfaces

import com.pmm.proyecto_leido.models.Book

interface InterfaceDao {
    // Método que obtiene los datos
    fun getDataBook(): List<Book>
}


