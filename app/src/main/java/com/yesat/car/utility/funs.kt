package com.yesat.car.utility

import com.yesat.car.model.Location
import com.yesat.car.model.User
import com.yesat.car.model.User1
import com.yesat.car.ui.client.XMainActivity
import com.yesat.car.ui.courier.YMainActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


fun locationFormat(location: Location, detail: String? = null): String{
    var str = "${location.region}, ${location.name}"
    if (!detail.isNullOrBlank()) str += ", $detail"
    return str
}

fun definePosition(start: Location, end: Location):String {
    if (start.region != end.region) return "Same Country"
    if (start.name == end.name) return "Same Region"
    return "Same City"
}

fun clientOrCourier() : Class<*>? = when (Shared.currentUser.type){
    User.CLIENT -> XMainActivity::class.java
    User.COURIER -> YMainActivity::class.java
    else -> {
        Shared.currentUser = User1()
        null
    }
}

fun asdf(path: String,field: String): MultipartBody.Part{
    val file = File(path)
    val body = RequestBody.create(MediaType.parse("image/*"), file)
    return MultipartBody.Part.createFormData(field, file.name, body)
}
