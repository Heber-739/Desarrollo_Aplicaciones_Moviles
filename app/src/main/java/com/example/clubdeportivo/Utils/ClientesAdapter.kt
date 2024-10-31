package com.example.clubdeportivo.Utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.R


class ClienteAdapter(private val clientes: List<Clientes_mostrar>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    @SuppressLint("DiscouragedApi")
    class ClienteViewHolder(itemView: View, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_names)
        val profilePhoto: ImageView = itemView.findViewById(R.id.profilePhoto)

        fun bind(cliente: Clientes_mostrar) {
            txtName.text = cliente.names
            val nombreImagen = "avatar_" + cliente.avatar.toString()
            val resourceId = itemView.context.resources.getIdentifier(nombreImagen, "drawable", itemView.context.packageName)
            profilePhoto.setImageResource(if (resourceId != 0) resourceId else R.drawable.avatar_0)

            // Asigna el listener al itemView y pasa el ID único del cliente
            itemView.setOnClickListener {
                listener.onItemClick(cliente.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.customer_item, parent, false)
        return ClienteViewHolder(view,listener)
    }


    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        holder.bind(clientes[position])
    }

    override fun getItemCount(): Int = clientes.size
}