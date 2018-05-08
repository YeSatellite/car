package com.yesat.car.ui.courier.order


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesat.car.R
import com.yesat.car.utility.Shared
import com.yesat.car.utility.Shared.norm
import kotlinx.android.synthetic.main.tmp_pager.view.*

class YOrderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.tmp_pager, container, false)
        (activity as AppCompatActivity).setSupportActionBar(v.toolbar)

        v.pager.adapter = OrderPagesAdapter(childFragmentManager)
        val tabs = v.tab_layout
        tabs.shouldExpand = true
        tabs.setViewPager(v.pager)

        val action = (activity as AppCompatActivity).intent.getStringExtra(Shared.action)
        norm(activity as AppCompatActivity,action)
        v.pager.currentItem = when (action){
            Shared.Action.acceptOffer -> 2
            else -> 0
        }


        return v
    }


    inner class OrderPagesAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        private var tabs = arrayOf("Posted", "Waiting", "Active")

        override fun getPageTitle(position: Int) = tabs[position]
        override fun getCount() = tabs.size

        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 -> YOrderPListFragment()
                1 -> YOrderWListFragment()
                2 -> YOrderAListFragment()
                else -> null
            }
        }
    }


}
