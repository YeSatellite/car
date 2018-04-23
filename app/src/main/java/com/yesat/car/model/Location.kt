package com.yesat.car.model

import com.google.gson.annotations.Expose

import java.io.Serializable

class Location : Serializable {
    @Expose var id: Long? = null
    @Expose var name: String? = null
    @Expose var region: Long? = null

    constructor()
    constructor(id: Long, name: String?, region: Long?) {
        this.id = id
        this.name = name
        this.region = region
    }
}
