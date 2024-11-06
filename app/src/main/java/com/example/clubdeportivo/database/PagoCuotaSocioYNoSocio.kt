package com.example.clubdeportivo.database

import android.content.ContentValues
import android.content.Context
import com.example.clubdeportivo.database.Database.Companion.COLUMN_CLIENTE_DNI_FK
import com.example.clubdeportivo.database.Database.Companion.COLUMN_FECHA_PAGO
import com.example.clubdeportivo.database.Database.Companion.COLUMN_METODO_PAGO
import com.example.clubdeportivo.database.Database.Companion.COLUMN_MONTO_PAGO
import com.example.clubdeportivo.database.Database.Companion.COLUMN_VENC_PAGO
import com.example.clubdeportivo.database.Database.Companion.TABLE_PAGO

class PagoCuotaSocioYNoSocio (context: Context) {
    private val dbHelper = Database(context)

    // Método para registrar un pago
    fun registrarPago(dniCliente: Int, monto: Double, metodoPago: String, fechaPago: Long, vencimiento: Long): String {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CLIENTE_DNI_FK, dniCliente)
            put(COLUMN_MONTO_PAGO, monto)
            put(COLUMN_METODO_PAGO, metodoPago)
            put(COLUMN_FECHA_PAGO, fechaPago)
            put(COLUMN_VENC_PAGO, vencimiento)
        }

        val result = db.insert(TABLE_PAGO, null, values)
        if (result == -1.toLong()) {
            return "Falla en el registro del pago"
        } else {
            return "Registro de pago exitoso"
        }

    }


}