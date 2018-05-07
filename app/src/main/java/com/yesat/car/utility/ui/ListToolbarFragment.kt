package com.yesat.car.utility.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesat.car.R
import kotlinx.android.synthetic.main.fragment_list_toolbar.view.*
import kotlinx.android.synthetic.main.tmp_recycler_view.view.*

abstract class ListToolbarFragment<T,V : ListFragment.ViewHolder> : ListFragment<T,V>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_list_toolbar, container, false)
        (activity as AppCompatActivity).setSupportActionBar(v.toolbar)

        val adapter = ListAdapter()
        v.rv_list.adapter = adapter
        val srRefresh = v.sr_refresh

        refreshListener = SwipeRefreshLayout.OnRefreshListener({
            refreshListener(adapter,srRefresh)
        })


        srRefresh.setOnRefreshListener(refreshListener)

        srRefresh.post({
            refreshListener!!.onRefresh()
            srRefresh.isRefreshing = true
        })
        srRefresh.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.BLUE)
        return v
    }
}
