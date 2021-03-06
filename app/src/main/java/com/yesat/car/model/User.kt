package com.yesat.car.model

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import java.io.Serializable

class User : Serializable {

    @Expose var id: Long? = null
    @Expose var phone: String? = null
    @Expose var name: String? = null
    @Expose var citizenship: String? = null
    @Expose var dob: String? = null
    @Expose var type: String? = null
    @Expose var city: Location? = null
    @Expose var avatar: String? = null
    @Expose var experience: Long? = null
    @Expose var rating: String? = null
    @Expose var token: String? = null

    fun toJson(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

    override fun toString(): String {
        return """
            |User(id=$id,
            |   phone=$phone,
            |   name=$name,
            |   citizenship=$citizenship,
            |   dob=$dob,
            |   type=$type,
            |   avatar=$avatar,
            |   experience=$experience,
            |   rating=$rating,
            |   token=$token
            |)""".trimMargin()
    }


    companion object {
        const val CLIENT = "client"
        const val COURIER = "courier"

        fun fromJson(json: String): User {
            return Gson().fromJson(json, User::class.java)
        }
    }

}