package com.example.clubdeportivo

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val profileCont = findViewById<FrameLayout>(R.id.profileCont);

        setContentView(R.layout.activity_main_menu)
        val buttonUsu = findViewById<Button>(R.id.btn_register_user)
        val buttonSalir = findViewById<Button>(R.id.btn_exit)
        val buttonReCliente = findViewById<Button>(R.id.btn_customers_report)
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