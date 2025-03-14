package com.example.wirelessproject

sealed class Homeclass(val route:String, val name:String, val img: Int) {

    object Home: Homeclass(route = "home_screen", name = "Home", img = R.drawable.house)
    object UploadImage:Homeclass(route ="Upload_Image", name = "UploadImage", img = R.drawable.gallery)
    object UploadFile:Homeclass(route ="Upload_File", name = "UploadFile",R.drawable.file)
}
