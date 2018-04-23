package com.yesat.car.utility

import com.yesat.car.model.Location

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