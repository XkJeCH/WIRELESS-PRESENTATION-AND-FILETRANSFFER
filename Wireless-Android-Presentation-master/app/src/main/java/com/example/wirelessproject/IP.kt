package com.example.wirelessproject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IP(
    @Expose
    @SerializedName("serverIp")val ip:String,
)