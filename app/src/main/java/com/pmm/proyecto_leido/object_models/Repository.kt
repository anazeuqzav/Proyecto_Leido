package com.pmm.proyecto_leido.object_models

import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.models.Book

class Repository {

    // Lista de libros
    private val libros = listOf(
        Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, "Fantasy", R.drawable.cover_lotr),
        Book("1984", "George Orwell", 1949, "Dystopia", R.drawable.cover_1984),
        Book(
            "To Kill a Mockingbird",
            "Harper Lee",
            1960,
            "Fiction",
            R.drawable.cover_mockingbird
        ),
        Book("Pride and Prejudice", "Jane Austen", 1813, "Romance", R.drawable.cover_pride),
        Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "Fiction", R.drawable.cover_gatsby),
        Book("Moby-Dick", "Herman Melville", 1851, "Adventure", R.drawable.cover_mobydick),
        Book("Jane Eyre", "Charlotte Brontë", 1847, "Drama", R.drawable.cover_janeeyre),
        Book("Brave New World", "Aldous Huxley", 1932, "Science Fiction", R.drawable.cover_brave),
        Book(
            "The Catcher in the Rye",
            "J.D. Salinger",
            1951,
            "Fiction",
            R.drawable.cover_catcher
        ),
        Book("Wuthering Heights", "Emily Brontë", 1847, "Drama", R.drawable.cover_wuthering)
    )

    // Método para obtener la lista de libros
    fun getBooks(): List<Book> {
        return libros
    }
}