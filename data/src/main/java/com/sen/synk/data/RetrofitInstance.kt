package com.sen.synk.data

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.sen.synk.data.constant.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    fun buildRetrofit(context: Context): Retrofit {

        val chuckerInterceptor = ChuckerInterceptor(context)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}