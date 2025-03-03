package com.pmm.proyecto_leido

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.pmm.proyecto_leido.databinding.ActivityMainBinding
import com.pmm.proyecto_leido.dialogues.DialogNewBook
import com.pmm.proyecto_leido.fragments.FragmentBooksRead

/**
 * Es la actividad principal de la aplicación y gestiona la navegación entre
 * las diferentes secciones mediante un Navigation Drawer y un NavController.
 * Configura el Navigation Drawer
 * Permite moverse entre los fragmentos
 * Controla el menú lateral y opciones del Toolbar
 * Intercepta clics en los elementos del menú para navegar entre fragmentos.
 * Incluye una opción para cerrar sesión (logout).
 * Maneja el botón flotante fabAdd
 */
class MainActivity : AppCompatActivity() {

    // Controlador de navegación
    private lateinit var navController: NavController
    // Configuración de la barra de navegación superior
    private lateinit var appBarConfiguration: AppBarConfiguration
    // Binding para acceder a las vistas
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa ViewBinding para acceder a las vistas del layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar y Navigation Drawer
        setupNavigationDrawer()

        // Configurar el botón flotante para agregar un libro
        binding.appBarLayoutDrawer.fabAdd.setOnClickListener {
            openAddBookDialog()
        }

        // Manejar clics en el menú lateral de navegación
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    logout()
                    true
                }
                else -> NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
            }
        }
    }

    /**
     * Configura el Navigation Drawer y la Toolbar.
     */
    private fun setupNavigationDrawer() {
        // Obtiene el fragmento que contiene la navegación
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val toolbar = binding.appBarLayoutDrawer.toolbar

        // Cambiar color del icono de navegación
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.md_theme_onPrimary))

        // Configuración de los destinos principales del Navigation Drawer
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.fragmentHome, R.id.fragmentBooksRead, R.id.fragmentBooksToRead, R.id.fragmentFavouritesBooks),
            binding.drawerLayout
        )

        // Configurar Toolbar
        setSupportActionBar(toolbar)

        // Vincular Navigation Controller con Toolbar y Drawer
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
    }

    /**
     * Permite la navegación hacia atrás con la flecha de la Toolbar.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Infla el menú de opciones en la Toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_op, menu)
        return true
    }

    /**
     * Maneja la selección de elementos del menú de la Toolbar.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fragmentHome -> {
                navController.navigate(R.id.fragmentHome)
                true
            }
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

    /**
     * Abre un diálogo para agregar un nuevo libro.
     */
    private fun openAddBookDialog() {
        val dialog = DialogNewBook { newBook ->
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

            // Si el fragmento actual es FragmentBooksRead, añadir el libro
            if (currentFragment is FragmentBooksRead) {
                currentFragment.addBook(newBook)
            }
        }
        dialog.show(supportFragmentManager, "DialogNewBook")
    }

    /**
     * Cierra la sesión del usuario y redirige a la pantalla de inicio de sesión.
     */
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
