package com.example.wirelessproject

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface API{
    @GET("/ipaddress")
    fun getServerIpAddress(): Call<IP>

    companion object {
        fun create(): API {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.32.170:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(API::class.java)
        }
    }
}