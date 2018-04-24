package com.yesat.car.ui.client

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.ui.Contacts2Fragment
import com.yesat.car.ui.ContactsFragment
import kotlinx.android.synthetic.main.activity_client_main.*

class XMainActivity : AppCompatActivity() {

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val selectedFragment = when (item.itemId) {
            R.id.navigation_home -> Contacts2Fragment()
            R.id.navigation_dashboard -> XOrderFragment()
            R.id.navigation_notifications -> ClientProfileFragment()
            else -> {
                ContactsFragment()
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_container, selectedFragment)
        transaction.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)

        navigation.setOnNavigationItemSelectedListener(navListener)
    }
}
