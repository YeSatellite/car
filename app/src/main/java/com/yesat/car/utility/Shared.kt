package com.yesat.car.utility

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.yesat.car.model.User

object Shared {
    private const val tagPrefix = "xxx"

    object Tag {
        const val norm = "$tagPrefix<->norm"
        const val hana = "$tagPrefix<->hana"
        const val info = "$tagPrefix<+>info"
    }

    const val user = "user"
    const val order = "order"
    const val offer = "order"
    const val transport = "transport"
    const val city = "city"
    const val paymentType = "paymentType"
    const val category = "category"

    const val posted = "posted"
    const val active = "active"

    var preferences: SharedPreferences? = null
        private set
    var currentUser = User()
        set(value) {
            field = value
            val editor = preferences!!.edit()
            editor.putString(user, field.toJson())
            editor.apply()
        }

    fun init(preferences: SharedPreferences) {
        Shared.preferences = preferences
        currentUser = User.fromJson(preferences.getString(user, "{}"))
    }

    fun Any.norm(text: String?) {
        Log.d(Shared.Tag.norm,text)
    }
}

