package com.pmm.proyecto_leido

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmm.proyecto_leido.controler.Controller
import com.pmm.proyecto_leido.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Atributos: Controlador y ViewBinding
    lateinit var controller: Controller
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el controlador y configurarlo
        init() // Llamamos al método init() que configura RecyclerView y el controlador
    }

    // Método para inicializar RecyclerView y el controlador
    fun init() {
        initRecyclerView()
        controller = Controller(this) // Creamos el controlador pasándole el contexto de la actividad
        controller.setAdapter() // Asignamos el adaptador al RecyclerView
    }

    // Configuración del RecyclerView
    private fun initRecyclerView() {
        binding.myRecyclerView.layoutManager = LinearLayoutManager(this) // Establecemos el LayoutManager
    }
}