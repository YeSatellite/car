package com.yesat.car.ui.auth

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.User
import com.yesat.car.ui.client.XMainActivity
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.activity_sms.*

class SmsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)
        Shared.init(PreferenceManager.getDefaultSharedPreferences(applicationContext))
        isAuthorised()
        v_send_sms.setOnClickListener {
            try {
                val phone = v_phone.get("phone is empty")
                sendSms(phone)
            }catch (ex: IllegalStateException){
                snack(ex.message ?: "Unknown error")
            }
        }
    }

    private fun isAuthorised() {
        if ( Shared.currentUser.token != null){
            startActivity(Intent(this, XMainActivity::class.java))
        }
    }

    private fun sendSms(phone: String){
        val map = hashMapOf("phone" to phone)
        Api.authService.sentSms(map).run2(this,{
            val i = Intent(this, LoginActivity::class.java)
            i.put2(phone)
            startActivity(i)
        },{ _, error ->
            snack(error)
        })
    }

    private fun login(phone:String, smsCode:String){
        val map = hashMapOf("phone" to phone,"sms_code" to smsCode)
        Api.authService.login(map).run2(this,{user ->
            Shared.currentUser = user
        },{ _, error ->
            snack(error)
        })
    }
    fun logout(){
        Shared.currentUser = User()
    }
}












