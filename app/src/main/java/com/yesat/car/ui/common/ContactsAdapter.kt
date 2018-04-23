package com.yesat.car.ui.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yesat.startkotlin.model.Contact
import com.yesat.car.R
import java.util.ArrayList

class ContactsAdapter(
        private var listener: ListInteractionListener,
        var contacts: List<Contact> = ArrayList()) :
        RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_contact, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.tvFirstName.text = contact.firstName
        holder.tvLastName.text = contact.lastName
        holder.tvPhone.text = contact.phone

        holder.v.setOnClickListener { listener.onInteract(position) }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    inner class ViewHolder(var v: View) : RecyclerView.ViewHolder(v) {
        var tvFirstName: TextView = v.findViewById(R.id.tv_first_name)
        var tvLastName: TextView = v.findViewById(R.id.tv_last_name)
        var tvPhone: TextView = v.findViewById(R.id.tv_phone)
    }

    interface ListInteractionListener {
        fun onInteract(position: Int)
    }
}