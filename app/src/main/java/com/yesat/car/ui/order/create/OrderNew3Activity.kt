package com.yesat.car.ui.order.create

import android.os.Bundle
import com.yesat.car.R
import com.yesat.car.utility.get
import com.yesat.car.utility.snack
import kotlinx.android.synthetic.main.activity_order_new3.*

class OrderNew3Activity : OrderNewActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new3)

        v_next.setOnClickListener({get()})
    }

    fun get(){
        try{
            order!!.acceptPerson = v_accept_person.get("acceptPerson is empty")
            order!!.acceptPersonContact = v_accept_personc.get("acceptPersonContact is empty")
            nextActivity(OrderNew4Activity::class.java)
        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }
    }
}
