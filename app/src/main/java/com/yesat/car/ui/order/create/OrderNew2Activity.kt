package com.yesat.car.ui.order.create

import android.os.Bundle
import com.yesat.car.R
import com.yesat.car.utility.get
import com.yesat.car.utility.snack
import kotlinx.android.synthetic.main.activity_order_new2.*

class OrderNew2Activity : OrderNewActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new2)


        v_next.setOnClickListener({get()})
    }


    fun get() = try{
            val height = v_height.get("height is empty").toFloat()
            val width = v_width.get("width is empty").toFloat()
            val length = v_length.get("length is empty").toFloat()
            val mass = v_mass.get("mass is empty").toFloat()
            order!!.volume = height*width*length/1000000
            order!!.mass = mass
            nextActivity(OrderNew3Activity::class.java)
        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }

}
