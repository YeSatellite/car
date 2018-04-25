package com.yesat.car.ui.courier.transport

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.yesat.car.R
import com.yesat.car.model.Transport
import com.yesat.car.utility.get2
import com.yesat.car.utility.src
import kotlinx.android.synthetic.main.activity_transport_detail.*
import android.widget.LinearLayout



class TransportDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport_detail)

        val transport = intent.get2(Transport::class.java)

        v_transport_type.text = transport.typeName
        v_mark.text = transport.markName
        v_model.text = transport.modelName
        v_number.text = transport.number
        v_body.text = transport.bodyName
        v_shipping_type.text = transport.shippingTypeName
        v_volume.text = transport.volume.toString()
        v_volume.text = transport.volume.toString()
        v_image1.src = transport.image1
        v_image2.src = transport.image2
        v_comment.text = transport.comment

        val animZoomIn = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)

        v_image1.setOnClickListener {
            v_image1.startAnimation(animZoomIn);
        }

    }
}
