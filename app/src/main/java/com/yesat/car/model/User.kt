package com.yesat.car.model

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import java.io.Serializable

abstract class User : Serializable {

    @Expose var id: Long? = null
    @Expose var phone: String? = null
    @Expose var name: String? = null
    @Expose var citizenship: String? = null
    @Expose var dob: String? = null
    @Expose var type: String? = null
    @Expose var avatar: String? = null
    @Expose var experience: String? = null
    @Expose var rating: String? = null
    @Expose var token: String? = null

    fun toJson(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

    override fun toString(): String {
        return """
            |User1(id=$id,
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

        fun fromJson(json: String): User1 {
            return Gson().fromJson(json, User1::class.java)
        }
    }

}

class User1 : User() {
    @Expose var city: Location? = null
}

class User2 : User() {
    @Expose var city: Long? = null
}
