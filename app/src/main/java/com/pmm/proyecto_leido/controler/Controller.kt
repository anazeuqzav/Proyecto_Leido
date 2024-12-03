package com.pmm.proyecto_leido.controler

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pmm.proyecto_leido.MainActivity
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.adapter.BookAdapter
import com.pmm.proyecto_leido.dao.DaoBooks
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

    // Método para hacer logout o simplemente mostrar un Toast con los libros
    fun loggOut() {
        Toast.makeText(context, "He mostrado los datos en pantalla", Toast.LENGTH_LONG).show()

        // Mostrar los libros en consola
        listBooks.forEach {
            println(it)
        }
    }

    // Método para eliminar un libro dado su posición
    private fun deleteBook(position: Int) {
        val removedBook = listBooks.removeAt(position)
        Toast.makeText(context, "Eliminado: ${removedBook.title}", Toast.LENGTH_SHORT).show()
        setAdapter() // Refresca el adaptador
    }


    private fun updateBook(position: Int) {
        val bookToUpdate = listBooks[position]
        Toast.makeText(context, "Actualizar: ${bookToUpdate.title}", Toast.LENGTH_SHORT).show()
        // Aquí podrías abrir un diálogo o actividad para editar los datos del libro
    }


    // Método para asignar el adaptador al RecyclerView en el MainActivity
    fun setAdapter() {
        val myActivity = context as MainActivity
        myActivity.binding.myRecyclerView.adapter = BookAdapter(
            listBooks,
            { pos -> deleteBook(pos) }, // Llama a delBook cuando se pulse el botón de eliminar
            { pos -> updateBook(pos) } // Llama a updateBook cuando se pulse el botón de actualizar
        )
    }
}