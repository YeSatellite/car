package com.yesat.car.ui.client

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.InfoTmp
import com.yesat.car.model.Location
import com.yesat.car.model.Route
import com.yesat.car.ui.info.InfoTmpActivity
import com.yesat.car.ui.info.LocationActivity
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.activity_route_filter.*
import java.util.*

class RouteFilterActivity : AppCompatActivity() {

    companion object {
        const val START_POINT_REQUEST_CODE = 54
        const val END_POINT_REQUEST_CODE = 57
        const val TYPE_REQUEST_CODE = 68
    }

    private val filter = Route.FilterRoute()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_filter)

        v_start_point.setOnClickListener({
            val i = Intent(this@RouteFilterActivity, LocationActivity::class.java)
            startActivityForResult(i, START_POINT_REQUEST_CODE)
        })
        v_end_point.setOnClickListener({
            val i = Intent(this@RouteFilterActivity,LocationActivity::class.java)
            startActivityForResult(i, END_POINT_REQUEST_CODE)
        })
        v_start_date.setOnClickListener{
            val calendar = Calendar.getInstance()
            DatePickerDialog(this@RouteFilterActivity,
                    DatePickerDialog.OnDateSetListener {
                        _, year, month, dayOfMonth ->
                        v_start_date.text2 = "$year-$month-$dayOfMonth"
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        v_end_date.setOnClickListener{
            TimePickerDialog(this@RouteFilterActivity,
                    TimePickerDialog.OnTimeSetListener {
                        _, hourOfDay, minute ->
                        v_end_date.text2 = "$hourOfDay:$minute"
                    }, 0, 0, true).show()
        }
        v_type.setOnClickListener {
            val i = Intent(this@RouteFilterActivity, InfoTmpActivity::class.java)
            Shared.call = Api.infoService.tType()
            startActivityForResult(i,TYPE_REQUEST_CODE)
        }
        v_add.setOnClickListener{
            filter()
        }

    }


    private fun filter(){
        try{
            check(filter.startPoint != null){getString(R.string.is_empty,"start point")}
            check(filter.endPoint != null){getString(R.string.is_empty,"end point")}
            filter.startDate = v_start_date.get(getString(R.string.is_empty,"start date"))
            filter.endDate = v_end_date.get(getString(R.string.is_empty,"end time"))
            checkNotNull(filter.type){getString(R.string.is_empty,"transport type")}

            val i = Intent()
            i.put2(filter)
            setResult(Activity.RESULT_OK)
            finish()


        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }
    }


    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                START_POINT_REQUEST_CODE -> {
                    val startPoint = data!!.get2(Location::class.java)
                    filter.startPoint = startPoint.id
                    v_start_point.text2 = locationFormat(startPoint)
                }
                END_POINT_REQUEST_CODE -> {
                    val endPoint = data!!.get2(Location::class.java)
                    filter.endPoint = endPoint.id
                    v_end_point.text2 = locationFormat(endPoint)
                }
                TYPE_REQUEST_CODE -> {
                    val type =  data!!.get2(InfoTmp::class.java)
                    filter.type = type.id
                    v_type.text2 = type.name ?: ""
                }
            }
        }
    }
}











