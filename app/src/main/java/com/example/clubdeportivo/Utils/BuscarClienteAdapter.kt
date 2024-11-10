package com.example.clubdeportivo.Utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.R
import com.example.clubdeportivo.models.Cliente

class BuscarClienteAdapter(
    private var clientes: List<Cliente>,
    private val onClienteClick: (Cliente) -> Unit
) : RecyclerView.Adapter<BuscarClienteAdapter.ClienteViewHolder>() {

    fun setClientes(clientesFiltrados: List<Cliente>) {
        clientes = clientesFiltrados
        notifyDataSetChanged()
    }

    inner class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dniTextView: TextView = itemView.findViewById(R.id.text_dni_cliente)
        private val nombreTextView: TextView = itemView.findViewById(R.id.text_nombre_cliente)
        private val tipoClienteTextView: TextView = itemView.findViewById(R.id.text_tipo_cliente)
        private val vencimientoTextView: TextView = itemView.findViewById(R.id.text_vencimiento)

        fun bind(cliente: Cliente) {
            dniTextView.text = "DNI: ${cliente.dni}"
            nombreTextView.text = "Nombre: ${cliente.nombre}"
            tipoClienteTextView.text = "Tipo: ${cliente.tipoCliente}"
            vencimientoTextView.text = "Vto.Cuota: ${cliente.fechaVencPago}"
            itemView.setOnClickListener { onClienteClick(cliente) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        holder.bind(clientes[position])
    }

    override fun getItemCount(): Int = clientes.size
}
