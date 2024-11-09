package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils

class MainReciboPago : AppCompatActivity() , ModalFragment.ModalListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recibo_de_pago)
        val btnImprimir = findViewById<TextView>(R.id.buttonImprimir)

        val tvCuota = findViewById<TextView>(R.id.txt_cuota)
        val tvFecha = findViewById<TextView>(R.id.txt_customer_end_date)
        val tvMetodoPago = findViewById<TextView>(R.id.txt_MetodoPago)
        val tvPrecio = findViewById<TextView>(R.id.txt_Precio)
        val tvNames = findViewById<TextView>(R.id.txt_customer_names)

        // Obtén los datos enviados desde la actividad anterior
        val nombreCompleto = intent.getStringExtra("NOMBRE_CLIENTE")
        val esSocio = intent.getBooleanExtra("ES_SOCIO", false)
        val fechaVencimiento = intent.getStringExtra("FECHA_VENCIMIENTO")
        val metodoPago = intent.getStringExtra("METODO_PAGO")
        val precioFinal = intent.getDoubleExtra("PRECIO_FINAL", 0.0)

        tvNames.text= nombreCompleto ?:"Nombre y apellido"
        if(esSocio){
            tvCuota.text= "Pago de cuota de Socio"
        }
        else tvCuota.text= "Pago de dias de No socio"

        tvFecha.text= fechaVencimiento ?: "Fecha no disponible"
        tvMetodoPago.text= metodoPago ?: "Metodo de pago no disponible"
        tvPrecio.text= "$%.2f".format(precioFinal)

        btnImprimir.setOnClickListener{
            val modalFragment = ModalFragment.newInstance(
                title = "Recibo impreso",
                text = "¿Desea emitir el carnet del socio?",
                btnReject = "No, ir al home",
                btnSuccess = "Si, emitirlo"
            )
            modalFragment.show(supportFragmentManager, "ModalFragment")
        }
    }

    override fun onModalResult(success: Boolean) {
        if (success) {
            val intent = Intent(this, CustomerCard::class.java)

            //DEBE PASARLE EL NOMBRE Y APELLIDO y la fecha de vencimiento
            startActivity(intent)
        } else {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

    }
}