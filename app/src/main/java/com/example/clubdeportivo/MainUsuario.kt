package com.example.clubdeportivo

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.database.Database

class MainUsuario : AppCompatActivity() {

    private lateinit var editClienteNombre: EditText
    private lateinit var editClienteEmail: EditText
    private lateinit var editClientePass: EditText
    private lateinit var editClientePass2: EditText
    private lateinit var chkUsuario: CheckBox
    private lateinit var btnRegistroUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_usuario)

        editClienteNombre = findViewById(R.id.editClienteNombre)
        editClienteEmail = findViewById(R.id.editClienteEmail)
        editClientePass = findViewById(R.id.editClientePass)
        editClientePass2 = findViewById(R.id.editClientePass2)
        chkUsuario = findViewById(R.id.chkUsuario)
        btnRegistroUsuario = findViewById(R.id.btnRegistroUsuario)

        btnRegistroUsuario.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val nombre = editClienteNombre.text.toString()
        val email = editClienteEmail.text.toString()
        val password = editClientePass.text.toString()
        val confirmPassword = editClientePass2.text.toString()

        // Validamos campos vacios
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Validamos que las contraseñas sean iguales
        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        // Validamos Aceptacion de términos y condiciones
        if (!chkUsuario.isChecked) {
            Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar en la base de datos
        val dbHelper = Database(this)
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Database.COLUMN_USERS_EMAIL, email)
            put(Database.COLUMN_USERS_NAME, nombre)
            put(Database.COLUMN_USERS_PASS, password)
            put(Database.COLUMN_USERS_ROL, 1) // Cambia esto según tu lógica
        }

        val newRowId = db.insert(Database.TABLE_USERS, null, values)

        if (newRowId != -1L) {
            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad y vuelve a la anterior
        } else {
            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
        }
    }
}
