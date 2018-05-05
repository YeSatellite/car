package com.yesat.car.ui.auth

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.theartofdev.edmodo.cropper.CropImage
import com.yesat.car.R
import com.yesat.car.ui.client.XMainActivity
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val phone = intent.get2(String::class.java)
        v_login.setOnClickListener({
            val sms = v_sms.get("sms is empty")
            login(phone,sms)
        })
    }

    private fun login(phone:String, smsCode:String){
        val map = hashMapOf(
                "phone" to phone,
                "sms_code" to smsCode,
                "phone_type" to "Android",
                "registrations_id" to "test")
        Api.authService.login(map).run2(this,{user ->
            Shared.currentUser = user
            val status = clientOrCourier()
            startActivity(Intent(this, status))
        },{ _, error ->
            snack(error)
        })
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        finish()
    }


}