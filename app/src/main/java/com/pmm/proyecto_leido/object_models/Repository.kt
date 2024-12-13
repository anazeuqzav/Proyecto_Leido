package com.pmm.proyecto_leido.object_models

import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.models.Book

class Repository {

    // Lista de libros
    private val libros = listOf(
        Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, "Fantasy", "https://m.media-amazon.com/images/I/81nV6x2ey4L.jpg"),
        Book("1984", "George Orwell", 1949, "Dystopia", "https://m.media-amazon.com/images/I/715WdnBHqYL._UF1000,1000_QL80_.jpg"),
        Book(
            "To Kill a Mockingbird",
            "Harper Lee",
            1960,
            "Fiction",
            "https://m.media-amazon.com/images/I/811NqsxadrS._AC_UF894,1000_QL80_.jpg"
        ),
        Book("Pride and Prejudice", "Jane Austen", 1813, "Romance", "https://www.gutenberg.org/files/1342/1342-h/images/cover.jpg"),
        Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "Fiction", "https://m.media-amazon.com/images/I/81QuEGw8VPL._AC_UF894,1000_QL80_.jpg"),
        Book("Moby-Dick", "Herman Melville", 1851, "Adventure", "https://m.media-amazon.com/images/I/71d5wo+-MuL._AC_UF1000,1000_QL80_.jpg"),
        Book("Jane Eyre", "Charlotte Brontë", 1847, "Drama", "https://imagessl1.casadellibro.com/a/l/s5/61/9788491048961.webp"),
        Book("Brave New World", "Aldous Huxley", 1932, "Science Fiction", "https://m.media-amazon.com/images/I/91D4YvdC0dL._AC_UF894,1000_QL80_.jpg"),
        Book(
            "The Catcher in the Rye",
            "J.D. Salinger",
            1951,
            "Fiction",
            "https://m.media-amazon.com/images/I/8125BDk3l9L._AC_UF1000,1000_QL80_.jpg"
        ),
        Book("Wuthering Heights", "Emily Brontë", 1847, "Drama", "https://m.media-amazon.com/images/I/81T34Sem-tL.jpg")
    )

    // Método para obtener la lista de libros
    fun getBooks(): List<Book> {
        return libros
    }
}