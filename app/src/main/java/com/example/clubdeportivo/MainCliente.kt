package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
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

        //Capturas
        val clienteNombre = findViewById<EditText>(R.id.editClienteNombre)
        val dniCliente = findViewById<EditText>(R.id.editClienteDNI)
        val emailCliente = findViewById<EditText>(R.id.editClienteEmail)
        val fechaNMCliente = findViewById<EditText>(R.id.editClienteFechaNto)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupSocio)
        val radioButtonSeleccionado = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        val radioApto = findViewById<RadioGroup>(R.id.radioGroupApto)
        val radioButtonSeleccionadoApto = findViewById<RadioButton>(radioApto.checkedRadioButtonId)
        val textoSeleccionadoApto = radioButtonSeleccionadoApto?.text.toString()


        btnRegistro.setOnClickListener {
            val nombreCompleto = clienteNombre.text.toString().trim()
            val dni = dniCliente.text.toString().trim()
            val email = emailCliente.text.toString().trim()
            val fechaNacimiento = fechaNMCliente.text.toString().trim()
            var isValid = true

            // Validación
            if (nombreCompleto.isEmpty() || !nombreCompleto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+".toRegex())) {
                clienteNombre.error = "El nombre solo puede contener letras y no debe estar vacío"
                isValid = false
            }


            if (dni.isEmpty() || !dni.matches("\\d+".toRegex()) || dni.length !in 7..10) {
                dniCliente.error = "El DNI debe contener entre 7 y 10 dígitos numéricos"
                isValid = false
            }


            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailCliente.error = "Introduce un correo electrónico válido"
                isValid = false
            }


            if (fechaNacimiento.isEmpty() || !fechaNacimiento.matches("\\d{2}/\\d{2}/\\d{4}".toRegex())) {
                fechaNMCliente.error = "Introduce la fecha en formato DD/MM/AAAA"
                isValid = false
            }


            if (isValid) {
                val tipoSocio = radioButtonSeleccionado.text.toString() // Obtiene el tipo de socio


                val actividadDestino = if (tipoSocio == "Socio") {
                    MainPagoCuotaSocio::class.java
                } else {
                    MainPagoCuotaNoSocio::class.java
                }


                Utils.cambioPantalla(this, actividadDestino)


                val intent = Intent(this, actividadDestino).apply {
                    putExtra("nombreCompleto", nombreCompleto)
                    putExtra("dni", dni)
                    putExtra("email", email)
                    putExtra("fechaNacimiento", fechaNacimiento)
                    putExtra("apto", textoSeleccionadoApto)
                }


                startActivity(intent)
            }


        }
    }
}