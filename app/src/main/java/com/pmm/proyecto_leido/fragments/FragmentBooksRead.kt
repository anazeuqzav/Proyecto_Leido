package com.pmm.proyecto_leido.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.adapter.BookAdapter
import com.pmm.proyecto_leido.controler.Controller
import com.pmm.proyecto_leido.dialogues.DialogDeleteBook
import com.pmm.proyecto_leido.dialogues.DialogEditBook
import com.pmm.proyecto_leido.dialogues.DialogNewBook
import com.pmm.proyecto_leido.models.Book


class FragmentBooksRead : Fragment() {
    private val controller: Controller by viewModels() // Usamos ViewModel
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflamos la vista del fragmento
        return inflater.inflate(R.layout.fragment_books_read, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view)
        bookAdapter = BookAdapter(
            { position -> deleteBook(position) },
            { position -> updateBook(position) }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = bookAdapter

        // Observar los datos en vivo de Firebase
        controller.entidades.observe(viewLifecycleOwner) { lista ->
            bookAdapter.submitList(lista.toMutableList()) // Actualizar el RecyclerView
        }
    }

    private fun deleteBook(position: Int) {
        val book = bookAdapter.currentList[position]

        DialogDeleteBook(position, book.title) { pos ->
            val bookToDelete = bookAdapter.currentList[pos]
            controller.eliminarEntidad(bookToDelete.id)
        }.show(parentFragmentManager, "DeleteBookDialog")
    }

    private fun updateBook(position: Int) {
        val book = bookAdapter.currentList[position]
        val editDialog = DialogEditBook(book) { updatedBook ->
            controller.actualizarEntidad(updatedBook)
        }
        editDialog.show(parentFragmentManager, "EditBookDialog")
    }

    fun addBook(newBook: Book) {
        controller.agregarEntidad(newBook)
    }

}



/*
class FragmentBooksRead : Fragment() {

    private lateinit var binding: FragmentBooksReadBinding
    private lateinit var controller: Controller

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inicializar el ViewBinding
        binding = FragmentBooksReadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar el controlador y cargar los datos
        controller = Controller(requireContext())
        controller.initData()

        // Configurar el RecyclerView
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        controller.setAdapter(binding.myRecyclerView) // Pasar RecyclerView al controlador
    }



    fun deleteBook(position: Int) {
        controller.deleteBook(position, binding.myRecyclerView)
    }
}
*/