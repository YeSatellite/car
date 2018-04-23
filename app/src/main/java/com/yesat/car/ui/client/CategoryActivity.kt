package com.yesat.car.ui.client

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesat.car.R
import com.yesat.car.model.Category
import com.yesat.car.model.Order
import com.yesat.car.ui.common.ListActivity
import com.yesat.car.ui.common.ListFragment
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.item_info_tmp.view.*


class CategoryActivity: ListActivity<Category, CategoryActivity.ViewHolder>() {

    override fun refreshListener(adapter: ListAdapter, srRefresh: SwipeRefreshLayout) {
        Api.infoService.category().run2(srRefresh,{ body ->
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
    override fun onBindViewHolder2(holder: ViewHolder, item: Category) {
        holder.hName.text = item.name
    }

    override fun onItemClick(item: Category){
        val i = Intent(this, OrderNewActivity::class.java)
        i.put2(item)
        startActivityForResult(i,36)
        finish()
    }
}
