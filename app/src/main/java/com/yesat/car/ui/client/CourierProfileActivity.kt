package com.yesat.car.ui.client

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yesat.car.R
import com.yesat.car.model.User
import com.yesat.car.utility.get2
import com.yesat.car.utility.src
import kotlinx.android.synthetic.main.activity_courier_profile.*

class CourierProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courier_profile)

        val user = intent.get2(User::class.java)

        v_image.src = user.avatar
        v_rating.text = user.rating
        v_phone.text = user.phone
        v_name.text = user.name
        v_experience.text = user.experience.toString()
        v_citizenship.text = user.citizenship
        v_city.text = user.city?.name
        v_dob.text = user.dob
    }
}
