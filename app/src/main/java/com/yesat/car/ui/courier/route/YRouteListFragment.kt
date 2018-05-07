package com.yesat.car.ui.courier.route

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import com.yesat.car.R
import com.yesat.car.model.Route
import com.yesat.car.utility.*
import com.yesat.car.utility.Shared.norm
import com.yesat.car.utility.ui.ListFragment
import com.yesat.car.utility.ui.ListToolbarFragment
import kotlinx.android.synthetic.main.item_courier_route.view.*


class YRouteListFragment : ListToolbarFragment<Route, YRouteListFragment.ViewHolder>() {
    companion object {
        const val ROUTE_NEW_ACTIVITY = 25
    }

    override fun refreshListener(adapter: ListAdapter, srRefresh: SwipeRefreshLayout) {
        Api.courierService.routes().run2(srRefresh,{body ->
            norm("count ${body.size}")
            adapter.list = body
            adapter.notifyDataSetChanged()
        },{ _, error ->
            activity?.snack(error)
        })
    }

    override fun onCreateViewHolder2(parent: ViewGroup): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_courier_route, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(v: View) : ListFragment.ViewHolder(v){
        val hStartPoint = v.v_start_point!!
        val hEndPoint= v.v_end_point!!
        val hPosition = v.v_position!!
        val hTransport = v.v_transport!!
        val hDate = v.v_date!!

    }
    override fun onBindViewHolder2(holder: ViewHolder, item: Route) {
        holder.hStartPoint.text = locationFormat(item.startPoint!!)
        holder.hEndPoint.text = locationFormat(item.endPoint!!)
        holder.hPosition.text = definePosition(item.startPoint!!, item.endPoint!!)
        holder.hTransport.text = "${item.transport?.modelName} ${item.transport?.modelName}"
        holder.hDate.text = dateFormat()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_order -> {
                val i = Intent(activity, RouteNewActivity::class.java)
                startActivityForResult(i, ROUTE_NEW_ACTIVITY)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ROUTE_NEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                refreshListener!!.onRefresh()
            }
        }
    }
}
