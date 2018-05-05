package com.yesat.car.ui.client.posted

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yesat.car.R
import com.yesat.car.model.Order
import com.yesat.car.utility.Shared
import com.yesat.car.utility.Shared.norm
import com.yesat.car.utility.locationFormat
import kotlinx.android.synthetic.main.tmp_order_item.*

class XOfferListActivity : AppCompatActivity() {

    companion object {
        const val COURIER_OFFER_PROFILE = 28
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_list)

        val order = intent.getSerializableExtra(Shared.order) as Order

        v_title.text = order.title
        v_start_point.text = locationFormat(order.startPoint!!,order.startDetail)
        v_end_point.text = locationFormat(order.endPoint!!,order.endDetail)
        norm("Hello2")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == COURIER_OFFER_PROFILE){
            if (resultCode == Activity.RESULT_OK){
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }
    }
}
