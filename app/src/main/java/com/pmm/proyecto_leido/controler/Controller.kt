package com.pmm.proyecto_leido.controler

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pmm.proyecto_leido.MainActivity
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.adapter.BookAdapter
import com.pmm.proyecto_leido.dao.DaoBooks
import com.pmm.proyecto_leido.dialogues.DialogEditBook
import com.pmm.proyecto_leido.models.Book

class Controller(val context: Context) {

    // Lista mutable de libros
    lateinit var listBooks: MutableList<Book>

    // Inicializador para cargar los datos de los libros
    init {
        initData()
    }

    // Método para cargar los datos desde el DAO
    fun initData() {
        // Cargar los libros desde el DAO (Repositorio)
        listBooks = DaoBooks.myDao.getDataBook().toMutableList()
    }

    // Configurar el adaptador para el RecyclerView
    fun setAdapter(recyclerView: RecyclerView) {
        val adapter = BookAdapter(
            listBooks,
            { pos -> deleteBook(pos, recyclerView) },
            { pos -> updateBook(pos, recyclerView) }
        )
        recyclerView.adapter = adapter
    }

    // Método para hacer logout o simplemente mostrar un Toast con los libros
    fun loggOut() {
        Toast.makeText(context, "He mostrado los datos en pantalla", Toast.LENGTH_LONG).show()

        // Mostrar los libros en consola
        listBooks.forEach {
            println(it)
        }
    }

    // Método para eliminar un libro dado su posición
    fun deleteBook(position: Int, recyclerView: RecyclerView) {
        listBooks.removeAt(position)
        recyclerView.adapter?.notifyItemRemoved(position)
        Toast.makeText(context, "Libro eliminado", Toast.LENGTH_SHORT).show()
    }


    // Método para editar un libro
    fun updateBook(position: Int, recyclerView: RecyclerView) {
        val bookToUpdate = listBooks[position]
        val editDialog = DialogEditBook(bookToUpdate) { updatedBook ->
            listBooks[position] = updatedBook
            recyclerView.adapter?.notifyItemChanged(position)
        }
        val activity = context as MainActivity
        editDialog.show(activity.supportFragmentManager, "EditBookDialog")
    }


    // Método para añadir un libro
    fun addBook(newBook: Book, recyclerView: RecyclerView) {
        listBooks.add(newBook)
        recyclerView.adapter?.notifyItemInserted(listBooks.lastIndex)
        Toast.makeText(context, "Libro añadido: ${newBook.title}", Toast.LENGTH_SHORT).show()
    }
}