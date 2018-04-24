package com.yesat.car.ui.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        val map = hashMapOf("phone" to phone,"sms_code" to smsCode)
        Api.authService.login(map).run2(this,{user ->
            Shared.currentUser = user
            startActivity(Intent(this, XMainActivity::class.java))
        },{ _, error ->
            snack(error)
        })
    }
}