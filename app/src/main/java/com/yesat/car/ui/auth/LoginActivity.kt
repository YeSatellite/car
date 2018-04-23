package com.yesat.car.ui.auth

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.model.User
import com.yesat.car.ui.client.XMainActivity
import com.yesat.car.utility.Api
import com.yesat.car.utility.Shared
import com.yesat.car.utility.run2
import com.yesat.car.utility.snack

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Shared.init(PreferenceManager.getDefaultSharedPreferences(applicationContext))
//        login("+77752166661","1111")
//        logout()
        isAuthorised()
    }

    private fun isAuthorised() {
        if ( Shared.currentUser.token != null){
            startActivity(Intent(this, XMainActivity::class.java))
        }
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