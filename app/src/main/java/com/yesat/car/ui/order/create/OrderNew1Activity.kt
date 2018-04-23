package com.yesat.car.ui.order.create

import android.os.Bundle
import com.yesat.car.R
import com.yesat.car.utility.get
import com.yesat.car.utility.snack
import kotlinx.android.synthetic.main.activity_order_new1.*

class OrderNew1Activity : OrderNewActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new1)

        v_next.setOnClickListener({get()})
    }

    fun get(){
        try{
            order!!.title = tv_title.get("title is empty")
            order!!.comment = tv_comment.get("comment is empty")
            nextActivity(OrderNew2Activity::class.java)
        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }
    }
}
