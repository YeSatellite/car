package com.yesat.car.utility

import android.app.Activity
import android.app.ProgressDialog
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.yesat.car.model.*
import com.yesat.car.utility.Shared.norm
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object Api {
    private val retrofit = Retrofit.Builder()
            .baseUrl("http://yesatellite.pythonanywhere.com/")
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

        @POST("$path/register/")
        fun register(@Body user: User): Call<User>

        @POST("$path/login/")
        fun login(@Body phone_sms: Map<String, String>): Call<User>

        @POST("$path/sent-sms/")
        fun sentSms(@Body phone: Map<String, String>): Call<User>
    }

    var infoService = retrofit.create(InfoService::class.java)!!
    interface InfoService {
        companion object {
            const val path = "info"
        }

        @GET("$path/city/")
        fun city(@Query("region") regionId: Long): Call<List<Location>>

        @GET("$path/region/")
        fun region(@Query("country") countryId: Long): Call<List<InfoTmp>>

        @GET("$path/country/")
        fun country(): Call<List<InfoTmp>>

        @GET("$path/payment-type/")
        fun paymentType(): Call<List<InfoTmp>>

        @GET("$path/category/")
        fun category(): Call<List<Category>>
    }

    var clientService = retrofit.create(ClientService::class.java)!!
    interface ClientService {
        companion object {
            const val path = "client"
        }

        @GET("$path/order/")
        fun orders(@Query("status") status: String): Call<List<Order>>

        @POST("$path/order/")
        fun orderAdd(@Body order: Order): Call<Order>

        @GET("$path/order/{id}/offers/")
        fun offers(@Path("id") orderId: Long): Call<List<Offer>>

        @Multipart
        @POST("$path/order/{id}/offers/")
        fun offersAccept(@Path("id") orderId: Long,
                         @Part("offer") offerId: Long): Call<Any>
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
            101 to "phone already exist"
    )

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
                     fail: (code: Int, error: String) -> Unit
){
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
                val bodyString = response.errorBody()?.string()
                Log.e(Shared.Tag.norm, "error body: $bodyString\n" +
                        "error status: ${response.code()}")
                val code = response.headers()["Error-Code"]?.toIntOrNull() ?: 0
                fail(code, Api.ERROR_CODE[code] ?: "Something went wrong")
            }
            stop()
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            fail(200, "Check your internet connection")
            stop()
        }

    })
}