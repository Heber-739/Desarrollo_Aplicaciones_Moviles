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
import com.example.clubdeportivo.database.Database
import com.example.clubdeportivo.models.Cliente
import java.text.SimpleDateFormat
import java.util.Date

class CustomersRegister : AppCompatActivity(), ClienteAdapter.OnItemClickListener, ModalFragment.ModalListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customers_register)
        val param = intent.getStringExtra("QUERY").toString()
        this.mostrarClientes(param);



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

        val btnRegresar = findViewById<Button>(R.id.btn_back)
        btnRegresar.setOnClickListener{
            Utils.cambioPantalla(this, MainMenu::class.java)
        }


    }

    private fun mostrarClientes(tipo: String){
        val today = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val dbHelper = Database(this)
        val db = dbHelper.readableDatabase
        val clientes = mutableListOf<Clientes_mostrar>()

        val query = when (tipo) {
            "ALL" -> "SELECT * FROM ${Database.TABLE_CLIENTES}"
            "OVERDUE" -> "SELECT * FROM ${Database.TABLE_CLIENTES} WHERE fecha_venc_pago < ?"
            "TOOVERDUE" -> "SELECT * FROM ${Database.TABLE_CLIENTES} WHERE fecha_venc_pago > ?"
            "OVERDUETODAY" -> "SELECT * FROM ${Database.TABLE_CLIENTES} WHERE fecha_venc_pago = ?"
            else -> "SELECT * FROM ${Database.TABLE_CLIENTES}"
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        try {


        val cursor = if (tipo == "ALL") {
            db.rawQuery(query, null)
        } else {
            db.rawQuery(query, arrayOf(today))
        }

        if (cursor.moveToFirst()) {
            do {
                val dni = cursor.getInt(cursor.getColumnIndexOrThrow(Database.COLUMN_CLIENTE_DNI))
                val nombre =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_NOMBRE_CLIENTE))
                val fechaVencPago =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_FECHA_VENC_PAGO))
                val nroAvatar =
                    cursor.getInt(cursor.getColumnIndexOrThrow(Database.COLUMN_NRO_AVATAR))

                val cliente = Clientes_mostrar(
                    dni,
                    nombre,
                    nroAvatar,
                    fechaVencPago
                )
                clientes.add(cliente)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        // Configura el adapter para el RecyclerView
        val adapter = ClienteAdapter(clientes,this)
        recyclerView.adapter = adapter

        } catch (e:Exception){
            val modal = ModalFragment.newInstance("No se puede visualizar elementos",
                "Error:  ${e.message}", "OK", )
            modal.show(supportFragmentManager, "ModalFragment")
        }

    }

    override fun onItemClick(id: Int) {
        // Usar el ID del elemento clicado
        Toast.makeText(this, "ID del elemento clicado: $id", Toast.LENGTH_SHORT).show()

        // Aquí puedes realizar cualquier operación con el ID
        // Ejemplo: buscar en la base de datos usando el ID, etc.
    }

    override fun onModalResult(success: Boolean) {
        TODO("Not yet implemented")
    }
}