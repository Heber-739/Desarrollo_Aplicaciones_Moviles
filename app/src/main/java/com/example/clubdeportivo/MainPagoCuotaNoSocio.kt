package com.example.clubdeportivo

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainPagoCuotaNoSocio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pago_de_cuota_nosocio)

        val btnHome= findViewById<ImageButton>(R.id.btnHome)
        val btnRegis = findViewById<ImageButton>(R.id.btnAnadir)
        val btnCuot = findViewById<ImageButton>(R.id.btnPago)
        val btnCarnet = findViewById<ImageButton>(R.id.btnCarnet)
        val btnReport = findViewById<ImageButton>(R.id.btnReportes)
        val btnPago = findViewById<Button>(R.id.btn_registrar_pago)

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
        btnPago.setOnClickListener{
            Utils.cambioPantalla(this, MainReciboPago::class.java)
        }
    }
}