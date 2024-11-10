package com.example.clubdeportivo

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class CustomerCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customer_card)

        val btnEmitir = findViewById<TextView>(R.id.btn_emitir_carnet)

        val tvVencimiento = findViewById<TextView>(R.id.txt_vencimiento)
        val tvTitulo = findViewById<TextView>(R.id.txt_titulo)
        val tvNombre = findViewById<TextView>(R.id.txt_nombre)
        val imageViewQR = findViewById<ImageView>(R.id.image_qr)


        // Obtenemos los datos enviados desde la actividad anterior
        val nombreCompleto = intent.getStringExtra("nombreCompleto")
        val esSocio = intent.getStringExtra("tipoCliente")
        val fechaVencimiento = intent.getStringExtra("vencimiento")


        // Generamos  el QR
        val qrText = "Nombre: $nombreCompleto\nVencimiento: $fechaVencimiento"
        val qrBitmap = generateQRCode(qrText)
        imageViewQR.setImageBitmap(qrBitmap)

        // Rellenamos los campos de texto con los datos recibidos
        tvNombre.text = nombreCompleto ?: "Nombre y apellido"
        if (esSocio == "Socio") {
            tvTitulo.text = "CARNET DE SOCIO"
        } else tvTitulo.text = "CARNET DE NO SOCIO"

        tvVencimiento.text = fechaVencimiento ?: "Fecha no disponible"
    }

    private fun generateQRCode(text: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        return try {
            val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(
                        x,
                        y,
                        if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
                    )
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}

