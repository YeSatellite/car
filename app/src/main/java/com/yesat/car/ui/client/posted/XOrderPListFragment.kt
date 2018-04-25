package com.yesat.car.ui.client.posted

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.support.v4.widget.SwipeRefreshLayout
import com.yesat.car.R
import com.yesat.car.model.Order
import com.yesat.car.utility.ui.ListFragment
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.item_posted_order.view.*
import kotlinx.android.synthetic.main.tmp_order_item.view.*
import android.os.Bundle
import android.view.*
import android.view.MenuInflater
import com.yesat.car.ui.client.CategoryActivity
import com.yesat.car.ui.client.XOrderDetailActivity


class XOrderPListFragment : ListFragment<Order, XOrderPListFragment.ViewHolder>() {
    companion object {
        const val OFFER_LIST_ACTIVITY = 25
    }

    override fun refreshListener(adapter: ListAdapter, srRefresh: SwipeRefreshLayout) {
        Api.clientService.orders(Shared.posted).run2(srRefresh,{body ->
            adapter.list = body
            adapter.notifyDataSetChanged()
        },{ _, error ->
            activity!!.snack(error)
        })
    }

    override fun onCreateViewHolder2(parent: ViewGroup): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_posted_order, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(v: View) : ListFragment.ViewHolder(v){
        val hTitle = v.v_title!!
        val hStartPoint = v.v_start_point!!
        val hEndPoint= v.v_end_point!!
        val hPosition = v.v_position!!
        val hComment = v.v_comment!!
        val hShowOffers = v.v_show_offers!!
        val hOrderDetail = v.v_order_detail!!

    }
    override fun onBindViewHolder2(holder: ViewHolder, item: Order) {
        holder.hTitle.text = item.title
        holder.hStartPoint.text = locationFormat(item.startPoint!!,item.startDetail)
        holder.hEndPoint.text = locationFormat(item.endPoint!!,item.endDetail)
        holder.hPosition.text = definePosition(item.startPoint!!, item.endPoint!!)
        holder.hComment.text = item.comment
        holder.hShowOffers.paintFlags = holder.hShowOffers.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        holder.hShowOffers.setOnClickListener({
            val i = Intent(activity, XOfferListActivity::class.java)
            i.putExtra(Shared.order,item)
            startActivityForResult(i, OFFER_LIST_ACTIVITY)
        })
        holder.hOrderDetail.setOnClickListener {
            val i = Intent(activity, XOrderDetailActivity::class.java)
            i.put2(item)
            startActivityForResult(i, OFFER_LIST_ACTIVITY)
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
                val i = Intent(activity, CategoryActivity::class.java)
                startActivityForResult(i,32)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OFFER_LIST_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                refreshListener!!.onRefresh()
            }
        }
    }
}
