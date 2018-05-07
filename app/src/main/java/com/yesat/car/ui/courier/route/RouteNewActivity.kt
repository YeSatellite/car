package com.yesat.car.ui.courier.route

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.Location
import com.yesat.car.model.Route
import com.yesat.car.model.Transport
import com.yesat.car.ui.courier.transport.TransportListActivity
import com.yesat.car.ui.info.LocationActivity
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.activity_route_new.*
import java.util.*

class RouteNewActivity : AppCompatActivity() {

    companion object {
        const val START_POINT_REQUEST_CODE = 54
        const val END_POINT_REQUEST_CODE = 57
        const val TRANSPORT_REQUEST_CODE = 68
    }

    private val route = Route()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_new)

        v_start_point.setOnClickListener({
            val i = Intent(this@RouteNewActivity, LocationActivity::class.java)
            startActivityForResult(i, START_POINT_REQUEST_CODE)
        })
        v_end_point.setOnClickListener({
            val i = Intent(this@RouteNewActivity,LocationActivity::class.java)
            startActivityForResult(i, END_POINT_REQUEST_CODE)
        })
        v_shipping_date.setOnClickListener{
            val calendar = Calendar.getInstance()
            DatePickerDialog(this@RouteNewActivity,
                    DatePickerDialog.OnDateSetListener {
                        _, year, month, dayOfMonth ->
                        v_shipping_date.text2 = "$year-$month-$dayOfMonth"
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        v_shipping_time.setOnClickListener{
            TimePickerDialog(this@RouteNewActivity,
                    TimePickerDialog.OnTimeSetListener {
                        _, hourOfDay, minute ->
                        v_shipping_time.text2 = "$hourOfDay:$minute"
                    }, 0, 0, true).show()
        }
        v_transport.setOnClickListener{
            val i = Intent(this@RouteNewActivity, TransportListActivity::class.java)
            startActivityForResult(i, TRANSPORT_REQUEST_CODE)
        }
        v_add.setOnClickListener{
            create()
        }

    }


    private fun create(){
        try{
            check(route.startPoint != null){getString(R.string.is_empty,"start point")}
            check(route.endPoint != null){getString(R.string.is_empty,"end point")}
            route.shippingDate = v_shipping_date.get(getString(R.string.is_empty,"shipping date"))
            route.shippingTime = v_shipping_time.get(getString(R.string.is_empty,"shipping time"))
            checkNotNull(route.transport){getString(R.string.is_empty,"transport")}

            Api.courierService.routesAdd(route).run3(this){
                setResult(Activity.RESULT_OK)
                finish()
            }

        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }
    }


    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                START_POINT_REQUEST_CODE -> {
                    route.startPoint = data!!.get2(Location::class.java)
                    v_start_point.text2 = locationFormat(route.startPoint!!)
                }
                END_POINT_REQUEST_CODE -> {
                    route.endPoint = data!!.get2(Location::class.java)
                    v_end_point.text2 = locationFormat(route.endPoint!!)
                }
                TRANSPORT_REQUEST_CODE -> {
                    route.transport = data!!.get2(Transport::class.java)
                    v_transport.text2 = "${route.transport!!.markName}, ${route.transport!!.modelName}"
                }
            }
        }
    }
}











