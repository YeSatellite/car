package com.yesat.car.ui.auth

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.InfoTmp
import com.yesat.car.model.Location
import com.yesat.car.model.User
import com.yesat.car.ui.info.InfoTmpActivity
import com.yesat.car.ui.info.LocationActivity
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.activity_client_register.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*

class ClientRegisterActivity : AppCompatActivity() {
    companion object {
        const val CITIZENSHIP_REQUEST_CODE = 57
        const val CITY_REQUEST_CODE = 86
        const val FINISH_REQUEST_CODE = 100
    }

    private val user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_register)

        user.type = User.CLIENT

        v_register.setOnClickListener{
            register()
        }


        v_dob.setOnClickListener{
            val calendar = Calendar.getInstance()
            DatePickerDialog(this@ClientRegisterActivity,
                    DatePickerDialog.OnDateSetListener {
                        _, year, month, dayOfMonth ->
                        v_dob.text2 = "$year-$month-$dayOfMonth"
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        v_citizenship.setOnClickListener {
            val i = Intent(this@ClientRegisterActivity, InfoTmpActivity::class.java)
            Shared.call = Api.infoService.country()
            startActivityForResult(i, CITIZENSHIP_REQUEST_CODE)
        }
        v_city.setOnClickListener({
            val i = Intent(this@ClientRegisterActivity, LocationActivity::class.java)
            startActivityForResult(i, CITY_REQUEST_CODE)
        })



    }

    private fun register(){
        try{
            user.phone = v_phone.get("phone is empty")
            user.name = v_name.get("name is empty")
            user.dob = v_dob.get("dob is empty")
            checkNotNull(user.citizenship)
            checkNotNull(user.city)

            val formData = MediaType.parse("multipart/form-data")
            val phone = RequestBody.create(formData, user.phone!!)
            val name = RequestBody.create(formData, user.name!!)
            val city = RequestBody.create(formData, user.city!!.id.toString())
            val citizenship = RequestBody.create(formData, user.citizenship!!)
            val dob = RequestBody.create(formData, user.dob!!)
            val type = RequestBody.create(formData, user.type!!)
            Api.authService.register(phone,name,city,citizenship,dob,type).run3(this,{
                val i = Intent(this, LoginActivity::class.java)
                i.put2(user.phone!!)
                startActivityForResult(i,FINISH_REQUEST_CODE)
            })


        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CITIZENSHIP_REQUEST_CODE -> {
                    user.citizenship = data!!.get2(InfoTmp::class.java).name
                    v_citizenship.text2 = user.citizenship!!
                }
                CITY_REQUEST_CODE -> {
                    val location = data!!.get2(Location::class.java)
                    user.city = location
                    v_city.text2 = locationFormat(location)
                }
            }
        }

        if(requestCode == FINISH_REQUEST_CODE){
            finish()
        }
    }
}
