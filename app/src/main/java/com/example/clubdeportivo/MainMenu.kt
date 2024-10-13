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
        val buttonSalir = findViewById<Button>(R.id.btn_exit)
        val buttonReCliente = findViewById<Button>(R.id.btn_customers_register)
        val btnCarnet = findViewById<Button>(R.id.btn_emmit_card)
        buttonUsu.setOnClickListener {
            Utils.cambioPantalla(this,MainCliente::class.java)
        }
        buttonSalir.setOnClickListener{
            Utils.cambioPantalla(this,MainActivity::class.java)
        }
        buttonReCliente.setOnClickListener{
            Utils.cambioPantalla(this,CustomersRegister::class.java)
        }
        btnCarnet.setOnClickListener{
            Utils.cambioPantalla(this, CustomerCard::class.java)
        }
    }



}