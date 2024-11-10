package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo.Utils.User
import com.example.clubdeportivo.Utils.Utils
import com.example.clubdeportivo.database.Database

class MainActivity : AppCompatActivity(), ModalFragment.ModalListener {

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa la base de datos y prueba la conexión
        val dbHelper = Database(this)
        val db = dbHelper.writableDatabase

        // Comprueba si la base de datos está abierta correctamente
        if (db.isOpen) {
            Toast.makeText(this, "Conexión a la base de datos establecida", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al conectar a la base de datos", Toast.LENGTH_SHORT).show()
        }

        // Obtén las referencias a los campos de entrada
        emailField = findViewById(R.id.editLoginEmail)
        passwordField = findViewById(R.id.editLoginPass)

        // Configura el botón de login
        val buttonLogin = findViewById<Button>(R.id.btnLogin)
        buttonLogin.setOnClickListener {
            loginUser()
        }

        // Configura el botón de registro
        val buttonRegister = findViewById<TextView>(R.id.txtResgitrate)
        buttonRegister.setOnClickListener {
            Utils.cambioPantalla(this, MainUsuario::class.java)
        }
    }

    private fun loginUser() {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Verifica si los campos están vacíos
            if (email.isEmpty() || password.isEmpty()) {
                val modal = ModalFragment.newInstance("Inicio de sesión incorrecto", "Debe completar todos los" +
                        " campos", "OK")
                modal.show(supportFragmentManager, "ModalFragment")
                return
            }

            // Aquí deberías añadir la lógica para verificar el usuario en la base de datos
            val dbHelper = Database(this)
            val db = dbHelper.readableDatabase

            val query = "SELECT * FROM ${Database.TABLE_USERS} WHERE email_usuario = '$email' AND password_usuario = '$password'"
            val cursor = db.rawQuery(query, null)

        try {
            if (cursor.moveToFirst()) {
                // Obtén el nombre y el email del usuario desde el cursor
                val userName = cursor.getString(cursor.getColumnIndexOrThrow("nombre_usuario"))
                val userEmail = cursor.getString(cursor.getColumnIndexOrThrow("email_usuario"))
                val nro_avatar = cursor.getInt(cursor.getColumnIndexOrThrow("nro_avatar"))

                User.avatar = nro_avatar
                User.name = userName
                User.email = userEmail
                // Si hay un usuario, iniciar sesión
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                //Utils.cambioPantalla(this, MainMenu::class.java) // SE REEMPLAZA EL METODO DE CAMBIO DE PANTALLA POR EL DE ABAJO PARA PASAR PARAMETROS
                val intent = Intent(this, MainMenu::class.java)
                intent.putExtra("USER_NAME", userName)  // Pasa el nombre del usuario
                intent.putExtra("USER_EMAIL", userEmail)  // Pasa el correo electrónico del usuario
                startActivity(intent)

            } else {
                // Si no hay coincidencias
                val modal = ModalFragment.newInstance("Error de inicio de sesión", "Credenciales" +
                        " incorrectas", "OK", "Reiniciar")
                modal.show(supportFragmentManager, "ModalFragment")
            }
        } catch (e: Exception) {
            val modal = ModalFragment.newInstance("Error de inicio de sesión", "Excepcion de consulta, motivo: ${e.message}",
                "OK")
            modal.show(supportFragmentManager, "ModalFragment")
        }

        cursor.close()
    }

    override fun onModalResult(success: Boolean) {
        passwordField.setText("");
    }
}
