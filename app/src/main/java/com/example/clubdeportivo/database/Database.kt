package com.example.clubdeportivo.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {


    companion object {

        private const val DATABASE_NAME = "ClubDeportivo.db"

        // Tabla de Usuarios
        const val TABLE_USERS = "Usuarios"
        const val COLUMN_USERS_ID = "id_usuario"
        const val COLUMN_USERS_EMAIL = "email_usuario"
        const val COLUMN_USERS_NAME = "nombre_usuario"
        const val COLUMN_USERS_PASS = "password_usuario"
        const val COLUMN_USERS_ROL = "rol_id"

        // Tabla de Clientes
        private const val TABLE_CLIENTES = "Clientes"
        private const val COLUMN_CLIENTE_DNI = "dni"
        private const val COLUMN_NOMBRE_CLIENTE = "nombre_cliente"
        private const val COLUMN_EMAIL_CLIENTE = "email_cliente"
        private const val COLUMN_FECHA_NAC = "fecha_nac"
        private const val COLUMN_APTO_FISICO = "apto_fisico"
        private const val COLUMN_TIPO_CLIENTE = "tipo_cliente"
        private const val COLUMN_FECHA_VENC_PAGO = "fecha_venc_pago"
        private const val COLUMN_NRO_AVATAR = "nro_avatar"

        // Tabla de Pagos
        const val TABLE_PAGO = "Pagos"
        private const val COLUMN_PAGO_ID = "id"
        const val COLUMN_CLIENTE_DNI_FK = "cliente_dni"
        const val COLUMN_MONTO_PAGO = "monto_pago"
        const val COLUMN_VENC_PAGO = "venc_pago"
        const val COLUMN_FECHA_PAGO = "fecha_pago"
        const val COLUMN_METODO_PAGO = "metodo_pago"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        // Creación de tabla de Usuarios
        val createTableUsuarios = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USERS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERS_EMAIL TEXT,
                $COLUMN_USERS_NAME TEXT,
                $COLUMN_USERS_PASS TEXT,
                $COLUMN_USERS_ROL INTEGER
            );
        """
        db?.execSQL(createTableUsuarios)

        // Creación de tabla de Clientes
        val createTableClientes = """
            CREATE TABLE $TABLE_CLIENTES (
                $COLUMN_CLIENTE_DNI INTEGER PRIMARY KEY,
                $COLUMN_NOMBRE_CLIENTE TEXT NOT NULL,
                $COLUMN_EMAIL_CLIENTE TEXT NOT NULL,
                $COLUMN_FECHA_NAC DATE DEFAULT CURRENT_DATE,
                $COLUMN_APTO_FISICO BOOLEAN DEFAULT 1,
                $COLUMN_TIPO_CLIENTE TEXT NOT NULL,
                $COLUMN_FECHA_VENC_PAGO DATE DEFAULT CURRENT_DATE,
                $COLUMN_NRO_AVATAR INTEGER DEFAULT 0
            );
        """
        db?.execSQL(createTableClientes)

        // Creación de tabla de Pagos
        val createTablePago = """
            CREATE TABLE $TABLE_PAGO (
                $COLUMN_PAGO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CLIENTE_DNI_FK INTEGER,
                $COLUMN_MONTO_PAGO REAL,
                $COLUMN_VENC_PAGO DATE DEFAULT CURRENT_DATE,
                $COLUMN_FECHA_PAGO DATE DEFAULT CURRENT_DATE,
                $COLUMN_METODO_PAGO TEXT NOT NULL,
                FOREIGN KEY ($COLUMN_CLIENTE_DNI_FK) REFERENCES $TABLE_CLIENTES($COLUMN_CLIENTE_DNI)
            );
        """
        db?.execSQL(createTablePago)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PAGO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }
}