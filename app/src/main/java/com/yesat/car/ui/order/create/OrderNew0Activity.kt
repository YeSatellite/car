package com.yesat.car.ui.order.create

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.theartofdev.edmodo.cropper.CropImage
import com.yesat.car.R
import com.yesat.car.model.Order
import kotlinx.android.synthetic.main.activity_order_new0.*


class OrderNew0Activity : OrderNewActivity() {
    var image: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new0)
        order = Order()

        val imageListener = { v: View ->
            CropImage.startPickImageActivity(this)
            image = v as ImageView
        }

        image1.setOnClickListener(imageListener)
        image2.setOnClickListener(imageListener)


        next.setOnClickListener({
            nextActivity(OrderNew1Activity::class.java)
        })

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
                        image!!.setImageURI(imageUri)
                        when (image!!.id) {
                            R.id.image1 -> order!!.image1 = imageUri.path
                            R.id.image2 -> order!!.image2 = imageUri.path
                        }
                    }
                }
            }
        }
    }
}
