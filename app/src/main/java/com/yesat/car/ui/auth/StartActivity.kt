package com.yesat.car.ui.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.yesat.car.R
import com.yesat.car.model.User
import com.yesat.car.model.User1
import com.yesat.car.utility.*
import com.yesat.car.utility.Shared.norm

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Shared.init(PreferenceManager.getDefaultSharedPreferences(applicationContext))

        if ( Shared.currentUser.token == null){
            startActivityForResult(Intent(this, SmsActivity::class.java),45)
        }
        norm("111")

        val test =  when(Shared.currentUser.type){
            User.CLIENT -> Api.clientService.test()
            User.COURIER -> Api.courierService.test()
            else -> {
                Shared.currentUser = User1()
                return
            }
        }
        norm("222")

        test.run2(this , { body ->
            body.token = Shared.currentUser.token
            Shared.currentUser = body
            val type = clientOrCourier()
            startActivityForResult(Intent(this, type), 47)}, { code, _ ->
            if (code == 200) {
                Shared.currentUser = User1()
                startActivityForResult(Intent(this, SmsActivity::class.java),45)
            }
        })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        finish()
    }
}
