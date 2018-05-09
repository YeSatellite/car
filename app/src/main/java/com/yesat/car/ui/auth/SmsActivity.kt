package com.yesat.car.ui.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.utility.*
import com.yesat.car.utility.Shared.norm
import kotlinx.android.synthetic.main.activity_sms.*

class SmsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        authTest()

        v_send_sms.setOnClickListener {
            try {
                val phone = v_phone.get("phone is empty")
                sendSms(phone)
            }catch (ex: IllegalStateException){
                snack(ex.message ?: "Unknown error")
            }
        }

        v_sign_up.setOnClickListener {
            val i = Intent(this,RegisterActivity::class.java)
            startActivity(i)
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


    private fun authTest() {
        if (Shared.currentUser.token != null){
            val status = clientOrCourier()
            val i = Intent(this, status)
            val action = intent.getStringExtra(Shared.action)
            norm(this,action)
            i.putExtra(Shared.action,action)
            startActivityForResult(i,0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Shared.currentUser.token != null)
            finish()
    }
}












