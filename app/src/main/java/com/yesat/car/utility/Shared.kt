package com.yesat.car.utility

import android.content.SharedPreferences
import android.util.Log
import com.yesat.car.model.InfoTmp
import com.yesat.car.model.User
import com.yesat.car.model.User1
import retrofit2.Call

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
    const val infoTmp = "infoTmp"
    const val category = "category"

    const val posted = "posted"
    const val waiting = "waiting"
    const val active = "active"

    var call: Call<List<InfoTmp>>? = null

    var preferences: SharedPreferences? = null
        private set
    var currentUser = User1()
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
        Log.d(Shared.Tag.norm,text ?: "null")
    }

    fun Any.hana(text: String?, t: Throwable? = null) {
        Log.e(Shared.Tag.norm,text,t)
    }
}

