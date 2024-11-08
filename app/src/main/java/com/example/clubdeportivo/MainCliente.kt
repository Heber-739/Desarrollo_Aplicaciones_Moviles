package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.Utils
import com.example.clubdeportivo.database.Database
private var shouldNavigate = false
class MainCliente : AppCompatActivity(), ModalFragment.ModalListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_cliente)

        //menu
        val button1 = findViewById<Button>(R.id.btn_nav_home)
        val button2 = findViewById<Button>(R.id.btn_nav_register)
        val button3 = findViewById<Button>(R.id.btn_nav_payment)
        val button4 = findViewById<Button>(R.id.btn_nav_card)
        val button5 = findViewById<Button>(R.id.btn_nav_reports)

        val btnRegistro = findViewById<Button>(R.id.btnClienteRegistro)

        btnRegistro.setOnClickListener {
            //Capturas
            val clienteNombre = findViewById<EditText>(R.id.editClienteNombre)
            val dniCliente = findViewById<EditText>(R.id.editClienteDNI)
            val emailCliente = findViewById<EditText>(R.id.editClienteEmail)
            val fechaNMCliente = findViewById<EditText>(R.id.editClienteFechaNto)
            val radioApto = findViewById<RadioGroup>(R.id.radioGroupApto)
            val radioButtonSeleccionadoApto = findViewById<RadioButton>(radioApto.checkedRadioButtonId)
            val textoSeleccionadoApto = radioButtonSeleccionadoApto?.text.toString()
            val nombreCompleto = clienteNombre.text.toString().trim()
            val dni = dniCliente.text.toString().trim()
            val email = emailCliente.text.toString().trim()
            val fechaNacimiento = fechaNMCliente.text.toString().trim()


            // Validación
            if (nombreCompleto.isEmpty() || !nombreCompleto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?: [a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$".toRegex())) {
                val modalFragment = ModalFragment.newInstance(
                    title = "Atencion!",
                    text = "El nombre solo puede contener letras y no debe estar vacío",
                    btnSuccess = "Aceptar"
                )
                modalFragment.show(supportFragmentManager, "ModalFragment")

                return@setOnClickListener
            }



            if (dni.isEmpty() || !dni.matches("\\d+".toRegex()) || dni.length !in 7..10) {
                val modalFragmentDni = ModalFragment.newInstance(
                    title = "Atencion!",
                    text = "El DNI debe contener entre 7 y 10 dígitos numéricos",
                    btnSuccess = "Aceptar"
                )
                modalFragmentDni.show(supportFragmentManager, "ModalFragmentDni")

                return@setOnClickListener
            }


            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                val modalFragmentEmail = ModalFragment.newInstance(
                    title = "Atencion!",
                    text = "Introduce un correo electrónico válido",
                    btnSuccess = "Aceptar"
                )
                modalFragmentEmail.show(supportFragmentManager, "ModalFragmentEmail")

                return@setOnClickListener
            }


            if (fechaNacimiento.isEmpty() || !fechaNacimiento.matches("\\d{2}/\\d{2}/\\d{4}".toRegex())) {
                val modalFragmentFecha = ModalFragment.newInstance(
                    title = "Atencion!",
                    text = "Introduce la fecha en formato DD/MM/AAAA",
                    btnSuccess = "Aceptar"
                )
                modalFragmentFecha.show(supportFragmentManager, "ModalFragmentFecha")

                return@setOnClickListener
            }


            // Si todas las validaciones son correctas, continúa con el flujo
            // Guardar en la base de datos
            val radioGroup = findViewById<RadioGroup>(R.id.radioGroupSocio)
            val radioButtonSeleccionado = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val dbHelper = Database(this)
            val db = dbHelper.writableDatabase

// Crear un objeto ContentValues para insertar los datos directamente
            val values = ContentValues().apply {
                put(Database.COLUMN_CLIENTE_DNI, dni)
                put(Database.COLUMN_NOMBRE_CLIENTE, nombreCompleto)
                put(Database.COLUMN_EMAIL_CLIENTE, email)
                put(Database.COLUMN_FECHA_NAC, fechaNacimiento)
                put(Database.COLUMN_APTO_FISICO, textoSeleccionadoApto == "Sí") //Lo combierte a boolean
                put(Database.COLUMN_TIPO_CLIENTE, radioButtonSeleccionado.text.toString())
                put(Database.COLUMN_NRO_AVATAR, 1)
            }

// Insertar los datos en la tabla Clientes
            val newRowId = db.insert(Database.TABLE_CLIENTES, null, values)

// Si la inserción fue exitosa
            if (newRowId != -1L) {
                Toast.makeText(this, "Cliente registrado con Exito!", Toast.LENGTH_SHORT).show()

                val tipoSocio = radioButtonSeleccionado?.text?.toString()?.trim() ?: ""

                Log.d("tipoSocio", tipoSocio)  // Esto imprimirá lo que está seleccionado.

                val actividadDestino = if (tipoSocio.equals("Socio", ignoreCase = true)) {
                    MainPagoCuotaSocio::class.java
                } else {
                    MainPagoCuotaNoSocio::class.java
                }

                val intent = Intent(this, actividadDestino).apply {
                    putExtra("nombreCompleto", nombreCompleto)
                    putExtra("dni", dni)
                    putExtra("email", email)
                    putExtra("fechaNacimiento", fechaNacimiento)
                    putExtra("apto", textoSeleccionadoApto)
                }

                startActivity(intent)
                finish()

            } else {
                val modalFragmentError = ModalFragment.newInstance(
                    title = "Ups!",
                    text = "Error al registrar el cliente",
                    btnSuccess = "Aceptar"
                )
                modalFragmentError.show(supportFragmentManager, "ModalFragmentError")
            }

            //transfiere los datos a las pantallas socio o no socios


        }
        //menu inferior
        button1.setOnClickListener {
            Utils.cambioPantalla(this, MainMenu::class.java)
        }
        button2.setOnClickListener {
            Utils.cambioPantalla(this, MainCliente::class.java)
        }
        button3.setOnClickListener {
            Utils.cambioPantalla(this, MainMenu::class.java)
        }
        button4.setOnClickListener {
            Utils.cambioPantalla(this, CustomerCard::class.java)
        }
        button5.setOnClickListener {
            Utils.cambioPantalla(this, CustomersRegister::class.java)
        }




    }
    override fun onModalResult(success: Boolean) {
        shouldNavigate = success

    }
}
