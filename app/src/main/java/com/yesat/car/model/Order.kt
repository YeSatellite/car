package com.yesat.car.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Order : Serializable {

    @Expose var id: Long? = null

    @Expose var owner: User? = null

    @Expose var title: String? = null

    @Expose var comment: String? = null

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

    @SerializedName("start_detail")
    @Expose var startDetail: String? = null

    @SerializedName("end_detail")
    @Expose var endDetail: String? = null

    @Expose var volume: Float? = null

    @Expose var mass: Float? = null

    @Expose var image1: String? = null

    @Expose var image2: String? = null

    @SerializedName("owner_type")
    @Expose var ownerType: Long? = null
    @SerializedName("owner_type_name")
    @Expose var ownerTypeName: String? = null

    @SerializedName("payment_type")
    @Expose var paymentType: Long? = null
    @SerializedName("payment_type_name")
    @Expose var paymentTypeName: String? = null

    @Expose var category: Long? = null
    @SerializedName("category_name")
    @Expose var categoryName: String? = null

    @SerializedName("accept_person")
    @Expose var acceptPerson: String? = null

    @SerializedName("accept_person_contact")
    @Expose var acceptPersonContact: String? = null

    @SerializedName("shipping_date")
    @Expose var shippingDate: String? = null

    @SerializedName("shipping_time")
    @Expose var shippingTime: String? = null

    @Expose var offer: Offer? = null


}
