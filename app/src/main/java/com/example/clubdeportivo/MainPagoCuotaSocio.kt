package com.example.clubdeportivo

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils
import com.example.clubdeportivo.database.Database
import com.example.clubdeportivo.database.PagoCuotaSocio
import java.util.Calendar

class MainPagoCuotaSocio : AppCompatActivity() {
    private var clienteDni: Int = 1111
    private var precio: Double =0.0
    private var metodoPago: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pago_de_cuota_socio)
        val helper = Database

        //Botones del menu
        val btnHome = findViewById<Button>(R.id.btn_nav_home)
        val btnRegis = findViewById<Button>(R.id.btn_nav_register)
        val btnCuot = findViewById<Button>(R.id.btn_nav_payment)
        val btnCarnet = findViewById<Button>(R.id.btn_nav_card)
        val btnReport = findViewById<Button>(R.id.btn_nav_reports)
        val btnPago = findViewById<Button>(R.id.btn_registrar_pago)

        //Botones del RadioGroup y RadioButtons
        val radioGroupMetodosPago = findViewById<RadioGroup>(R.id.rg_metodos_pago)
        val radioButtonEfectivo = findViewById<RadioButton>(R.id.rb_efectivo)
        val radioButtonTarjeta = findViewById<RadioButton>(R.id.rb_tarjeta)
        val textViewPrecioEfectivo = findViewById<TextView>(R.id.tv_precio_efectivo)
        val textViewPrecioTarjeta = findViewById<TextView>(R.id.tv_precio_tarjeta)

        btnHome.setOnClickListener {
            Utils.cambioPantalla(this, MainMenu::class.java)
        }
        btnRegis.setOnClickListener {
            Utils.cambioPantalla(this, MainCliente::class.java)
        }
        btnCuot.setOnClickListener {
            Utils.cambioPantalla(this, MainMenu::class.java)
        }
        btnCarnet.setOnClickListener {
            Utils.cambioPantalla(this, CustomerCard::class.java)
        }
        btnReport.setOnClickListener {
            Utils.cambioPantalla(this, CustomersRegister::class.java)
        }
        btnPago.setOnClickListener {
            Utils.cambioPantalla(this, MainReciboPago::class.java)
        }


        radioGroupMetodosPago.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_efectivo -> {
                    //  Pago en efectivo
                    metodoPago = "Efectivo"
                    precio = textViewPrecioEfectivo.text.toString().toDouble()

                }

                R.id.rb_tarjeta -> {
                    // Pago con tarjeta
                    metodoPago = "Tarjeta"
                    precio = textViewPrecioTarjeta.text.toString().toDouble()

                }
            }

        }

        // Registrar el pago
        btnPago.setOnClickListener {
            val db = PagoCuotaSocio(this)

            if (metodoPago != null) {
                val fechaActual = Calendar.getInstance().timeInMillis

                //Calcula la fecha de vencimiento
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = fechaActual
                calendar.add(Calendar.DAY_OF_YEAR, 30)
                val vencimiento = calendar.timeInMillis

                // Inserta en la base de datos
                db.registrarPago(clienteDni, precio, metodoPago!!, fechaActual, vencimiento)

                //Debe salir un modal de pago exitoso

            } else {
                //Debe salir el modal de que por favor seleccione un metodo de pago.
            }
        }

    }
}