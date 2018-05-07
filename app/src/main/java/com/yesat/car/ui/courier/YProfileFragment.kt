package com.yesat.car.ui.courier

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.yesat.car.R
import com.yesat.car.model.User
import com.yesat.car.utility.Shared
import com.yesat.car.utility.Shared.norm
import com.yesat.car.utility.src
import kotlinx.android.synthetic.main.activity_courier_profile.view.*
import kotlinx.android.synthetic.main.fragment_courier_profile.view.*

class YProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_courier_profile, container, false)
        (activity as AppCompatActivity).setSupportActionBar(v.toolbar)
        setHasOptionsMenu(true)

        val user = Shared.currentUser
        norm(user.toString())

        v.v_image.src = user.avatar
        v.v_rating.text = user.rating
        v.v_phone.text = user.phone
        v.v_name.text = user.name
        v.v_experience.text = user.experience.toString()
        v.v_citizenship.text = user.citizenship
        v.v_city.text = user.city?.name
        v.v_dob.text = user.dob

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                Shared.currentUser = User()
                activity!!.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
