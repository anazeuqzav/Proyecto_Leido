package com.pmm.proyecto_leido


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

/**
 * Esta clase maneja la pantalla de inicio de sesión de la aplicación y se encarga de:
 * Autenticación con Firebase (FirebaseAuth)
 * Manejo de errores de autenticación
 * Guardado y verificación de sesiones con SharedPreferences
 * Recuperación de contraseña
 */
class LoginActivity : AppCompatActivity() {

    // Objeto global para definir la clave de SharedPreferences
    object Global {
        var sharedPreferences = "sharedpreferences"
    }

    // Instancia de Firebase Authentication
    private lateinit var auth: FirebaseAuth

    // Elementos de la interfaz de usuario
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: TextView
    private lateinit var recoverPasswordButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Verificar si hay una sesión abierta
        verifyOpenSession()

        // Configurar la interfaz de usuario
        setContentView(R.layout.activity_login)

        // Asignar referencias a los elementos de la interfaz
        emailEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        recoverPasswordButton = findViewById(R.id.recoverPasswordButton)

        // Configurar el botón de inicio de sesión
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            handleLogin(email, password)
        }

        // Configurar el botón de registro
        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Configurar el botón de recuperación de contraseña
        recoverPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            if (email.isNotEmpty()) {
                recoverPassword(email)
            } else {
                showToast("Por favor, ingresa tu correo electrónico.")
            }
        }
    }

    /**
     * Maneja el inicio de sesión con Firebase Authentication.
     */
    private fun handleLogin(email: String, password: String) {
        when {
            email.isEmpty() -> {
                emailEditText.error = "El correo electrónico no puede estar vacío"
                emailEditText.requestFocus()
            }
            password.isEmpty() -> {
                passwordEditText.error = "La contraseña no puede estar vacía"
                passwordEditText.requestFocus()
            }
            else -> {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user?.isEmailVerified == true) {
                                saveSession(email, password)
                                navigateToMainActivity()
                            } else {
                                auth.signOut()
                                showToast("Debes verificar tu correo antes de iniciar sesión.")
                            }
                        } else {
                            handleAuthError(task.exception)
                        }
                    }
            }
        }
    }

    /**
     * Maneja los errores de autenticación en Firebase.
     */
    private fun handleAuthError(exception: Exception?) {
        val message = when (exception) {
            is FirebaseAuthInvalidUserException -> "El usuario no existe o ha sido deshabilitado."
            is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña incorrectos."
            else -> exception?.message ?: "Error desconocido"
        }
        showToast(message)
    }

    /**
     * Envia un correo para recuperar la contraseña.
     */
    private fun recoverPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Se ha enviado un correo para restablecer tu contraseña.")
                } else {
                    showToast("Error al enviar el correo. Verifica que el email sea correcto.")
                }
            }
    }

    /**
     * Muestra un mensaje toast.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Navega a la actividad principal de la aplicación.
     */
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Verifica si hay una sesión activa y redirige al usuario si es necesario.
     */
    fun verifyOpenSession() {
        val openSession: SharedPreferences = getSharedPreferences(Global.sharedPreferences, Context.MODE_PRIVATE)
        val email = openSession.getString("email", null)

        if (email != null) {
            val user = auth.currentUser
            if (user != null) { // Si hay un usuario autenticado, navegar a MainActivity
                navigateToMainActivity()
            }
        }
    }

    /**
     * Guarda la sesión del usuario en SharedPreferences.
     */
    fun saveSession(correo: String, password: String) {
        val saveSession: SharedPreferences.Editor = this.getSharedPreferences(Global.sharedPreferences, Context.MODE_PRIVATE).edit()
        saveSession.putString("email", correo)
        saveSession.putString("password", password)
        saveSession.apply()
    }
}
