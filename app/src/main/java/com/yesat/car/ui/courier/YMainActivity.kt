package com.yesat.car.ui.courier

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.ui.Contacts2Fragment
import com.yesat.car.ui.ContactsFragment
import com.yesat.car.ui.courier.transport.TrasportListFragment
import com.yesat.car.utility.disableShiftMode
import kotlinx.android.synthetic.main.activity_courier_main.*

class YMainActivity : AppCompatActivity() {

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val selectedFragment = when (item.itemId) {
            R.id.navigation_home -> Contacts2Fragment()
            R.id.navigation_dashboard -> TrasportListFragment()
            R.id.navigation_notifications -> Contacts2Fragment()
            R.id.navigation_notificationss -> Contacts2Fragment()
            else -> ContactsFragment()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_container, selectedFragment)
        transaction.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courier_main)

        v_navigation.setOnNavigationItemSelectedListener(navListener)
        v_navigation.disableShiftMode()
    }
}
