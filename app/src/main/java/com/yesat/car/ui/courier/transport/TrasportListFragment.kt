package com.yesat.car.ui.courier.transport

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import com.yesat.car.R
import com.yesat.car.model.Transport
import com.yesat.car.utility.*
import com.yesat.car.utility.ui.ListFragment
import kotlinx.android.synthetic.main.item_transport.view.*


class TrasportListFragment : ListFragment<Transport, TrasportListFragment.ViewHolder>() {
    companion object {
        const val TRANSPORT_NEW_ACTIVITY = 25
    }

    override fun refreshListener(adapter: ListAdapter, srRefresh: SwipeRefreshLayout) {
        Api.courierService.transports().run2(srRefresh,{body ->
            adapter.list = body
            adapter.notifyDataSetChanged()
        },{ _, error ->
            activity!!.snack(error)
        })
    }

    override fun onCreateViewHolder2(parent: ViewGroup): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_transport, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(v: View) : ListFragment.ViewHolder(v){
        val hImage = v.v_image!!
        val hName = v.v_name!!
        val hType= v.v_type!!
        val hTransportDetail = v.v_transport_detail!!

    }
    override fun onBindViewHolder2(holder: ViewHolder, item: Transport) {
        holder.hImage.src = item.image1
        holder.hName.text = item.fullName
        holder.hType.text = item.typeName
        holder.hTransportDetail.setOnClickListener {
            val i = Intent(activity, TransportDetailActivity::class.java)
            i.put2(item)
            startActivityForResult(i, TRANSPORT_NEW_ACTIVITY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_order_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_order -> {
//                val i = Intent(activity, CategoryActivity::class.java)
//                startActivityForResult(i,TRANSPORT_NEW_ACTIVITY)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TRANSPORT_NEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                refreshListener!!.onRefresh()
            }
        }
    }
}
