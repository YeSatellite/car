package com.yesat.car.ui.client

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.yesat.car.R
import kotlinx.android.synthetic.main.activity_client_main.*


class XMainActivity : AppCompatActivity() {

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val selectedFragment = when (item.itemId) {
            R.id.navigation_home -> XRouteListFragment()
            R.id.navigation_dashboard -> XOrderFragment()
            R.id.navigation_notifications -> XProfileFragment()
            else -> XRouteListFragment()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_container, selectedFragment)
        transaction.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)

        v_navigation.setOnNavigationItemSelectedListener(navListener)
        v_navigation.selectedItemId = R.id.navigation_home
    }
}
