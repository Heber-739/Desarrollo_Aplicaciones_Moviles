package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.Utils.BuscarClienteAdapter
import com.example.clubdeportivo.models.Cliente
import com.example.clubdeportivo.database.Database

class BuscarCliente : AppCompatActivity() {

    private lateinit var clienteAdapter: BuscarClienteAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_cliente)

        // Obtén los datos enviados desde la actividad anterior
        val proceso = intent.getStringExtra("PROCESO")

        clienteAdapter = BuscarClienteAdapter(emptyList()) { cliente ->

            val dniCliente = cliente.dni.toString()
            val nombreCliente = cliente.nombre
            val tipoCliente = cliente.tipoCliente // Socio o No socio



            // Declaramos el Intent antes del if-else para poder acceder fuera del bloque condicional
            val intent: Intent

            if (proceso == "pago") {
                // Asignamos el Intent según el tipo de cliente
                if (tipoCliente == "Socio") {
                    intent = Intent(this, MainPagoCuotaSocio::class.java)
                } else {
                    intent = Intent(this, MainPagoCuotaNoSocio::class.java)
                }
            } else {
                intent = Intent(this, CustomerCard::class.java)
            }
            // Agregamos los extras y lanzamos la actividad
            intent.putExtra("dni", dniCliente)
            intent.putExtra("nombreCompleto", nombreCliente)
            intent.putExtra("tipoCliente", tipoCliente)
            startActivity(intent)
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_clientes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = clienteAdapter

        // Cargar y mostrar la lista de clientes
        val clientes =
            loadClientesFromDatabase() // Obtener la lista de clientes desde la base de datos
        clienteAdapter.setClientes(clientes)

        // Configurar la barra de búsqueda
        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtrar la lista de clientes en tiempo real
                val clientesFiltrados = if (newText.isNullOrEmpty()) {
                    clientes
                } else {
                    clientes.filter {
                        it.nombre.contains(newText, ignoreCase = true) ||
                                it.email.contains(newText, ignoreCase = true)
                    }
                }
                clienteAdapter.setClientes(clientesFiltrados)
                return true
            }
        })
    }

    private fun loadClientesFromDatabase(): List<Cliente> {
        val dbHelper = Database(this)
        val db = dbHelper.readableDatabase
        val clientes = mutableListOf<Cliente>()

        // Consulta a la base de datos para obtener la lista de clientes
        val query = "SELECT * FROM ${Database.TABLE_CLIENTES}"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val dni = cursor.getInt(cursor.getColumnIndexOrThrow(Database.COLUMN_CLIENTE_DNI))
                val nombre =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_NOMBRE_CLIENTE))
                val email =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_EMAIL_CLIENTE))
                val fechaNac =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_FECHA_NAC))
                val aptoFisico =
                    cursor.getInt(cursor.getColumnIndexOrThrow(Database.COLUMN_APTO_FISICO)) == 1
                val tipoCliente =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_TIPO_CLIENTE))
                val fechaVencPago =
                    cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_FECHA_VENC_PAGO))
                val nroAvatar =
                    cursor.getInt(cursor.getColumnIndexOrThrow(Database.COLUMN_NRO_AVATAR))

                // Crear el objeto Cliente y agregarlo a la lista
                val cliente = Cliente(
                    dni,
                    nombre,
                    email,
                    fechaNac,
                    aptoFisico,
                    tipoCliente,
                    fechaVencPago,
                    nroAvatar
                )
                clientes.add(cliente)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return clientes
    }
}
