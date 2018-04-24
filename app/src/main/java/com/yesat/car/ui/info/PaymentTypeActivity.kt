package com.yesat.car.ui.info

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesat.car.R
import com.yesat.car.model.InfoTmp
import com.yesat.car.ui.common.ListActivity
import com.yesat.car.ui.common.ListFragment
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.item_info_tmp.view.*


class PaymentTypeActivity: ListActivity<InfoTmp, PaymentTypeActivity.ViewHolder>() {

    override fun refreshListener(adapter: ListAdapter, srRefresh: SwipeRefreshLayout) {
        Api.infoService.paymentType().run2(srRefresh,{ body ->
            adapter.list = body
            adapter.notifyDataSetChanged()
        },{ _, error ->
            snack(error)
        })
    }

    override fun onCreateViewHolder2(parent: ViewGroup): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_info_tmp, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(v: View) : ListFragment.ViewHolder(v){
        val hName = v.v_name!!

    }
    override fun onBindViewHolder2(holder: ViewHolder, item: InfoTmp) {
        holder.hName.text = item.name
    }

    override fun onItemClick(item: InfoTmp){
        val i = Intent()
        i.put2(item)
        setResult(Activity.RESULT_OK,i)
        finish()
    }
}
