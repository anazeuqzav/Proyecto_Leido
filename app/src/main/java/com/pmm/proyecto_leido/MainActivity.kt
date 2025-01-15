package com.pmm.proyecto_leido

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.pmm.proyecto_leido.controler.Controller
import com.pmm.proyecto_leido.databinding.ActivityMainBinding
import com.pmm.proyecto_leido.dialogues.DialogDeleteBook
import com.pmm.proyecto_leido.dialogues.DialogEditBook
import com.pmm.proyecto_leido.dialogues.DialogNewBook
import com.pmm.proyecto_leido.fragments.FragmentBooksRead

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var controller: Controller


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el controlador y configurarlo
        // Llamamos al método init() que configura RecyclerView y el controlador
        controller = Controller(context = this)

        // Configurar Toolbar y Navigation Drawer
        setupNavigationDrawer()

        // Configurar elementos adicionales si es necesario
        setupFloatingActionButton()

        // Configurar el botón flotante para agregar un libro
        binding.appBarLayoutDrawer.fabAdd.setOnClickListener {
            // Abrir un fragmento o diálogo para capturar los datos del nuevo libro
            openAddBookDialog()
        }
    }



    private fun setupNavigationDrawer() {
        // Obtener NavHostFragment y NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Configurar destinos principales (top-level destinations)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.fragmentBooksRead, R.id.fragmentBooksToRead, R.id.fragmentFavouritesBooks),
            binding.drawerLayout
        )

        // Vincular Toolbar con NavController
        setSupportActionBar(binding.appBarLayoutDrawer.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Vincular NavigationView con NavController
        binding.navigationView.setupWithNavController(navController)
    }

    private fun setupFloatingActionButton() {
        binding.appBarLayoutDrawer.fabAdd.setOnClickListener {
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            if (currentFragment is FragmentBooksRead) {
                val dialog = DialogNewBook { newBook ->
                    currentFragment.addBook(newBook)
                }
                dialog.show(supportFragmentManager, "DialogNewBook")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_op, menu)
        return true
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fragmentBooksRead -> {
                navController.navigate(R.id.fragmentBooksRead)
                true
            }

            R.id.fragmentBooksToRead -> {
                navController.navigate(R.id.fragmentBooksToRead)
                true
            }

            R.id.fragmentFavouritesBooks -> {
                navController.navigate(R.id.fragmentFavouritesBooks)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDeleteDialog(position: Int) {
        val bookName = controller.listBooks[position].title
        val deleteDialog = DialogDeleteBook(position, bookName) { pos ->
            // Obtén el NavHostFragment y el fragmento activo
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

            if (currentFragment is FragmentBooksRead) {
                // Encuentra el RecyclerView dentro del fragmento
                val recyclerView =
                    currentFragment.view?.findViewById<RecyclerView>(R.id.my_recycler_view)
                if (recyclerView != null) {
                    // Llama al controlador para borrar el libro
                    controller.deleteBook(pos, recyclerView)
                }
            }
        }
            deleteDialog.show(supportFragmentManager, "DialogDeleteBook")
        }

    private fun openAddBookDialog() {
        val dialog = DialogNewBook { newBook ->
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
            if (currentFragment is FragmentBooksRead) {
                // Busca el RecyclerView dentro del fragmento
                val recyclerView =
                    currentFragment.view?.findViewById<RecyclerView>(R.id.my_recycler_view)
                if (recyclerView != null) {
                    // Actualiza el RecyclerView usando el controlador
                    controller.addBook(newBook, recyclerView)
                    controller.setAdapter(recyclerView)
                }
            }
        }
        dialog.show(supportFragmentManager, "DialogNewBook")
    }
}