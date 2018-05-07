package com.yesat.car.utility

import com.yesat.car.model.Location
import com.yesat.car.model.User
import com.yesat.car.ui.client.XMainActivity
import com.yesat.car.ui.courier.YMainActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun locationFormat(location: Location, detail: String? = null): String{
    var str = "${location.countryName}, ${location.regionName}, ${location.name}"
    if (!detail.isNullOrBlank()) str += ", $detail"
    return str
}

fun definePosition(start: Location, end: Location):String {
    if (start.countryName != end.countryName) return "kasha"
    if (start.regionName != end.regionName) return "Same Country"
    if (start.name != end.name) return "Same Region"
    return "Same City"
}

fun clientOrCourier() : Class<*>? = when (Shared.currentUser.type){
    User.CLIENT -> XMainActivity::class.java
    User.COURIER -> YMainActivity::class.java
    else -> {
        Shared.currentUser = User()
        null
    }
}

fun dateFormat(): String {
    val cal = Calendar.getInstance()
    val monthDate = SimpleDateFormat("MMMM",Locale.getDefault())
    return monthDate.format(cal.time)
}

fun asdf(path: String,field: String): MultipartBody.Part{
    val file = File(path)
    val body = RequestBody.create(MediaType.parse("image/*"), file)
    return MultipartBody.Part.createFormData(field, file.name, body)
}
