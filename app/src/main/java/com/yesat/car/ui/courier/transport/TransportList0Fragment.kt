package com.yesat.car.ui.courier.transport

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesat.car.R
import kotlinx.android.synthetic.main.fragment_transport_list0.view.*


class TransportList0Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_transport_list0, container, false)
        (activity as AppCompatActivity).setSupportActionBar(v.toolbar)
        return v
    }
}
