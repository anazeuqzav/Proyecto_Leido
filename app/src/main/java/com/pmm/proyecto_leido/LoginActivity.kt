package com.pmm.proyecto_leido

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    // Constantes estáticas de usuario y contraseña
    companion object {
        const val MYUSER = "Anazeuqzav"
        const val MYPASS = "root"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Referencias a los elementos del layout
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        // Configurar el botón de login
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim() // elimina los espacios
            val password = passwordEditText.text.toString().trim() // elimina los espacios

            // Comprobación si los campos están vacíos
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Introduce un nombre de usuario y una contraseña", Toast.LENGTH_SHORT).show()
              // Comprobación si el usuario es correcto
            } else if (username != MYUSER) {
                Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
              // Comprobación si la contraseña es correcta
            } else if (password != MYPASS) {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
              // Comprobación si ambas credenciales son correctas
            } else if (username == MYUSER && password == MYPASS) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USERNAME", username) // Pasa el nombre de usuario
                startActivity(intent)
                finish() // Finaliza LoginActivity
            } else {
                // Si se produce algun otro error con las credencias
                Toast.makeText(this, "Error en las credenciales", Toast.LENGTH_SHORT).show()
            }
        }

    }
}