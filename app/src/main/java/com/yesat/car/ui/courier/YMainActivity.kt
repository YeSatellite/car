package com.yesat.car.ui.courier

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import com.yesat.car.ui.ContactsFragment
import com.yesat.car.ui.courier.order.YOrderFragment
import com.yesat.car.ui.courier.route.YRouteListFragment
import com.yesat.car.ui.courier.transport.TransportListFragment
import com.yesat.car.utility.disableShiftMode
import kotlinx.android.synthetic.main.activity_courier_main.*

class YMainActivity : AppCompatActivity() {

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val selectedFragment = when (item.itemId) {
            R.id.navigation_home -> YRouteListFragment()
            R.id.navigation_dashboard -> TransportListFragment()
            R.id.navigation_notifications -> YOrderFragment()
            R.id.navigation_notificationss -> YProfileFragment()
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
        v_navigation.selectedItemId = R.id.navigation_home
        v_navigation.disableShiftMode()
    }
}
