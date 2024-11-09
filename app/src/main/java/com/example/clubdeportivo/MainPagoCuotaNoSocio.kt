package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils
import com.example.clubdeportivo.database.PagoCuotaSocioYNoSocio
import java.util.Calendar
import java.util.Locale

class MainPagoCuotaNoSocio : AppCompatActivity(),  ModalFragment.ModalListener {

    private val precioPorDia = 1200.0
    private val precioDescuento =  precioPorDia * 0.90
    private var precioTotal=0.00
    private var metodoPago: String = "Pago efectivo"
    private var fechaVencimientoFinal = ""
    lateinit var nombreCompleto: String
    lateinit var clienteDni: String


        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.pago_de_cuota_nosocio)

            // Los datos obtenidos desde Registro de cliente
            nombreCompleto = intent.getStringExtra("nombreCompleto")?: ""
            clienteDni = intent.getStringExtra("dni")?: ""

            val btnHome = findViewById<Button>(R.id.btn_nav_home)
            val btnRegis = findViewById<Button>(R.id.btn_nav_register)
            val btnCuot = findViewById<Button>(R.id.btn_nav_payment)
            val btnCarnet = findViewById<Button>(R.id.btn_nav_card)
            val btnReport = findViewById<Button>(R.id.btn_nav_reports)
            val btnPago = findViewById<Button>(R.id.btn_registrar_pago)
            val editTextDias = findViewById<EditText>(R.id.editText_dias)
            val tvPrecioTarjeta = findViewById<TextView>(R.id.tv_precio_tarjeta)
            val tvPrecioEfectivo = findViewById<TextView>(R.id.tv_precio_efectivo)
            val checkEfectivo = findViewById<CheckBox>(R.id.check_efectivo)
            val checkTarjeta = findViewById<CheckBox>(R.id.check_tarjeta)

            tvPrecioTarjeta.text = "$%.2f".format(precioPorDia)
            tvPrecioEfectivo.text = "$%.2f".format(precioDescuento)

            editTextDias.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val diasIngresados = s.toString().toIntOrNull()
                    if (diasIngresados != null && diasIngresados > 0) {
                        precioTotal = diasIngresados * precioPorDia
                        val precioTotalDescuento = precioTotal * 0.90
                        tvPrecioTarjeta.text = "$%.2f".format(precioTotal)
                        tvPrecioEfectivo.text = "$%.2f".format(precioTotalDescuento)
                    }
                    else {
                        tvPrecioTarjeta.text = "$%.2f".format(precioPorDia)
                        tvPrecioEfectivo.text = "$%.2f".format(precioPorDia * 0.90)
                    }
                }
            })

            // Configuración para permitir solo una selección a la vez en los checkbox
            checkEfectivo.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkTarjeta.isChecked = false
                    metodoPago= "Pago efectivo"
                    precioTotal *= 0.90
                }
            }

            checkTarjeta.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkEfectivo.isChecked = false
                    metodoPago= "Pago con tarjeta"
                }
            }

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

            // Registrar el pago
            btnPago.setOnClickListener {
                val db = PagoCuotaSocioYNoSocio(this)
                val diasIngresados = editTextDias.text.toString().toIntOrNull() ?: 0 // Convierte el texto a entero o usa 0 si está vacío
                if (diasIngresados<=0){
                    Toast.makeText(this, "Ingrese cantidad de dias", Toast.LENGTH_SHORT).show()

                    return@setOnClickListener
                    }
                else {
                        val fechaActual = Calendar.getInstance().timeInMillis

                        //Fecha de vencimiento segun los dias seleccionados
                        val calendar = Calendar.getInstance().apply {
                            timeInMillis = fechaActual
                            add(Calendar.DAY_OF_YEAR, diasIngresados)
                        }

                        val dateFormat =
                            java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val fechaVencimiento = dateFormat.format(calendar.time)
                        fechaVencimientoFinal = fechaVencimiento

                        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val fechaActualFormateada = formatoFecha.format(fechaActual)

                        // Inserta en la base de datos
                        db.registrarPago(
                            clienteDni.toIntOrNull() ?: 0,
                            precioTotal,
                            metodoPago,
                            fechaActualFormateada,
                            fechaVencimiento
                        )

                        val modalFragment = ModalFragment.newInstance(
                            title = "Pago Registrado!",
                            text = "¿Desea imprimir el recibo el pago?",
                            btnReject = "No, ir al home",
                            btnSuccess = "Si, imprimirlo"
                        )
                        modalFragment.show(supportFragmentManager, "ModalFragment")
                    }
            }
        }

    override fun onModalResult(success: Boolean) {
        if (success) {
            val intent = Intent(this, MainReciboPago::class.java)

            // Pasar los datos necesarios
            intent.putExtra("NOMBRE_CLIENTE", nombreCompleto)
            intent.putExtra("ES_SOCIO",false)  // true o false dependiendo de si es socio o no
            intent.putExtra("FECHA_VENCIMIENTO", fechaVencimientoFinal)
            intent.putExtra("METODO_PAGO", metodoPago)
            intent.putExtra("PRECIO_FINAL", precioTotal)

            startActivity(intent)
        } else {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

    }
}