package com.sen.synk.data

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {



//    fun buildRetrofit(context: Context): Retrofit {
//        Log.d("album", "buildRetrofit: berhasul")
//        return build(context)
//    }
//
//    fun build(context: Context): Retrofit {
//        val gson: Gson = GsonBuilder()
//            .setLenient()
//            .create()
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(ChuckerInterceptor(context))
//            .build()
//
//        return Retrofit.Builder()
//            .baseUrl()
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(client)
//            .build()
//    }

    fun buildRetrofit(context: Context): Retrofit {
//        val loggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }

        val chuckerInterceptor = ChuckerInterceptor(context)

        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}