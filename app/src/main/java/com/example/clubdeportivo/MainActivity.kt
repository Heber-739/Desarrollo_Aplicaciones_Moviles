package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(),  ModalFragment.ModalListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //aqui hace el cambio de pantalla

        val button = findViewById<Button>(R.id.btnLogin)
        var pass = true

        button.setOnClickListener {
            if (pass) {
                pass = false
                val modal = ModalFragment.newInstance("Campos  invalidos", "No se aceptan campos vacios", "OK", "Reiniciar")
                modal.show(supportFragmentManager, "ModalFragment")
            } else {
                Utils.cambioPantalla(this, MainMenu::class.java)
            }

        }

        val buttonRegister = findViewById<TextView>(R.id.txtResgitrate)
        buttonRegister.setOnClickListener {
            Utils.cambioPantalla(this, MainUsuario::class.java)
        }
    }

    override fun onModalResult(success: Boolean) {
        // Esta funcion se ejecuta al cerrar el modal
        var email = findViewById<TextView>(R.id.editLoginEmail)
        email.text =  if(success) "success" else "reject"
    }


}