package com.yesat.car.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Location : Serializable {
    @Expose var id: Long? = null

    @Expose var name: String? = null

    @SerializedName("region_name")
    @Expose var regionName: String? = null

    @SerializedName("country_name")
    @Expose var countryName: String? = null
}
