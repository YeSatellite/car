package com.yesat.car.ui.order.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.yesat.car.R
import com.yesat.car.utility.Api
import com.yesat.car.utility.Shared.norm
import com.yesat.car.utility.run2
import com.yesat.car.utility.snack
import kotlinx.android.synthetic.main.activity_order_new5.*

class OrderNew5Activity : OrderNewActivity() {
    private val list = listOf("type1", "type2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new5)

        val adapter  = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line,list)
        v_payment_type.setAdapter(adapter)
        v_payment_type.setOnItemClickListener { _, _, position, _ ->
            order!!.paymentType = position + 1L
        }

        v_next.setOnClickListener({
            get()
        })

    }

    fun get() {
        try {
            if(order!!.paymentType == null)
                throw IllegalStateException("Choose payment type")
            order!!.ownerType = when(v_owner_type.checkedRadioButtonId){
                (R.id.type1) -> 1
                (R.id.type2) -> 2
                else ->{
                    throw IllegalStateException("Choose owner type")
                }
            }
            norm(order.toString())
            Api.clientService.orderAdd(order!!).run2(this,
                    {
                        body -> norm("Response : ($body)")
                        setResult(Activity.RESULT_OK, Intent())
                        finish()
                    },{
                _, error -> snack(error)

            })

        } catch (ex: IllegalStateException) {
            snack(ex.message ?: "Unknown error")
        }
    }
}
