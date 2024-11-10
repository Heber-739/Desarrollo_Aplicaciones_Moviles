package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.clubdeportivo.Utils.User
import com.example.clubdeportivo.Utils.Utils
import com.example.clubdeportivo.database.Database

class MainMenu : AppCompatActivity(), ModalFragment.ModalListener {

    private lateinit var userName: String;
    private lateinit var userEmail: String;
    private var nroAvatar: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main_menu)

        this.getUserInfo()

        val buttonUsu = findViewById<Button>(R.id.btn_register_user)
        val buttonPago = findViewById<Button>(R.id.btn_payment)
        val buttonSalir = findViewById<Button>(R.id.btn_exit)

        val buttonReCliente = findViewById<Button>(R.id.btn_all_customers)
        val buttonOverdue = findViewById<Button>(R.id.btn_overdue)
        val buttonToOverdue = findViewById<Button>(R.id.btn_to_overdue)
        val buttonOverdueToday = findViewById<Button>(R.id.btn_overdue_today)



        val btnCarnet = findViewById<Button>(R.id.btn_emmit_card)

        buttonUsu.setOnClickListener {
            Utils.cambioPantalla(this,MainCliente::class.java)
        }
        buttonPago.setOnClickListener {
            val intent = Intent(this, BuscarCliente::class.java)
            intent.putExtra("PROCESO", "pago")
            startActivity(intent)

        }
        buttonSalir.setOnClickListener{
            Utils.cambioPantalla(this,MainActivity::class.java)
        }
        buttonReCliente.setOnClickListener{
            val intent = Intent(this, CustomersRegister::class.java)
            intent.putExtra("QUERY", "ALL")
            startActivity(intent)
        }
        buttonToOverdue.setOnClickListener{
            val intent = Intent(this, CustomersRegister::class.java)
            intent.putExtra("QUERY", "TOOVERDUE")
            startActivity(intent)
        }
        buttonOverdue.setOnClickListener{
            val intent = Intent(this, CustomersRegister::class.java)
            intent.putExtra("QUERY", "OVERDUE")
            startActivity(intent)
        }
        buttonOverdueToday.setOnClickListener{
            val intent = Intent(this, CustomersRegister::class.java)
            intent.putExtra("QUERY", "OVERDUETODAY")
            startActivity(intent)
        }
        btnCarnet.setOnClickListener{
            val intent = Intent(this, BuscarCliente::class.java)
            intent.putExtra("PROCESO", "carnet")
            startActivity(intent)

        }
    }

    override fun onResume() {
        super.onResume()
        this.getUserInfo()
        this.showAvatar()
    }

    @SuppressLint("DiscouragedApi")
    private fun showAvatar(){
        this.nroAvatar = User.avatar

        val addIcon = findViewById<ImageView>(R.id.addIcon)
        val profileImage = findViewById<ImageView>(R.id.profilePhoto)

        val drawableId = resources.getIdentifier("avatar_${this.nroAvatar}", "drawable", packageName)
        profileImage.setImageResource(drawableId)

        if(this.nroAvatar ==0 ){
            addIcon.isVisible = true
            addIcon.setOnClickListener{
                val intent = Intent(this, AvatarSelect::class.java)
                intent.putExtra("USER_EMAIL", this.userEmail)
                intent.putExtra("TABLE", Database.TABLE_USERS)
                startActivity(intent)
            }
        } else {
            addIcon.isVisible = false
        }
    }


    private fun getUserInfo(){

            this.userName = User.name
            this.userEmail = User.email


        val nameTextView = findViewById<TextView>(R.id.txt_user_name)
        val emailTextView = findViewById<TextView>(R.id.txt_user_email)

        nameTextView.text = this.userName
        emailTextView.text = this.userEmail

    }

    override fun onModalResult(success: Boolean) {
        TODO("Not yet implemented")
    }


}