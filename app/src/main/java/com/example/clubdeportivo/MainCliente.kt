package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainCliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_cliente)
        val button = findViewById<ImageButton>(R.id.btnHome)
        button.setOnClickListener{
            cambioPantalla(MainMenu::class.java)
        }

    }
    fun cambioPantalla(actividad: Class<*>) {
        val intent = Intent(this, actividad)
        startActivity(intent)
    }
}