package com.pmm.proyecto_leido.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmm.proyecto_leido.R
import com.pmm.proyecto_leido.controler.Controller
import com.pmm.proyecto_leido.databinding.FragmentBooksReadBinding
import com.pmm.proyecto_leido.dialogues.DialogDeleteBook
import com.pmm.proyecto_leido.models.Book


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

    fun addBook(newBook: Book) {
        controller.addBook(newBook, binding.myRecyclerView)
    }

}