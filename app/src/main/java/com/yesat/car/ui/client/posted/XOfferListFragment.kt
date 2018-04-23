package com.yesat.car.ui.client.posted

import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesat.car.R
import com.yesat.car.model.Offer
import com.yesat.car.model.Order
import com.yesat.car.ui.client.posted.XOfferListActivity.Companion.COURIER_OFFER_PROFILE
import com.yesat.car.ui.common.ListFragment
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.tmp_offer_item.view.*


class XOfferListFragment : ListFragment<Offer, XOfferListFragment.ViewHolder>() {
    override fun refreshListener(adapter: ListAdapter, srRefresh: SwipeRefreshLayout) {
        val order = activity!!.intent.getSerializableExtra(Shared.order) as Order?
        Api.clientService.offers(order!!.id!!).run2(srRefresh,{ body ->
            adapter.list = body
            adapter.notifyDataSetChanged()
        },{ _, error ->
            activity!!.snack(error)
        })
    }

    override fun onCreateViewHolder2(parent: ViewGroup): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.tmp_offer_item, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(v: View) : ListFragment.ViewHolder(v){
        val hName = v.v_name!!
        val hRating = v.v_rating!!
        val hTransportModel = v.v_transport_model!!
        val hShippingType = v.v_shipping_type!!
        val hPaymentType = v.v_payment_type!!
        val hDelete = v.v_delete!!
        val hCall = v.v_call!!
        val hPrice = v.v_price!!

    }
    override fun onBindViewHolder2(holder: ViewHolder, item: Offer) {
        holder.hName.text = item.transport?.owner?.name
        holder.hName.link = true
        holder.hName.setOnClickListener({})
        holder.hRating.text = item.transport?.owner?.rating
        holder.hTransportModel.text = "${item.transport?.modelName} ${item.transport?.modelName}"
        holder.hShippingType.text = item.shippingTypeName
        holder.hPaymentType.text = item.paymentTypeName
        holder.hPrice.text = item.price.toString()
        holder.hName.setOnClickListener({
            val i = Intent(activity, CourierOfferPProfileActivity::class.java)
            i.putExtra(Shared.offer,item)
            activity!!.startActivityForResult(i,COURIER_OFFER_PROFILE)
        })
        holder.hDelete.setOnClickListener {
            //TODO
        }
        holder.hCall.setOnClickListener{
            //TODO
        }

    }
}
