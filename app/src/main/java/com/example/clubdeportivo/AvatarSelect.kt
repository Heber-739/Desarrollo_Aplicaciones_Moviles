package com.example.clubdeportivo

import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.Utils.AvatarAdapter
import com.example.clubdeportivo.Utils.UserAvatar
import com.example.clubdeportivo.database.Database


class AvatarSelect: AppCompatActivity(), AvatarAdapter.OnAvatarClickListener, ModalFragment.ModalListener  {

    private var email:String = ""
    private var table:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.email = intent.getStringExtra("USER_EMAIL").toString()
        this.table = intent.getStringExtra("TABLE").toString()

        // Configura el layout de la actividad
        setContentView(R.layout.avatar_select)  // Reemplaza "activity_my" con el nombre de tu layout


        /* Listar los clientes */
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)


        val gridLayoutManager = GridLayoutManager(this, 3) // 3 es el número de columnas
        recyclerView.layoutManager = gridLayoutManager


        val adapter = AvatarAdapter(this)
        recyclerView.adapter = adapter

    }

    override fun onAvatarClick(id: Int) {
        val dbHelper = Database(this)
        val db = dbHelper.writableDatabase

        try {

    val updateQuery = "UPDATE ${this.table} SET nro_avatar = ? WHERE email_usuario = ?"
    val stmt = db.compileStatement(updateQuery)
    stmt.bindLong(1, id.toLong())
    stmt.bindString(2, this.email)
    val rowCount = stmt.executeUpdateDelete()
            stmt.close()

            val modal = ModalFragment.newInstance("No ",
                " ${this.table} - ${this.email} - ${id} - ${rowCount}", "OK", )
            modal.show(supportFragmentManager, "ModalFragment")

    UserAvatar.setAvatar(id)

//    onBackPressedDispatcher.onBackPressed()

}
         catch (e:Exception){
        val modal = ModalFragment.newInstance("No se pudo encontrar el avatar seleccionado nro",
            "Error:  ${e.message}", "OK", )
        modal.show(supportFragmentManager, "ModalFragment")
    }


    }

    override fun onModalResult(success: Boolean) {
        OnBackPressedDispatcher()
    }

}