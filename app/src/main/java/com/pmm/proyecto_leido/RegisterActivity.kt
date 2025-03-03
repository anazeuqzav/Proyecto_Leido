package com.pmm.proyecto_leido


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth

/**
 * Esta actividad gestiona el registro de usuarios en Firebase Authentication mediante email y contraseña.
 * Sus principales funciones son:
 * Permite al usuario introducir su email y contraseña.
 * Si el registro es exitoso, envía un correo de verificación y se envía al usuario a LoginActivity.
 */
class RegisterActivity : AppCompatActivity() {

    // Componentes de la UI
    private lateinit var btnRegister: Button
    private lateinit var btnLastRegister: TextView
    private lateinit var editUser: EditText
    private lateinit var editPassword: EditText
    private lateinit var editRepeatPassword: EditText
    private lateinit var auth: FirebaseAuth // Instancia para autenticación con Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Ajusta los márgenes para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()  // Inicializa los componentes de la interfaz
        start() // Configura los eventos de los botones
    }

    /**
     * Inicializa las referencias a los elementos de la UI y la instancia de FirebaseAuth.
     */
    private fun init() {
        btnRegister = findViewById(R.id.btn_register_in_register)
        btnLastRegister = findViewById(R.id.btn_last_register)
        editUser = findViewById(R.id.edit_user_register)
        editPassword = findViewById(R.id.edit_pass_register)
        editRepeatPassword = findViewById(R.id.pass_register_repeat_in_register)
        auth = Firebase.auth // Inicializa Firebase Authentication
    }

    /**
     * Configura los eventos de los botones.
     */
    private fun start() {
        // Evento para el botón de registro
        btnRegister.setOnClickListener {
            val email = editUser.text.toString()
            val pass = editPassword.text.toString()
            val repeatPass = editRepeatPassword.text.toString()

            // Validación de campos
            if (pass != repeatPass || email.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()) {
                Toast.makeText(this, "Campos vacíos y/o password diferentes", Toast.LENGTH_LONG).show()
            } else {
                // Intentar registrar el usuario
                registerUser(email, pass) { result, msg ->
                    Toast.makeText(this@RegisterActivity, msg, Toast.LENGTH_LONG).show()
                    if (result) startActivityLogin()
                }
            }
        }

        // Evento para el botón de "Ya tengo cuenta"
        btnLastRegister.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual
        }
    }

    /**
     * Inicia la actividad de inicio de sesión y cierra la actividad de registro.
     */
    private fun startActivityLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Registra un usuario en Firebase Authentication.
     * @param email Correo electrónico del usuario
     * @param pass Contraseña del usuario
     * @param onResult Callback con el resultado del registro
     */
    private fun registerUser(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Enviar email de verificación
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { verificationTask ->
                            val msg = if (verificationTask.isSuccessful) {
                                "Usuario Registrado. Verifique su correo"
                            } else {
                                "Usuario Registrado. ${verificationTask.exception?.message}"
                            }
                            auth.signOut() // Cerrar sesión para obligar la verificación
                            onResult(true, msg)
                        }
                        ?.addOnFailureListener { exception ->
                            Log.e("RegisterActivity", "Fallo al enviar correo: ${exception.message}")
                            onResult(false, "No se pudo enviar el correo: ${exception.message}")
                        }
                } else {
                    // Manejo de errores en el registro
                    try {
                        throw task.exception ?: Exception("Error desconocido")
                    } catch (e: FirebaseAuthUserCollisionException) {
                        onResult(false, "Ese usuario ya existe")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        onResult(false, "Contraseña débil: ${e.reason}")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        onResult(false, "Email no válido")
                    } catch (e: Exception) {
                        onResult(false, e.message.toString())
                    }
                }
            }
    }
}
