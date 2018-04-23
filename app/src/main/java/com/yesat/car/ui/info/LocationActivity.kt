package com.yesat.car.ui.info

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.utility.Api
import com.yesat.car.utility.Shared.norm

class LocationActivity : AppCompatActivity() {

    var state = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val selectedFragment = LocationFragment(Api.infoService.country())
        RESULT_OK
        next(0)
    }

    fun next(id:Long){
        norm(state.toString())
        val selectedFragment = when(state){
            0 -> LocationFragment(Api.infoService.country())
            1 -> LocationFragment(Api.infoService.region(id))
            2 -> CityFragment(Api.infoService.city(id))
            else -> return
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_container, selectedFragment)
        transaction.commit()
        state++
    }
}
