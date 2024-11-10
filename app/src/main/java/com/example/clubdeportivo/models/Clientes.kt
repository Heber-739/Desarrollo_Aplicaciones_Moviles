package com.example.clubdeportivo.models

data class Cliente(
    val dni: Int,
    val nombre: String,
    val email: String,
    val fechaNac: String,
    val aptoFisico: Boolean,
    val tipoCliente: String,
    val fechaVencPago: String,
    val nroAvatar: Int
)
