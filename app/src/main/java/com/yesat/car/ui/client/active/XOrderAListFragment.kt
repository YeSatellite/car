package com.yesat.car.ui.client.active

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesat.car.R
import com.yesat.car.model.Order
import com.yesat.car.ui.client.XOrderDetailActivity
import com.yesat.car.utility.ui.ListFragment
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.item_active_order.view.*
import kotlinx.android.synthetic.main.tmp_order_item.view.*


class XOrderAListFragment : ListFragment<Order, XOrderAListFragment.ViewHolder>() {
    override fun refreshListener(adapter: ListAdapter, srRefresh: SwipeRefreshLayout) {
        Api.clientService.orders(Shared.active).run2(srRefresh,{body ->
            adapter.list = body
            adapter.notifyDataSetChanged()
        },{ _, error ->
            activity?.snack(error)
        })
    }

    override fun onCreateViewHolder2(parent: ViewGroup): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_active_order, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(v: View) : ListFragment.ViewHolder(v){
        val hTitle = v.v_title!!
        val hStartPoint = v.v_start_point!!
        val hEndPoint= v.v_end_point!!
        val hPosition = v.v_position!!
        val hPrice = v.v_price!!
        val hComment = v.v_comment!!
        val hYourCourier = v.v_your_courier!!
        val hOrderDetail = v.v_order_detail!!

    }
    override fun onBindViewHolder2(holder: ViewHolder, item: Order) {
        holder.hTitle.text = item.title
        holder.hStartPoint.text = locationFormat(item.startPoint!!,item.startDetail)
        holder.hEndPoint.text = locationFormat(item.endPoint!!,item.endDetail)
        holder.hPosition.text = definePosition(item.startPoint!!, item.endPoint!!)
        holder.hPrice.text = item.offer!!.price.toString()
        holder.hComment.text = item.comment
        holder.hYourCourier.paintFlags = holder.hYourCourier.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        holder.hYourCourier.setOnClickListener({
            val i = Intent(activity, CourierOfferAProfileActivity::class.java)
            i.putExtra(Shared.offer,item.offer!!)
            startActivityForResult(i, 25)
        })
        holder.hOrderDetail.setOnClickListener {
            val i = Intent(activity, XOrderDetailActivity::class.java)
            i.put2(item)
            startActivity(i)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 25) {
            if (resultCode == Activity.RESULT_OK) {
                refreshListener!!.onRefresh()
            }
        }
    }
}
