package com.yesat.car.ui.client.active

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.Offer
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.activity_courier_offer_profile.*
import kotlinx.android.synthetic.main.tmp_courier_profile.*

class CourierOfferAProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courier_offer_profile)

        val offer = intent.getSerializableExtra(Shared.offer) as Offer
        val user = offer.transport!!.owner!!

        v_image.src = user.avatar
        v_rating.text = user.rating
        v_phone.text = user.phone
        v_name.text = user.name
        v_experience.text = user.experience
        v_transport.text = offer.transport?.fullName
        v_citizenship.text = user.citizenship
        v_city.text = user.city?.name
        v_dob.text = user.dob
        v_payment_type.text = offer.paymentTypeName
        v_other_service.text = offer.otherServiceName
        v_shipping_type.text = offer.shippingTypeName
        v_accept.text = "DONE"
        v_accept.setOnClickListener({
            //TODO
        })
    }
}
