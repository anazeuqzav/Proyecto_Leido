package com.pmm.proyecto_leido.models


data class Book (
    var id: String = "",
    var title: String = "",
    var author: String = "",
    var year: Int = 0,
    var genrer: String = "",
    var cover: String = ""
) {
    // Firebase necesita un constructor sin argumentos
    constructor() : this("", "", "", 0)

    override fun toString(): String {
        return "Book(id = $id, title='$title', author='$author', year='$year', genrer='$genrer')"
    }
}