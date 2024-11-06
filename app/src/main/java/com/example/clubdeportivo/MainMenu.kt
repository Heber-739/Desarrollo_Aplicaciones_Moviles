package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.clubdeportivo.Utils.Utils
import com.example.clubdeportivo.database.Database

class MainMenu : AppCompatActivity() {

    private lateinit var userName: String;
    private lateinit var userEmail: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Obtén los datos enviados desde la actividad anterior
        this.userName = intent.getStringExtra("USER_NAME").toString()
        this.userEmail = intent.getStringExtra("USER_EMAIL").toString()

        this.getUserInfo(this.userEmail)



        setContentView(R.layout.activity_main_menu)

        // Referencias a los TextView en los que mostrar el nombre y email
        val nameTextView = findViewById<TextView>(R.id.txt_user_name)
        val emailTextView = findViewById<TextView>(R.id.txt_user_email)

        // Asigna los valores a los TextView
        nameTextView.text = this.userName ?: "Nombre no disponible"
        emailTextView.text = this.userEmail ?: "Email no disponible"


        val buttonUsu = findViewById<Button>(R.id.btn_register_user)
        val buttonSalir = findViewById<Button>(R.id.btn_exit)
        val buttonReCliente = findViewById<Button>(R.id.btn_customers_report)
        val btnCarnet = findViewById<Button>(R.id.btn_emmit_card)
        buttonUsu.setOnClickListener {
            Utils.cambioPantalla(this,MainCliente::class.java)
        }
        buttonSalir.setOnClickListener{
            Utils.cambioPantalla(this,MainActivity::class.java)
        }
        buttonReCliente.setOnClickListener{
            Utils.cambioPantalla(this,CustomersRegister::class.java)
        }
        btnCarnet.setOnClickListener{
            Utils.cambioPantalla(this, CustomerCard::class.java)
        }
    }

    private fun getUserInfo(email:String){
        // Aquí deberías añadir la lógica para verificar el usuario en la base de datos
        val dbHelper = Database(this)
        val db = dbHelper.readableDatabase

        val query = "SELECT * FROM ${Database.TABLE_USERS} WHERE email_usuario = '$email'"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            val nro_avatar = cursor.getInt(cursor.getColumnIndexOrThrow("nro_avatar"))

            if(nro_avatar==1){
            val addIcon = findViewById<ImageView>(R.id.addIcon)
                addIcon.isVisible = true
                addIcon.setOnClickListener{

                }

            }
            val profileCont = findViewById<FrameLayout>(R.id.profileCont)


        } else {
            val modal = ModalFragment.newInstance("Error!!", "Usuario no encontrado", "OK")
            modal.show(supportFragmentManager, "ModalFragment")
        }
        cursor.close()
    }



}