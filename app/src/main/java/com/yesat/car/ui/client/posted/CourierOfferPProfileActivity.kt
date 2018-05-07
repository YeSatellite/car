package com.yesat.car.ui.client.posted

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.Offer
import com.yesat.car.utility.*
import com.yesat.car.utility.Shared.norm
import kotlinx.android.synthetic.main.activity_courier_offer_profile.*
import kotlinx.android.synthetic.main.tmp_courier_profile.*

class CourierOfferPProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courier_offer_profile)

        val offer = intent.getSerializableExtra(Shared.offer) as Offer
        val user = offer.transport!!.owner!!

        v_image.src = user.avatar
        v_rating.text = user.rating
        v_phone.text = user.phone
        v_name.text = user.name
        v_experience.text = user.experience.toString()
        v_transport.text = offer.transport?.fullName
        v_citizenship.text = user.citizenship
        v_city.text = user.city?.name
        v_dob.text = user.dob
        v_payment_type.text = offer.paymentTypeName
        v_other_service.text = offer.otherServiceName
        v_shipping_type.text = offer.shippingTypeName
        v_accept.setOnClickListener({
            Api.clientService.offersAccept(offer.order!!, offer.id!!)
                    .run2(this@CourierOfferPProfileActivity,{
                        setResult(Activity.RESULT_OK, Intent())
                        finish()
                    },{
                        _, error ->
                        snack(error)
                        norm(offer.order!!.toString() + " "+offer.id!!)
                    })
        })
    }
}
