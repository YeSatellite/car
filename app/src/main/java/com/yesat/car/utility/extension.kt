package com.yesat.car.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.provider.MediaStore
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yesat.car.utility.Shared.norm
import java.io.Serializable

fun Activity.snack(text: String){
    val rootView = window.decorView.findViewById<View>(android.R.id.content)
    Snackbar.make(rootView,text, Snackbar.LENGTH_LONG).show()
}

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            item.setShiftingMode(false)
            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e(Shared.Tag.hana, "Unable to get shift mode field", e)
    } catch (e: IllegalAccessException) {
        Log.e(Shared.Tag.hana, "Unable to change value of shift mode", e)
    }
}

fun SwipeRefreshLayout.setOnRefreshListenerAuto(listener: () -> Unit){
    setOnRefreshListener(listener)
    post({
        listener()
        isRefreshing = true
    })
    setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.BLUE)
}


fun Activity.getPath(uri: Uri): String {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = this.managedQuery(uri, projection, null, null, null)
    val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    return cursor.getString(columnIndex)
}

fun EditText.get(error: String = ""): String{
    val text = this.text.toString()
    if (error.isNotBlank() && text.isBlank())
        throw IllegalStateException(error)
    return text
}

var ImageView.src : String?
    get() = ""
    set(value) = Picasso.get().load(value).into(this)


var TextView.link : Boolean
    get() = true
    set(value) {
        if (value) {
            this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }

fun ViewPager.onChange(listener:(position:Int) -> Unit){
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {listener(position)}
    })
}

@Suppress("UNCHECKED_CAST")
fun <T: Serializable> Intent.get2(type : Class<T>): T{
    norm(">>>get "+data::class.java.simpleName)
    return this.getSerializableExtra(type.simpleName) as T
}

fun Intent.put2(data: Serializable){
    norm(">>>put "+data::class.java.simpleName)
    this.putExtra(data::class.java.simpleName,data)
}















