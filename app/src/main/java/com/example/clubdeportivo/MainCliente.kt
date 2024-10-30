package com.example.clubdeportivo

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils

class MainCliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_cliente)
        val button = findViewById<Button>(R.id.btn_nav_home)
        val btnRegistro = findViewById<Button>(R.id.btnClienteRegistro)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupSocio)
        button.setOnClickListener{
            Utils.cambioPantalla(this,MainMenu::class.java)
        }
        btnRegistro.setOnClickListener{
            if(radioGroup.checkedRadioButtonId == R.id.radioButtonSocio){
            Utils.cambioPantalla(this, MainPagoCuotaSocio::class.java)
            } else {
            Utils.cambioPantalla(this, MainPagoCuotaNoSocio::class.java)
            }

        }

    }

}