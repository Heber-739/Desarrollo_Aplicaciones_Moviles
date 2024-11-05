package com.example.clubdeportivo

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Obtén los datos enviados desde la actividad anterior
        val userName = intent.getStringExtra("USER_NAME")
        val userEmail = intent.getStringExtra("USER_EMAIL")

        val profileCont = findViewById<FrameLayout>(R.id.profileCont)

        setContentView(R.layout.activity_main_menu)

        // Referencias a los TextView en los que mostrar el nombre y email
        val nameTextView = findViewById<TextView>(R.id.txt_user_name)
        val emailTextView = findViewById<TextView>(R.id.txt_user_email)

        // Asigna los valores a los TextView
        nameTextView.text = userName ?: "Nombre no disponible"
        emailTextView.text = userEmail ?: "Email no disponible"


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