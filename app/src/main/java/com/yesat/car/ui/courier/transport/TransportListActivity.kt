package com.yesat.car.ui.courier.transport

import android.app.Activity
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesat.car.R
import com.yesat.car.model.Transport
import com.yesat.car.utility.*
import com.yesat.car.utility.ui.ListActivity
import com.yesat.car.utility.ui.ListFragment
import kotlinx.android.synthetic.main.item_transport.view.*


class TransportListActivity: ListActivity<Transport, TransportListActivity.ViewHolder>() {

    override fun refreshListener(adapter: ListAdapter, srRefresh: SwipeRefreshLayout) {
        Api.courierService.transports().run2(srRefresh,{body ->
            adapter.list = body
            adapter.notifyDataSetChanged()
        },{ _, error ->
            this.snack(error)
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
        holder.hTransportDetail.visibility = View.GONE
    }

    override fun onItemClick(item: Transport){
        val i = Intent()
        i.put2(item)
        setResult(Activity.RESULT_OK,i)
        finish()
    }
}
