package com.pmm.proyecto_leido

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Recuperar el nombre de usuario del Intent
        val username = intent.getStringExtra("USERNAME")

        // Referencia al TextView del layout
        val usernameTextView = findViewById<TextView>(R.id.usernameTextView)
        usernameTextView.text = "Hola $username"
    }
}