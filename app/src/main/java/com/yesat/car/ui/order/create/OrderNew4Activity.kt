package com.yesat.car.ui.order.create

import android.os.Bundle
import android.widget.ArrayAdapter
import com.yesat.car.R
import com.yesat.car.model.Location
import com.yesat.car.utility.get
import com.yesat.car.utility.snack
import kotlinx.android.synthetic.main.activity_order_new4.*

class OrderNew4Activity : OrderNewActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new4)

        val mCats = arrayOf("Yernar", "Aybek", "Yereke")
        val adapter  = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, mCats)
        v_start_point.setAdapter(adapter)
        v_end_point.setAdapter(adapter)

        v_next.setOnClickListener({
            get()
        })

    }

    fun get(){
        try{
            order!!.startPoint = Location(1,null,null)
            order!!.endPoint = Location(1,null,null)
            order!!.startDetail = v_start_detail.get("Start Detail is empty")
            order!!.endDetail = v_end_detail.get("Start Detail is empty")
            nextActivity(OrderNew5Activity::class.java)
        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }
    }
}
