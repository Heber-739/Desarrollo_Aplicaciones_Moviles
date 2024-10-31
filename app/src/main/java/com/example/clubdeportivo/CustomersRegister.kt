package com.example.clubdeportivo

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.Utils.ClienteAdapter
import com.example.clubdeportivo.Utils.Clientes_mostrar
import com.example.clubdeportivo.Utils.Utils
import java.util.Date

class CustomersRegister : AppCompatActivity(), ClienteAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customers_register)
        val btnHome= findViewById<Button>(R.id.btn_nav_home)
        val btnRegis = findViewById<Button>(R.id.btn_nav_register)
        val btnCuot = findViewById<Button>(R.id.btn_nav_payment)
        val btnCarnet = findViewById<Button>(R.id.btn_nav_card)
        val btnReport = findViewById<Button>(R.id.btn_nav_reports)

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

        /* Listar los clientes */
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lista de clientes a mostrar
        val cliente1 = Clientes_mostrar(3423 ,"heber duarte", 1 , Date())
        val cliente2 = Clientes_mostrar(3421 ,"Cristian Orihuela", 5 , Date())
        val cliente3 = Clientes_mostrar(3420 ,"Mica orellano", 15 , Date())

        val clientes = listOf(cliente1, cliente2, cliente3)

        // Configura el adapter para el RecyclerView
        val adapter = ClienteAdapter(clientes,this)
        recyclerView.adapter = adapter


        val btnRegresar = findViewById<Button>(R.id.btn_back)
        btnRegresar.setOnClickListener{
            Utils.cambioPantalla(this, MainMenu::class.java)
        }


    }

    override fun onItemClick(id: Int) {
        // Usar el ID del elemento clicado
        Toast.makeText(this, "ID del elemento clicado: $id", Toast.LENGTH_SHORT).show()

        // Aquí puedes realizar cualquier operación con el ID
        // Ejemplo: buscar en la base de datos usando el ID, etc.
    }
}