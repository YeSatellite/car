package com.yesat.car.ui.courier

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.*
import com.yesat.car.ui.courier.transport.TransportList0Fragment
import com.yesat.car.ui.courier.transport.TransportListActivity
import com.yesat.car.ui.courier.transport.TransportNewActivity
import com.yesat.car.ui.info.InfoTmpActivity
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.activity_offer_new.*


@SuppressLint("Registered")
class OfferNewActivity : AppCompatActivity() {
    companion object {
        const val TRANSPORT_REQUEST_CODE = 68
        const val PAYMENT_TYPE_REQUEST_CODE = 86
        const val OTHER_SERVICE_REQUEST_CODE = 57
        const val SHIPPING_TYPE_REQUEST_CODE = 54
    }
    private val offer = Offer()
    private var order: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_new)

        order = intent.get2(Order::class.java)
        v_date.text = order!!.shippingDate

        v_transport.setOnClickListener{
            val i = Intent(this@OfferNewActivity,TransportListActivity::class.java)
            startActivityForResult(i,TRANSPORT_REQUEST_CODE)
        }
        v_payment_type.setOnClickListener {
            val i = Intent(this@OfferNewActivity,InfoTmpActivity::class.java)
            Shared.call = Api.infoService.paymentType()
            startActivityForResult(i,PAYMENT_TYPE_REQUEST_CODE)
        }
        v_other_service.setOnClickListener {
            val i = Intent(this@OfferNewActivity,InfoTmpActivity::class.java)
            Shared.call = Api.infoService.otherType()
            startActivityForResult(i,OTHER_SERVICE_REQUEST_CODE)
        }
        v_shipping_type.setOnClickListener{
            val i = Intent(this@OfferNewActivity,InfoTmpActivity::class.java)
            Shared.call = Api.infoService.tShippingType()
            startActivityForResult(i,SHIPPING_TYPE_REQUEST_CODE)
        }
        v_create.setOnClickListener({
            create()
        })
    }

    private fun create(){
        try{
            offer.price = v_price.get(getString(R.string.is_empty,"price")).toLong()
            offer.comment = v_comment.get(getString(R.string.is_empty,"comment"))
            checkNotNull(offer.transport){getString(R.string.is_empty,"transport")}
            checkNotNull(offer.paymentType){getString(R.string.is_empty,"payment type")}
            checkNotNull(offer.otherService){getString(R.string.is_empty,"other service")}
            checkNotNull(offer.shippingType){getString(R.string.is_empty,"shipping type")}

            Api.courierService.offerAdd(order!!.id!!,offer).run2(this,{
                setResult(Activity.RESULT_OK)
                finish()
            },{ _, error ->
                snack(error)
            })
            
        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TRANSPORT_REQUEST_CODE -> {
                    offer.transport = data!!.get2(Transport::class.java)
                    v_transport.text2 = "${offer.transport!!.markName}, ${offer.transport!!.modelName}"
                }
                PAYMENT_TYPE_REQUEST_CODE -> {
                    val paymentType =  data!!.get2(InfoTmp::class.java)
                    offer.paymentType = paymentType.id
                    v_payment_type.text2 = paymentType.name!!
                }
                OTHER_SERVICE_REQUEST_CODE -> {
                    val otherService =  data!!.get2(InfoTmp::class.java)
                    offer.otherService = otherService.id
                    v_other_service.text2 = otherService.name!!
                }
                SHIPPING_TYPE_REQUEST_CODE -> {
                    val shippingType =  data!!.get2(InfoTmp::class.java)
                    offer.shippingType = shippingType.id
                    v_shipping_type.text2 = shippingType.name!!
                }
            }
        }
    }
}









