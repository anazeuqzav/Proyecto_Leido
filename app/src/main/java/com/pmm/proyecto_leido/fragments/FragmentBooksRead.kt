package com.pmm.proyecto_leido.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.adapter.BookAdapter
import com.pmm.proyecto_leido.controler.Controller
import com.pmm.proyecto_leido.dialogues.DialogDeleteBook
import com.pmm.proyecto_leido.dialogues.DialogEditBook
import com.pmm.proyecto_leido.models.Book

/**
 * Fragmento que muestra la lista de libros leídos.
 * Utiliza un RecyclerView para visualizar los libros y permite eliminarlos o editarlos.
 */
class FragmentBooksRead : Fragment() {

    // ViewModel que maneja la lógica de los datos
    private val controller: Controller by viewModels()

    // Adaptador para mostrar la lista de libros en el RecyclerView
    private lateinit var bookAdapter: BookAdapter

    /**
     * Infla la vista del fragmento cuando se crea.
     * @return Vista inflada con el layout asociado.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_books_read, container, false)
    }

    /**
     * Se ejecuta después de que la vista ha sido creada.
     * Aquí se configuran el RecyclerView y los observadores de datos.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view)
        bookAdapter = BookAdapter(
            { position -> deleteBook(position) }, // Función de eliminación
            { position -> updateBook(position) }  // Función de edición
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = bookAdapter

        // Observar los cambios en la lista de libros en Firebase
        controller.entidades.observe(viewLifecycleOwner) { lista ->
            bookAdapter.submitList(lista.toMutableList()) // Actualiza la lista en RecyclerView
        }
    }

    /**
     * Elimina un libro de la lista.
     * @param position Posición del libro en la lista.
     */
    private fun deleteBook(position: Int) {
        val book = bookAdapter.currentList[position]

        // Mostrar diálogo de confirmación antes de eliminar
        DialogDeleteBook(position, book.title) { pos ->
            val bookToDelete = bookAdapter.currentList[pos]
            controller.eliminarEntidad(bookToDelete.id)
        }.show(parentFragmentManager, "DeleteBookDialog")

        Toast.makeText(context, "Libro eliminado correctamente", Toast.LENGTH_SHORT).show()
    }

    /**
     * Abre el diálogo de edición para modificar un libro.
     * @param position Posición del libro en la lista.
     */
    private fun updateBook(position: Int) {
        val book = bookAdapter.currentList[position]

        // Mostrar diálogo de edición
        val editDialog = DialogEditBook(book) { updatedBook ->
            controller.actualizarEntidad(updatedBook)
        }
        editDialog.show(parentFragmentManager, "EditBookDialog")

        Toast.makeText(context, "Libro editado correctamente", Toast.LENGTH_SHORT).show()
    }

    /**
     * Agrega un nuevo libro a la lista.
     * @param newBook Libro a agregar.
     */
    fun addBook(newBook: Book) {
        controller.agregarEntidad(newBook)
        Toast.makeText(context, "Libro agregado correctamente", Toast.LENGTH_SHORT).show()
    }
}
