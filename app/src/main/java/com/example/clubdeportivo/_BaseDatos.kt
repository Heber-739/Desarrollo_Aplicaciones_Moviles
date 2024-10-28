package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object {
        // Nombre de la base de datos y versión
        private val DATABASE_NAME = "_ClubDeportivo.db"
        private val DATABASE_VERSION = 1

        // Nombre de la tabla y columnas
        private val TABLE_USERS = "Usuarios"
        private val COLUMN_USERS_ID = "id_usuario"
        private val COLUMN_USERS_EMAIL = "email_usuario"
        private val COLUMN_USERS_NAME = "nombre_usuario"
        private val COLUMN_USERS_PASS = "password_usuario"
        private val COLUMN_USERS_ROL = "rol_id"

        // Sentencia SQL para crear la tabla

        //        private val CREATE_TABLE_USERS = ("CREATE TABLE " + TABLE_USERS + " (" + COLUMN_USERS_ID +
        //        " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERS_EMAIL + " TEXT, " + COLUMN_USERS_NAME +
        //        " TEXT, " +  COLUMN_USERS_PASS + " TEXT " + COLUMN_USERS_ROL + " INTEGER);")

        // Sentencia SQL para crear la tabla
        private  val CREATE_TABLE_USERS = ("""
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USERS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERS_EMAIL TEXT,
                $COLUMN_USERS_NAME TEXT,
                $COLUMN_USERS_PASS TEXT,
                $COLUMN_USERS_ROL INTEGER
            );
        """)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USERS) // Crear la tabla
    }

    override fun onUpgrade(db: SQLiteDatabase?, OldVersion: Int, NewVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS) // Borrar tabla si existe
        onCreate(db) // Crear tabla nuevamente
    }

    // Método para insertar un usuario en la base de datos
    fun insertarUsuario(nombre: String, usuario: String, pass: String, rol: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERS_NAME, nombre)
            put(COLUMN_USERS_EMAIL, usuario)
            put(COLUMN_USERS_PASS, pass)
            put(COLUMN_USERS_ROL, rol)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result // Retorna el ID del nuevo registro
    }

    // Método para obtener todos los usuarios

        //    fun obtenerUsuarios(): Cursor {
        //        val db = readableDatabase
        //        return db.query(TABLE_USERS, null, null, null, null, null, null)
        //    }


}