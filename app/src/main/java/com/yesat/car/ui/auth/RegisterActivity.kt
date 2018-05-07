package com.yesat.car.ui.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yesat.car.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        v_client.setOnClickListener{
            val i = Intent(this, ClientRegisterActivity::class.java)
            startActivityForResult(i,74)
        }

        v_courier.setOnClickListener{
            val i = Intent(this, CourierRegisterActivity::class.java)
            startActivityForResult(i,23)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        finish()
    }
}
