package com.yesat.car.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Route : Serializable {

    @Expose var id: Long? = null

    @Expose var owner: User? = null

    @Expose var transport: Transport? = null
        set(value) {
            field = value
            transportId = value!!.id
        }
    @SerializedName("transport_id")
    @Expose var transportId: Long? = null

    @SerializedName("start_point")
    @Expose var startPoint: Location? = null
        set(value) {
            field = value
            startPointId = value!!.id
        }
    @SerializedName("start_point_id")
    @Expose var startPointId: Long? = null

    @SerializedName("end_point")
    @Expose var endPoint: Location? = null
        set(value) {
            field = value
            endPointId = value!!.id
        }
    @SerializedName("end_point_id")
    @Expose var endPointId: Long? = null

    @SerializedName("shipping_date")
    @Expose var shippingDate: String? = null

    @SerializedName("shipping_time")
    @Expose var shippingTime: String? = null


    class FilterRoute(
            var type: Long? = null,
            var startPoint: Long? = null,
            var endPoint: Long? = null,
            var startDate: String? = null,
            var endDate: String? = null
    ) : Serializable
}
