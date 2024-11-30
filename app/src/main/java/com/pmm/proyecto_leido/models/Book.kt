package com.pmm.proyecto_leido.models

class Book (
    var title: String,
    var author: String,
    var year: Int,
    var genrer: String,
    var cover: Int
) {
    override fun toString(): String {
        return "Book(title='$title', author='$author', year='$year', genrer='$genrer')"
    }
}