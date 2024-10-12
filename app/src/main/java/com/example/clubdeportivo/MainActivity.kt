package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //aqui hace el cambio de pantalla
        //hay que hacer logica de base de datos y verificacion con los EditText
        val button = findViewById<Button>(R.id.btnLogin)
        button.setOnClickListener {

            cambioPantalla(MainMenu::class.java)
        }
    }
    // Función que inicia la segunda actividad
    fun cambioPantalla(actividad: Class<*>) {
        val intent = Intent(this, actividad)
        startActivity(intent)
    }



}