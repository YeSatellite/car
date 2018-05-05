package com.yesat.car.ui.courier.transport

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.yesat.car.R
import com.yesat.car.model.InfoTmp
import com.yesat.car.model.Transport
import com.yesat.car.ui.info.InfoTmpActivity
import com.yesat.car.utility.*
import kotlinx.android.synthetic.main.activity_transport_new.*
import java.io.File

class TransportNewActivity : AppCompatActivity() {

    companion object {
        const val TRANSPORT_TYPE_REQUEST_CODE = 54
        const val MARK_REQUEST_CODE = 57
        const val MODEL_REQUEST_CODE = 84
        const val BODY_REQUEST_CODE = 86
        const val SHIPPING_TYPE_REQUEST_CODE = 89
        const val IMAGE1_REQUEST_CODE = 12
        const val IMAGE2_REQUEST_CODE = 13
    }

    private val transport = Transport()
    private val images = Array<File?>(2){null}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport_new)

        v_transport_type.setOnClickListener {
            val i = Intent(this@TransportNewActivity, InfoTmpActivity::class.java)
            Shared.call = Api.infoService.tType()
            startActivityForResult(i,TRANSPORT_TYPE_REQUEST_CODE)
        }

        v_mark.setOnClickListener {
            val i = Intent(this@TransportNewActivity, InfoTmpActivity::class.java)
            Shared.call= Api.infoService.tMark()
            startActivityForResult(i, MARK_REQUEST_CODE)
        }
        v_model.setOnClickListener {
            val i = Intent(this@TransportNewActivity, InfoTmpActivity::class.java)
            Shared.call= Api.infoService.tModel(transport.mark ?: run {
                snack("Choose mark")
                return@setOnClickListener
            })
            startActivityForResult(i, MODEL_REQUEST_CODE)
        }

        v_body.setOnClickListener {
            val i = Intent(this@TransportNewActivity, InfoTmpActivity::class.java)
            Shared.call= Api.infoService.tBody()
            startActivityForResult(i, BODY_REQUEST_CODE)
        }

        v_shipping_type.setOnClickListener {
            val i = Intent(this@TransportNewActivity, InfoTmpActivity::class.java)
            Shared.call= Api.infoService.tShippingType()
            startActivityForResult(i, SHIPPING_TYPE_REQUEST_CODE)
        }

        v_image1.setOnClickListener{
            startActivityForResult(CropImage.getPickImageChooserIntent(this),
                    IMAGE1_REQUEST_CODE)
        }
        v_image2.setOnClickListener {
            startActivityForResult(CropImage.getPickImageChooserIntent(this),
                    IMAGE2_REQUEST_CODE)
        }

        v_add.setOnClickListener{
            create()
        }

    }


    private fun create(){
        try{
            checkNotNull(transport.type){"type is empty"}
            checkNotNull(images[0]){"image1 "}
            checkNotNull(images[1]){"image2"}
            checkNotNull(transport.model){"model is empty"}
            transport.number = v_number.get("number is empty")
            checkNotNull(transport.body){"body id empty"}
            checkNotNull(transport.shippingType){"shipping type id empty"}
            val height = v_height.get("height is empty").toFloat()
            val width = v_width.get("width is empty").toFloat()
            val length = v_length.get("length is empty").toFloat()
            transport.volume = height*width*length
            transport.comment = v_comment.get("comment is empty")
            Api.courierService.transportsAdd(transport).run3(this){ body ->
                updateImage(body.id!!)
            }

        }catch (ex: IllegalStateException){
            snack(ex.message ?: "Unknown error")
        }
    }

    private fun updateImage(id: Long) {
        val image1 = images[0]!!.toMultiPartImage("image1")
        val image2 = images[1]!!.toMultiPartImage("image2")

        Api.courierService.transportsUpdate(id,image1,image2).run2(this,{
            setResult(Activity.RESULT_OK, Intent())
            finish()
        },{ _, error ->
            snack(error)
        })
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TRANSPORT_TYPE_REQUEST_CODE -> {
                    val transportType =  data!!.get2(InfoTmp::class.java)
                    transport.type = transportType.id
                    v_transport_type.text2 = transportType.name ?: ""
                }
                MARK_REQUEST_CODE -> {
                    val mark =  data!!.get2(InfoTmp::class.java)
                    transport.mark = mark.id
                    v_mark.text2 = mark.name ?: ""
                }
                MODEL_REQUEST_CODE -> {
                    val model =  data!!.get2(InfoTmp::class.java)
                    transport.model = model.id
                    v_model.text2 = model.name ?: ""
                }
                BODY_REQUEST_CODE -> {
                    val body =  data!!.get2(InfoTmp::class.java)
                    transport.body = body.id
                    v_body.text2 = body.name ?: ""
                }
                SHIPPING_TYPE_REQUEST_CODE -> {
                    val shippingType =  data!!.get2(InfoTmp::class.java)
                    transport.shippingType = shippingType.id
                    v_shipping_type.text2 = shippingType.name ?: ""
                }
                IMAGE1_REQUEST_CODE -> {
                    val imageUri = CropImage.getPickImageResultUri(this, data)
                    v_image1.setImageURI(imageUri)
                    images[0] = compressImage(imageUri)
                }
                IMAGE2_REQUEST_CODE -> {
                    val imageUri = CropImage.getPickImageResultUri(this, data)
                    v_image2.setImageURI(imageUri)
                    images[1] = compressImage(imageUri)
                }
            }
        }
    }
}











