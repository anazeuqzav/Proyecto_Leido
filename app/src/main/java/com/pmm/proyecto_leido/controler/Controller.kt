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

    // Método para hacer logout o simplemente mostrar un Toast con los libros
    fun loggOut() {
        Toast.makeText(context, "He mostrado los datos en pantalla", Toast.LENGTH_LONG).show()

        // Mostrar los libros en consola
        listBooks.forEach {
            println(it)
        }
    }

    // Método para eliminar un libro dado su posición
    fun deleteBook(position: Int) {
        val removedBook = listBooks.removeAt(position)
        Toast.makeText(context, "Eliminado: ${removedBook.title}", Toast.LENGTH_SHORT).show()
        setAdapter() // Refresca el adaptador
    }

    // Método para manejar la llamada desde el diálogo de eliminación
    fun onDeleteBookDialog(position: Int) {
        deleteBook(position)  // Llamamos a la función de eliminar el libro
    }

    // Método para editar un libro
    fun updateBook(position: Int) {
        val bookToUpdate = listBooks[position]
        val editDialog = DialogEditBook(bookToUpdate) { updatedBook ->
            okOnEditBook(updatedBook, position)
        }
        val myActivity = context as MainActivity
        editDialog.show(myActivity.supportFragmentManager, "EditBookDialog")
    }

    private fun okOnEditBook(updatedBook: Book, position: Int) {
        // Actualiza el libro en la posición correspondiente
        listBooks[position] = updatedBook
        val adapter = (context as MainActivity).binding.myRecyclerView.adapter as BookAdapter
        adapter.notifyItemChanged(position) // Notificar el cambio en el RecyclerView
    }


    // Método para añadir un libro
    fun addBook(newBook: Book) {
        listBooks.add(newBook) // Añadir el libro a la lista
        Toast.makeText(context, "Libro añadido: ${newBook.title}", Toast.LENGTH_SHORT).show()
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