package com.example.clubdeportivo.Utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivo.R

class AvatarAdapter(private val listener: OnAvatarClickListener) : RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>()  {

    interface OnAvatarClickListener {
        fun onAvatarClick(id: Int)
    }

    @SuppressLint("DiscouragedApi")
    class AvatarViewHolder(itemView: View, private val listener: OnAvatarClickListener) : RecyclerView.ViewHolder(itemView) {
        val profilePhoto: ImageView = itemView.findViewById<ImageView>(R.id.profilePhoto)


        fun bind(avatar: Int) {
            val nombreImagen = "avatar_" + avatar.toString()
            val resourceId = itemView.context.resources.getIdentifier(nombreImagen, "drawable", itemView.context.packageName)
            profilePhoto.setImageResource(if (resourceId != 0) resourceId else R.drawable.avatar_0)

            itemView.setOnClickListener {
                listener.onAvatarClick(avatar)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.avatar_item, parent, false)
        return AvatarViewHolder(view,listener)
    }


    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        holder.bind(position +1)
    }

    override fun getItemCount(): Int = 25


}