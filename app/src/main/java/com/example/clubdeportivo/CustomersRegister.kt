package com.example.clubdeportivo

import android.content.Intent
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
import java.util.TimeZone

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
        val timeZone = TimeZone.getTimeZone("America/Argentina/Buenos_Aires")
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        dateFormat.timeZone = timeZone
        val today = dateFormat.format(Date())

        val dbHelper = Database(this)
        val db = dbHelper.readableDatabase
        val clientes = mutableListOf<Clientes_mostrar>()

        val all = "SELECT c.nombre_cliente, c.email_cliente, p.venc_pago, c.nro_avatar FROM " +
                "${Database.TABLE_CLIENTES} AS C INNER JOIN ${Database.TABLE_PAGO} AS P ON C.dni = P.cliente_dni " +
                "WHERE p.venc_pago = ( SELECT MAX(P2.venc_pago) FROM ${Database.TABLE_PAGO} AS P2 " +
                "WHERE P2.cliente_dni = C.dni );"

        val queryOverdue =
            "SELECT c.nombre_cliente, c.email_cliente, p.venc_pago, c.nro_avatar FROM " +
                    "${Database.TABLE_CLIENTES} AS C INNER JOIN ${Database.TABLE_PAGO} AS P ON C.dni = P.cliente_dni " +
                    "WHERE p.venc_pago = ( SELECT MAX(P2.venc_pago) FROM ${Database.TABLE_PAGO} AS P2 " +
                    "WHERE P2.cliente_dni = C.dni ) AND P.venc_pago < ?;"

        val queryTooverdue =
            "SELECT c.nombre_cliente, c.email_cliente, p.venc_pago, c.nro_avatar FROM " +
                    "${Database.TABLE_CLIENTES} AS C INNER JOIN ${Database.TABLE_PAGO} AS P ON C.dni = P.cliente_dni " +
                    "WHERE p.venc_pago = ( SELECT MAX(P2.venc_pago) FROM ${Database.TABLE_PAGO} AS P2 " +
                    "WHERE P2.cliente_dni = C.dni ) AND P.venc_pago > ?;"

        val queryOverdueToday =
            "SELECT c.nombre_cliente, c.email_cliente, p.venc_pago, c.nro_avatar FROM " +
                    "${Database.TABLE_CLIENTES} AS C INNER JOIN ${Database.TABLE_PAGO} AS P ON C.dni = P.cliente_dni " +
                    "WHERE p.venc_pago = ( SELECT MAX(P2.venc_pago) FROM ${Database.TABLE_PAGO} AS P2 " +
                    "WHERE P2.cliente_dni = C.dni ) AND p.venc_pago = ?;"

        val query = when (tipo) {
            "ALL" -> all
            "OVERDUE" -> queryOverdue
            "TOOVERDUE" -> queryTooverdue
            "OVERDUETODAY" -> queryOverdueToday



            else -> all

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
                val nombre =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_NOMBRE_CLIENTE))
                val email =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_EMAIL_CLIENTE))
                val fechaVencPago =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_VENC_PAGO))
                val nroAvatar =
                    cursor.getInt(cursor.getColumnIndexOrThrow(Database.COLUMN_NRO_AVATAR))

                val cliente = Clientes_mostrar(
                    email,
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

    override fun onItemClick(email: String) {
        // Usar el ID del elemento clicado
        Toast.makeText(this, "ID del elemento clicado: $email", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, AvatarSelect::class.java)
        intent.putExtra("USER_EMAIL", email)
        intent.putExtra("TABLE", Database.TABLE_CLIENTES)
        intent.putExtra("COLUMN", "cliente")
        startActivity(intent)


        // Aquí puedes realizar cualquier operación con el ID
        // Ejemplo: buscar en la base de datos usando el ID, etc.
    }

    override fun onModalResult(success: Boolean) {
        TODO("Not yet implemented")
    }
}