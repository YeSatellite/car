package com.yesat.car.ui.courier.offer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yesat.car.R
import com.yesat.car.model.Offer
import com.yesat.car.utility.get2
import kotlinx.android.synthetic.main.activity_offer_detail.*

class OfferDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_detail)

        val offer = intent.get2(Offer::class.java)

        v_mark.text = offer.transport!!.markName
        v_model.text = offer.transport!!.modelName
        v_type.text = offer.transport!!.typeName
        v_body_type.text = offer.transport!!.bodyName
        v_price.text = offer.price.toString()
        v_shipping_type.text = offer.shippingTypeName
        v_other_services.text = offer.otherServiceName
        v_payment_type.text = offer.paymentTypeName

    }
}
