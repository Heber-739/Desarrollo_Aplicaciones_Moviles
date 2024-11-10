package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils

class MainReciboPago : AppCompatActivity(), ModalFragment.ModalListener {

    // Declaración de variables de clase
    private var nombreCompleto: String? = null
    private var fechaVencimiento: String? = null
    private var esSocio: Boolean = false

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

        // Inicialización de variables de clase
        nombreCompleto = intent.getStringExtra("NOMBRE_CLIENTE")
        esSocio = intent.getBooleanExtra("ES_SOCIO", false)
        fechaVencimiento = intent.getStringExtra("FECHA_VENCIMIENTO")
        val metodoPago = intent.getStringExtra("METODO_PAGO")
        val precioFinal = intent.getDoubleExtra("PRECIO_FINAL", 0.0)

        tvNames.text = nombreCompleto ?: "Nombre y apellido"
        tvCuota.text = if (esSocio) "Pago de cuota de Socio" else "Pago de días de No socio"
        tvFecha.text = fechaVencimiento ?: "Fecha no disponible"
        tvMetodoPago.text = metodoPago ?: "Método de pago no disponible"
        tvPrecio.text = "$%.2f".format(precioFinal)

        btnImprimir.setOnClickListener {
            val modalFragment = ModalFragment.newInstance(
                title = "Recibo impreso",
                text = "¿Desea emitir el carnet del socio?",
                btnReject = "No, ir al home",
                btnSuccess = "Sí, emitirlo"
            )
            modalFragment.show(supportFragmentManager, "ModalFragment")
        }
    }

    override fun onModalResult(success: Boolean) {
        if (success) {
            val intentCarnet = Intent(this, CustomerCard::class.java)
            intentCarnet.putExtra("nombreCompleto", nombreCompleto)
            intentCarnet.putExtra("vencimiento", fechaVencimiento)
            intentCarnet.putExtra("esSocio", esSocio)
            startActivity(intentCarnet)
        } else {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
    }
}
