package com.yesat.car.utility

import android.app.Activity
import android.app.ProgressDialog
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.yesat.car.model.*
import com.yesat.car.utility.Shared.hana
import com.yesat.car.utility.Shared.norm
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File


object Api {
    private val retrofit = Retrofit.Builder()
            .baseUrl("http://188.166.50.157:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                var request = chain.request().newBuilder()
                if (getToken().length>10)
                    request = request.addHeader("Authorization", getToken())
                chain.proceed(request.build())
            }.build())
            .build()


    var authService = retrofit.create(AuthService::class.java)!!
    interface AuthService {
        companion object {
            const val path = "auth"
        }

        @Multipart
        @POST("$path/register/")
        fun register(
                @Part("phone") email:RequestBody,
                @Part("name") name:RequestBody,
                @Part("city") city:RequestBody,
                @Part("citizenship") citizenship:RequestBody,
                @Part("dob") dob:RequestBody,
                @Part("type") type:RequestBody,
                @Part("experience") experience:RequestBody? = null,
                @Part image: MultipartBody.Part? = null
        ): Call<Any>

        @POST("$path/login/")
        fun login(@Body phone_sms: Map<String, String>): Call<User>

        @POST("$path/sent-sms/")
        fun sentSms(@Body phone: Map<String, String>): Call<Any>
    }

    var infoService = retrofit.create(InfoService::class.java)!!
    interface InfoService {
        companion object {
            const val path = "info"
            const val transport = "transport"
        }

        @GET("$path/city/")
        fun city(@Query("region") regionId: Long): Call<List<Location>>

        @GET("$path/region/")
        fun region(@Query("country/") countryId: Long): Call<List<InfoTmp>>

        @GET("$path/country/")
        fun country(): Call<List<InfoTmp>>

        @GET("$path/payment-type/")
        fun paymentType(): Call<List<InfoTmp>>

        @GET("$path/category/")
        fun category(): Call<List<Category>>

        @GET("$path/other-type/")
        fun otherType(): Call<List<InfoTmp>>


        @GET("$path/$transport/type/")
        fun tType(): Call<List<InfoTmp>>

        @GET("$path/$transport/shipping-type/")
        fun tShippingType(): Call<List<InfoTmp>>

        @GET("$path/$transport/mark/")
        fun tMark(): Call<List<InfoTmp>>

        @GET("$path/$transport/body/")
        fun tBody(): Call<List<InfoTmp>>

        @GET("$path/$transport/model/")
        fun tModel(@Query("mark/") markId: Long): Call<List<InfoTmp>>
    }

    var clientService = retrofit.create(ClientService::class.java)!!
    interface ClientService {
        companion object {
            const val path = "client"
        }
        @GET("$path/clients/current")
        fun test(): Call<User>

        @GET("$path/order/")
        fun orders(@Query("status") status: String): Call<List<Order>>

        @POST("$path/order/")
        fun orderAdd(@Body order: Order): Call<Order>

        @Multipart
        @PATCH("$path/order/{id}/")
        fun orderUpdate(@Path("id") id: Long,
                        @Part image1: MultipartBody.Part,
                        @Part image2: MultipartBody.Part): Call<Order>

        @GET("$path/order/{id}/offers/")
        fun offers(@Path("id") orderId: Long): Call<List<Offer>>

        @Multipart
        @POST("$path/order/{id}/offers/")
        fun offersAccept(@Path("id") orderId: Long,
                         @Part("offer") offerId: Long): Call<Any>

        @GET("$path/routes/")
        fun routes(@Query("type") type: Long,
                   @Query("start_point") startPoint: Long,
                   @Query("end_point") endPoint: Long,
                   @Query("start_date") startDate: String,
                   @Query("end_date") endDate: String): Call<List<Route>>
    }

    var courierService = retrofit.create(CourierService::class.java)!!
    interface CourierService {
        companion object {
            const val path = "courier"
        }
        @GET("$path/couriers/current")
        fun test(): Call<User>

        @GET("$path/transports/")
        fun transports(): Call<List<Transport>>
        @POST("$path/transports/")
        fun transportsAdd(@Body transport: Transport): Call<Transport>
        @Multipart
        @PATCH("$path/transports/{id}/")
        fun transportsUpdate(@Path("id") id: Long,
                        @Part image1: MultipartBody.Part,
                        @Part image2: MultipartBody.Part): Call<Transport>


        @GET("$path/order/")
        fun orders(@Query("status") status: String): Call<List<Order>>

        @GET("$path/order/{id}/offer/")
        fun offer(@Path("id") orderId: Long): Call<Offer>

        @POST("$path/order/{id}/offer/")
        fun offerAdd(@Path("id") orderId: Long,@Body offer: Offer): Call<Offer>

        @GET("$path/routes/")
        fun routes(): Call<List<Route>>

        @POST("$path/routes/")
        fun routesAdd(@Body route: Route): Call<Route>

    }





    private fun getToken(): String {
        return "JWT ${Shared.currentUser.token}"
    }

    // ----------TEST-END----------
    fun test(run: () -> Unit) {
        Handler().postDelayed(run, 1000)
    }

    // ---------TEST-START---------

    val ERROR_CODE = mapOf(
            100 to "Unknown error",
            101 to "phone already exist",
            102 to "phone number is not correct",
            103 to "error 103",
            104 to "error 104"
    )

}

fun <T> Call<T>.run3(context: Activity, ok: (body:T) -> Unit){
    val pd = ProgressDialog(context)
    pd.setTitle("Loading")
    pd.show()
    run586(this,ok,{ _, error ->  context.snack(error)},{pd.dismiss()})
}

fun <T> Call<T>.run2(context: Activity,
                     ok: (body:T) -> Unit,
                     fail: (code: Int, error: String) -> Unit
                     ){
    val pd = ProgressDialog(context)
    pd.setTitle("Title")
    pd.setMessage("Message")
    pd.show()

    run586(this,ok,fail,{pd.dismiss()})
}

fun <T> Call<T>.run2(srRefresh: SwipeRefreshLayout,
                     ok: (body:T) -> Unit,
                     fail: (code: Int, error: String) -> Unit){
    run586(this,ok,fail,{srRefresh.isRefreshing = false})
}



private fun <T> run586(call: Call<T>,
                ok: (body:T) -> Unit,
                fail: (code: Int, error: String) -> Unit,
                stop: () -> Unit){
    call.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            val body = response!!.body()
            if(response.isSuccessful) {
                if (body != null) ok(body)
                else {
                    fail(0,"Error")
                    norm("body is null")
                }
            } else {
                if( response.code() == 401){
                    fail(200, "fatal")
                }
                val bodyString = response.errorBody()?.string()
                Log.e(Shared.Tag.norm, "error body: $bodyString\n" +
                        "error status: ${response.code()}")
                val code = response.headers()["Error-Code"]?.toIntOrNull() ?: 0
                fail(code, Api.ERROR_CODE[code] ?: "Something went wrong")
            }
            stop()
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            hana("<123>",t)
            fail(200, "Check your internet connection")
            stop()
        }

    })
}

fun File.toMultiPartImage(field: String): MultipartBody.Part {
    val body = RequestBody.create(MediaType.parse("image/*"), this)
    return MultipartBody.Part.createFormData(field, name, body)
}