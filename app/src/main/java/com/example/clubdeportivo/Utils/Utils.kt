package com.example.clubdeportivo.Utils

import android.content.Context
import android.content.Intent


object Utils {

    fun cambioPantalla(context: Context, actividad: Class<*>) {
        val intent = Intent(context, actividad)
        context.startActivity(intent)
    }
}