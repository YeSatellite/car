package com.yesat.car.ui.client

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.theartofdev.edmodo.cropper.CropImage
import com.yesat.car.R
import com.yesat.car.model.Category
import com.yesat.car.model.InfoTmp
import com.yesat.car.model.Location
import com.yesat.car.model.Order
import com.yesat.car.ui.info.LocationActivity
import com.yesat.car.ui.info.PaymentTypeActivity
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.activity_order_new.*
import kotlinx.android.synthetic.main.tmp_order_image.view.*


class OrderNewActivity : AppCompatActivity() {
    companion object {
        const val START_POINT_REQUEST_CODE = 54
        const val END_POINT_REQUEST_CODE = 57
        const val PAYMENT_TYPE_REQUEST_CODE = 86
    }


    private val imageList = ArrayList<Uri>()
    private val order = Order()
    private var imageNew: ImageView? = null
    private lateinit var adapter: CustomPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new)

        order.category = intent.get2(Category::class.java).id

        adapter = CustomPagerAdapter(this)
        images.adapter = adapter
        images.onChange { position ->
            v_image_text.text = "${position+1}/2 image"
        }

        imageNew = v_image_new
        imageNew!!.setOnClickListener({
            CropImage.startPickImageActivity(this)
        })

        v_start_point.setOnClickListener({
            val i = Intent(this@OrderNewActivity,LocationActivity::class.java)
            startActivityForResult(i,START_POINT_REQUEST_CODE)
        })
        v_end_point.setOnClickListener({
            val i = Intent(this@OrderNewActivity,LocationActivity::class.java)
            startActivityForResult(i,END_POINT_REQUEST_CODE)
        })
        v_payment_type.setOnClickListener {
            val i = Intent(this@OrderNewActivity,PaymentTypeActivity::class.java)
            startActivityForResult(i,PAYMENT_TYPE_REQUEST_CODE)
        }

        v_create.setOnClickListener({
            create()
        })
    }

    fun create(){
        try{
            order.title = v_title.get(getString(R.string.is_empty,"title"))
            order.comment = v_comment.get("comment is empty")
            val height = v_height.get("height is empty").toFloat()
            val width = v_width.get("width is empty").toFloat()
            val length = v_length.get("length is empty").toFloat()
            order.volume = height*width*length/1000000
            order.mass = v_mass.get("mass is empty").toFloat()
            if (order.startPoint == null) throw IllegalStateException("start point is empty")
            if (order.endPoint == null) throw IllegalStateException("end point is empty")
            order.startDetail = v_start_detail.get("Start Detail is empty")
            order.endDetail = v_end_detail.get("Start Detail is empty")
            order.shippingDate = v_shipping_date.get("shipping date is empty")
            order.shippingTime = v_shipping_time.get("shipping time is empty")
            order.acceptPerson = v_accept_person.get("acceptPerson is empty")
            order.acceptPersonContact = v_accept_person.get("acceptPersonContact is empty")
            order.ownerType = when(v_owner_type.checkedRadioButtonId){
                R.id.v_natural_person -> 1
                R.id.v_juridical_person -> 2
                else -> throw IllegalStateException("select owner type")
            }

            Api.clientService.orderAdd(order).run2(this,{ _ ->
                setResult(Activity.RESULT_OK, Intent())
                finish()
            },{ _, error ->
                snack(error)
            })
            
        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }
    }






















    inner class CustomPagerAdapter(mContext: Context) : PagerAdapter() {
        private var mLayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount() = 2

        override fun isViewFromObject(view: View, o: Any): Boolean {
            return view === o as LinearLayout
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val v = mLayoutInflater.inflate(R.layout.tmp_order_image, container, false)

            val image = v.v_image
            if(position < imageList.size)
                image.setImageURI(imageList[position])
            container.addView(v)
            return v
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as LinearLayout)
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }

    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE -> {
                    if (CropImage.isExplicitCameraPermissionRequired(this)) {
                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE)
                    }
                    else {
                        val imageUri = CropImage.getPickImageResultUri(this, data)
                        imageList.add(imageUri)
                        adapter.notifyDataSetChanged()
                        if (imageList.size == 2)
                            imageNew!!.visibility = View.GONE
                    }
                }
                START_POINT_REQUEST_CODE -> {
                    order.startPoint = data!!.get2(Location::class.java)
                    v_start_point.text2 = locationFormat(order.startPoint!!)
                }
                END_POINT_REQUEST_CODE -> {
                    order.endPoint = data!!.get2(Location::class.java)
                    v_end_point.text2 = locationFormat(order.endPoint!!)
                }
                PAYMENT_TYPE_REQUEST_CODE -> {
                    val paymentType =  data!!.get2(InfoTmp::class.java)
                    order.paymentType = paymentType.id
                    v_payment_type.setText(paymentType.name, TextView.BufferType.EDITABLE)
                }
            }
        }
    }
}