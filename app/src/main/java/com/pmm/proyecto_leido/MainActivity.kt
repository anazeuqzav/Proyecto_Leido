package com.pmm.proyecto_leido

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmm.proyecto_leido.controler.Controller
import com.pmm.proyecto_leido.databinding.ActivityMainBinding
import com.pmm.proyecto_leido.dialogues.DialogDeleteBook
import com.pmm.proyecto_leido.dialogues.DialogEditBook
import com.pmm.proyecto_leido.dialogues.DialogNewBook

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

        // Configurar el botón flotante para agregar un libro
        binding.fabAdd.setOnClickListener {
            // Abrir un fragmento o diálogo para capturar los datos del nuevo libro
            openAddBookDialog()
        }
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

    // Método para abrir el diálogo de añadir libro
    private fun openAddBookDialog() {
        val dialog = DialogNewBook { newBook ->
            controller.addBook(newBook) // Agregar el libro al controlador
            controller.setAdapter() // Actualizar el RecyclerView
        }
        dialog.show(supportFragmentManager, "DialogNewBook") // Mostrar el diálogo
    }

    fun showDeleteDialog(position: Int) {
        val bookName = controller.listBooks[position].title
        val deleteDialog = DialogDeleteBook(position, bookName) { pos ->
            controller.deleteBook(pos) // Eliminar el libro después de la confirmación
        }
        deleteDialog.show(supportFragmentManager, "DeleteBookDialog")
    }
    fun showEditDialog(position: Int) {
        val bookToEdit = controller.listBooks[position]
        val editDialog = DialogEditBook(bookToEdit) { updatedBook ->
            controller.updateBook(position) // Actualiza el libro
        }
        editDialog.show(supportFragmentManager, "EditBookDialog")
    }
}