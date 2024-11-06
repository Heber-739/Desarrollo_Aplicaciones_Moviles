package com.example.clubdeportivo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils
import com.example.clubdeportivo.database.PagoCuotaSocio
import java.util.Calendar

class MainPagoCuotaNoSocio : AppCompatActivity(),  ModalFragment.ModalListener {

    private val precioPorDia = 1200.0
    private val precioDescuento =  precioPorDia * 0.90
    private val precioFinal=0.00
    private var metodoPago: String?=null
    private var clienteDni: Int = 1111

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.pago_de_cuota_nosocio)

            val btnHome = findViewById<Button>(R.id.btn_nav_home)
            val btnRegis = findViewById<Button>(R.id.btn_nav_register)
            val btnCuot = findViewById<Button>(R.id.btn_nav_payment)
            val btnCarnet = findViewById<Button>(R.id.btn_nav_card)
            val btnReport = findViewById<Button>(R.id.btn_nav_reports)
            val btnPago = findViewById<Button>(R.id.btn_registrar_pago)
            val spinnerDias = findViewById<Spinner>(R.id.spinner_dias)
            val tvPrecioTarjeta = findViewById<TextView>(R.id.tv_precio_tarjeta)
            val tvPrecioEfectivo = findViewById<TextView>(R.id.tv_precio_efectivo)
            val checkEfectivo = findViewById<CheckBox>(R.id.check_efectivo)
            val checkTarjeta = findViewById<CheckBox>(R.id.check_tarjeta)

            tvPrecioTarjeta.text = "$%.2f".format(precioPorDia)
            tvPrecioEfectivo.text = "$%.2f".format(precioDescuento)

            //Selector de dias
            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.dias_acceso_array,
                android.R.layout.simple_spinner_item
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spinnerDias.adapter = adapter

            spinnerDias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val diasSeleccionados = parent.getItemAtPosition(position).toString()
                    if (diasSeleccionados == "Seleccione cantidad de días de acceso") {
                        val modalFragment = ModalFragment.newInstance(
                            title = "Selección inválida",
                            text = "Por favor, seleccione una cantidad válida de días de acceso.",
                            btnSuccess = "Aceptar"
                        )
                        modalFragment.show(supportFragmentManager, "ModalFragment")
                    } else {
                        // Calcula el precio en función de los días seleccionados
                        val cantidadDias = diasSeleccionados.toIntOrNull() ?: 1
                        val precioTotal = cantidadDias * precioPorDia
                        val precioTotalDescuento = precioTotal *0.90

                        tvPrecioTarjeta.text = "$%.2f".format(precioTotal)
                        tvPrecioEfectivo.text = "$%.2f".format(precioTotalDescuento)

                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }

            // Configuración para permitir solo una selección a la vez en los checkbox
            checkEfectivo.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkTarjeta.isChecked = false
                    metodoPago= "Pago efectivo"
                }
            }

            checkTarjeta.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkEfectivo.isChecked = false
                    metodoPago= "Pago efectivo"
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
            btnPago.setOnClickListener{
                Utils.cambioPantalla(this, MainReciboPago::class.java)
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
                    db.registrarPago(clienteDni, precioFinal, metodoPago!!, fechaActual, vencimiento)

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


    }


}