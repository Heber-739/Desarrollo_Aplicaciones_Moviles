package com.example.clubdeportivo

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CustomersRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customers_register)
        val btnHome= findViewById<Button>(R.id.btn_nav_home)
        val btnRegis = findViewById<Button>(R.id.btn_nav_register)
        val btnCuot = findViewById<Button>(R.id.btn_nav_payment)
        val btnCarnet = findViewById<Button>(R.id.btn_nav_card)
        val btnReport = findViewById<Button>(R.id.btn_nav_reports)

        btnHome.setOnClickListener{
            Utils.cambioPantalla(this, MainMenu::class.java)
        }
        btnRegis.setOnClickListener{
            Utils.cambioPantalla(this, MainCliente::class.java)
        }
        btnCuot.setOnClickListener{
            Utils.cambioPantalla(this, MainMenu::class.java)
        }
        btnCarnet.setOnClickListener{
            Utils.cambioPantalla(this, CustomerCard::class.java)
        }
        btnReport.setOnClickListener{
            Utils.cambioPantalla(this, CustomersRegister::class.java)
        }


        val btnRegresar = findViewById<Button>(R.id.btn_back)
        btnRegresar.setOnClickListener{
            Utils.cambioPantalla(this, MainMenu::class.java)
        }
    }
}