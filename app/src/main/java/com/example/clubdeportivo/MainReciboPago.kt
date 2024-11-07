package com.example.clubdeportivo

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainReciboPago : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recibo_de_pago)
        val tvCuota = findViewById<TextView>(R.id.txt_cuota)
        val tvFecha = findViewById<TextView>(R.id.txt_customer_end_date)
        val tvMetodoPago = findViewById<TextView>(R.id.txt_MetodoPago)
        val tvPrecio = findViewById<TextView>(R.id.txt_Precio)

        // Obtén los datos enviados desde la actividad anterior
        val esSocio = intent.getStringExtra("ES_SOCIO")
        val fechaVencimiento = intent.getStringExtra("FECHA_VENCIMIENTO")
        val metodoPago = intent.getStringExtra("METODO_PAGO")
        val precioFinal = intent.getStringExtra("PRECIO_FINAL")

        tvCuota.text = esSocio ?: "Pago de No socio"
        tvFecha.text= fechaVencimiento ?: "Fecha no disponible"
        tvMetodoPago.text= metodoPago ?: "Metodo de pago no disponible"
        tvPrecio.text= precioFinal ?: "Precio no disponible"
    }
}