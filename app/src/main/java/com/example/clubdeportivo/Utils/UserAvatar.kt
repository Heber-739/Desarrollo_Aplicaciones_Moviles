package com.example.clubdeportivo.Utils

import android.content.Context
import android.content.Intent


object UserAvatar {
    private var avatar:Int = 0
    private var changeAvatar:Boolean = false

    fun getAvatar(): Int {
        return this.avatar
    }
    fun setAvatar(id: Int) {
        this.changeAvatar = true
        this.avatar = id
    }
    fun changeAvatar(): Boolean {
        if(this.changeAvatar){
            this.changeAvatar = false
            return true
        }
        return this.changeAvatar
    }
}