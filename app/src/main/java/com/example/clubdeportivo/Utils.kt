package com.example.clubdeportivo

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity


object Utils {

    fun cambioPantalla(context: Context, actividad: Class<*>) {
        val intent = Intent(context, actividad)
        context.startActivity(intent)
    }
}