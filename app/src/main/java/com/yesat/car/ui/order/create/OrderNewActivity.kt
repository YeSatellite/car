package com.yesat.car.ui.order.create

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.Order


abstract class OrderNewActivity : AppCompatActivity() {


    var order: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new)


    }

    fun nextActivity(a:Any?){

    }
}
