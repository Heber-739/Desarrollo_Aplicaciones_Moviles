package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils
import com.example.clubdeportivo.database.PagoCuotaSocioYNoSocio
import java.util.Calendar
import java.util.Locale

class MainPagoCuotaSocio : AppCompatActivity(),  ModalFragment.ModalListener {
    private val precioPorMes = 10000.00
    private val precioDescuento =  precioPorMes * 0.90
    private var precioFinal=precioDescuento
    private var metodoPago: String="Pago efectivo"
    private var fechaVencimientoFinal = ""
    lateinit var nombreCompleto: String
    lateinit var clienteDni: String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pago_de_cuota_socio)

        // Los datos obtenidos desde Registro de cliente
        nombreCompleto = intent.getStringExtra("nombreCompleto")?: ""
        clienteDni = intent.getStringExtra("dni")?: ""

        //Botones del menu
        val btnHome = findViewById<Button>(R.id.btn_nav_home)
        val btnRegis = findViewById<Button>(R.id.btn_nav_register)
        val btnCuot = findViewById<Button>(R.id.btn_nav_payment)
        val btnCarnet = findViewById<Button>(R.id.btn_nav_card)
        val btnReport = findViewById<Button>(R.id.btn_nav_reports)
        val btnPago = findViewById<Button>(R.id.btn_registrar_pago)

        val tvPrecioEfectivo = findViewById<TextView>(R.id.tv_precio_efectivo)
        val tvPrecioTarjeta = findViewById<TextView>(R.id.tv_precio_tarjeta)
        val checkEfectivo = findViewById<CheckBox>(R.id.check_efectivo)
        val checkTarjeta = findViewById<CheckBox>(R.id.check_tarjeta)

        tvPrecioTarjeta.text = "$%.2f".format(precioPorMes)
        tvPrecioEfectivo.text = "$%.2f".format(precioDescuento)

        // Configuración para permitir solo una selección a la vez en los checkbox
        checkEfectivo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkTarjeta.isChecked = false
                metodoPago= "Pago efectivo"
                precioFinal= precioDescuento
            }
        }

        checkTarjeta.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkEfectivo.isChecked = false
                metodoPago= "Pago con tarjeta"
                precioFinal= precioPorMes
            }
        }

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


        // Registrar el pago
        btnPago.setOnClickListener {
            val db = PagoCuotaSocioYNoSocio(this)

                val fechaActual = Calendar.getInstance().timeInMillis

                //Fecha de vencimiento segun los dias seleccionados
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = fechaActual
                    add(Calendar.DAY_OF_YEAR, 30)
                }

                val dateFormat = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val fechaVencimiento = dateFormat.format(calendar.time)
                fechaVencimientoFinal= fechaVencimiento

                val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val fechaActualFormateada = formatoFecha.format(fechaActual)

                // Inserta en la base de datos
                db.registrarPago(clienteDni.toIntOrNull() ?: 0, precioFinal, metodoPago, fechaActualFormateada, fechaVencimiento)

                val modalFragment = ModalFragment.newInstance(
                    title = "Pago Registrado!",
                    text = "¿Desea imprimir el recibo el pago?",
                    btnReject = "No, ir al home",
                    btnSuccess = "Si, imprimirlo"
                )
                modalFragment.show(supportFragmentManager, "ModalFragment")

        }
    }

    override fun onModalResult(success: Boolean) {
        if (success) {
            // Crear el Intent para la actividad de impresión
            val intent = Intent(this, MainReciboPago::class.java)

            // Pasar los datos necesarios
            intent.putExtra("NOMBRE_CLIENTE", nombreCompleto)
            intent.putExtra("ES_SOCIO",true)  // true o false dependiendo de si es socio o no
            intent.putExtra("FECHA_VENCIMIENTO", fechaVencimientoFinal)
            intent.putExtra("METODO_PAGO", metodoPago)
            intent.putExtra("PRECIO_FINAL", precioFinal)

            startActivity(intent)
        } else {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

    }
}