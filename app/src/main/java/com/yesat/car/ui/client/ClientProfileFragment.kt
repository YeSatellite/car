package com.yesat.car.ui.client

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesat.car.R
import com.yesat.car.utility.Shared
import kotlinx.android.synthetic.main.fragment_client_profile.*

class ClientProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_client_profile, container, false)

        val user = Shared.currentUser

        v_phone.text = user.phone
        v_name.text = user.name
        v_city.text = user.city?.name
        v_dob.text = user.dob

        return v
    }
}
