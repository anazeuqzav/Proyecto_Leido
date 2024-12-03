package com.pmm.proyecto_leido

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Referencias a los elementos del layout
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        // Obtener desde strings.xml los valores de usuario y contraseña
        val storedUsername = getString(R.string.username)
        val storedPassword = getString(R.string.password)

        // Configurar el botón de login
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // método para el manejo del login
            handleLogin(username, password, storedUsername, storedPassword, usernameEditText, passwordEditText)
        }
    }

    /**
     * Maneja el inicio de sesión. Recibe el nombre de usuario, contraseña introducidos en el editText,
     * y los válida comparandolos con los valores almacenados en string.xml. Si la validación falla,
     * muestra un mensaje de error.
     */
    private fun handleLogin(
        username: String,
        password: String,
        storedUsername: String,
        storedPassword: String,
        usernameEditText: EditText,
        passwordEditText: EditText
    ) {
        when {
            username.isEmpty() -> {
                usernameEditText.error = getString(R.string.error_empty_username)
                usernameEditText.requestFocus() // Muestra el cursor en el campo de usuario
            }
            password.isEmpty() -> {
                passwordEditText.error = getString(R.string.error_empty_password)
                passwordEditText.requestFocus() // Muestra el cursor en el campo de contraseña
            }
            username != storedUsername -> {
                showToast(getString(R.string.error_invalid_username))
            }
            password != storedPassword -> {
                showToast(getString(R.string.error_invalid_password))
            }
            else -> {
                navigateToMainActivity(username) // Si la validación es correcta pasa a la pantalla principal
            }
        }
    }

    /**
     * Muestra un mensaje toast
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Navega a la actividad principal de la aplicación.
     */
    private fun navigateToMainActivity(username: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("USERNAME", username)
        }
        startActivity(intent)
        finish()
    }
}

