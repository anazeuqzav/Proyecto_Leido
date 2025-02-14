package com.pmm.proyecto_leido

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.pmm.proyecto_leido.LoginActivity.Global
import com.pmm.proyecto_leido.controler.Controller
import com.pmm.proyecto_leido.databinding.ActivityMainBinding
import com.pmm.proyecto_leido.dialogues.DialogDeleteBook
import com.pmm.proyecto_leido.dialogues.DialogNewBook
import com.pmm.proyecto_leido.fragments.FragmentBooksRead
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar y Navigation Drawer
        setupNavigationDrawer()

        // Configurar el botón flotante para agregar un libro
        binding.appBarLayoutDrawer.fabAdd.setOnClickListener {
            openAddBookDialog()
        }

        // **Interceptar clics en el menú lateral**
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    logout()
                    true
                }
                else -> NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
            }
        }

        // Configurar el botón flotante para agregar un libro
        binding.appBarLayoutDrawer.fabAdd.setOnClickListener {
            openAddBookDialog()
        }
    }

    private fun setupNavigationDrawer() {
        // Obtener NavHostFragment y NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val toolbar = binding.appBarLayoutDrawer.toolbar

        // Cambiar color del icono de navegación
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.md_theme_onPrimary))

        // Configurar destinos principales
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_op, menu)
        return true
    }

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

    private fun openAddBookDialog() {
        val dialog = DialogNewBook { newBook ->
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

            if (currentFragment is FragmentBooksRead) {
                currentFragment.addBook(newBook) // Llama al método del fragmento
            }
        }
        dialog.show(supportFragmentManager, "DialogNewBook")
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
