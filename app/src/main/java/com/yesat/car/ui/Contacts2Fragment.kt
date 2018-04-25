package com.yesat.car.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yesat.startkotlin.model.Contact
import com.yesat.car.R
import com.yesat.car.utility.Api
import com.yesat.car.utility.setOnRefreshListenerAuto
import kotlinx.android.synthetic.main.fragment_contact2.view.*
import kotlinx.android.synthetic.main.tmp_recycler_view.view.*


class Contacts2Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_contact2, container, false)
        (activity as AppCompatActivity).setSupportActionBar(v.toolbar)


        val adapter = ContactsAdapter(object : ContactsAdapter.ListInteractionListener {
            override fun onInteract(position: Int) {
                Log.d("xxx", "asdf")
            }
        })
        v.rv_list.adapter = adapter
        val srRefresh = v.sr_refresh
        srRefresh.setOnRefreshListenerAuto{
            Api.test{
                val contacts = ArrayList<Contact>()
                contacts.add(Contact("Ariana","Grande","+77757778899"))
                contacts.add(Contact("Ariana","Grande","+77757778899"))
                contacts.add(Contact("Ariana","Grande","+77757778899"))
                adapter.contacts = contacts
                adapter.notifyDataSetChanged()
                srRefresh.isRefreshing = false
            }
        }
        return v
    }
}
