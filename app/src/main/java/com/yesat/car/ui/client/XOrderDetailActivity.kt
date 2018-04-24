package com.yesat.car.ui.client

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.Order
import com.yesat.car.utility.*
import com.yesat.car.utility.Shared.norm
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.tmp_order_item.*

class XOrderDetailActivity : AppCompatActivity() {

    private var order: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        order = intent.get2(Order::class.java)
        norm(order.toString())

        v_title.text = order!!.title
        v_start_point.text = locationFormat(order!!.startPoint!!,order!!.startDetail!!)
        v_end_point.text = locationFormat(order!!.endPoint!!,order!!.endDetail!!)

        v_volume.text = order!!.volume.toString()
        v_mass.text = order!!.mass.toString()

        v_position.text = definePosition(order!!.startPoint!!,order!!.endPoint!!)
        v_category.text = order!!.categoryName
        v_payment_type.text = order!!.paymentTypeName

        v_comment.text = order!!.comment

        v_image1.src = order!!.image1
        v_image2.src = order!!.image2

    }
}
