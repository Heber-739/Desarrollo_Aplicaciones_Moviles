package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        val buttonUsu = findViewById<Button>(R.id.btn_register_user)
        val buttonSalir = findViewById<Button>(R.id.btn_salir)

        buttonUsu.setOnClickListener {
            cambioPantalla(MainCliente::class.java)
        }
        buttonSalir.setOnClickListener{
            cambioPantalla(MainActivity::class.java)
        }
    }
    // Función que inicia la segunda actividad
    fun cambioPantalla(actividad: Class<*>) {
        val intent = Intent(this, actividad)
        startActivity(intent)
    }

}