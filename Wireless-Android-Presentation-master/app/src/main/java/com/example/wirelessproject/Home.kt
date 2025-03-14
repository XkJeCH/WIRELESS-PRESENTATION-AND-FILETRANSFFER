package com.example.wirelessproject

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wirelessproject.ui.theme.WirelessProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {
    val bttnColor = Color(android.graphics.Color.parseColor("#9c6644"))


        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                        .clickable(
                            onClick = {
                                navController.navigate(Homeclass.UploadFile.route)
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.file),
                        contentDescription = "File",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Button(
                    modifier = Modifier.padding(top = 10.dp, bottom = 50.dp),
                    onClick = {
                        navController.navigate(Homeclass.UploadFile.route)
                    },
                    colors = ButtonDefaults.buttonColors(bttnColor)
                ) {
                    Text("Send File")
                }

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                        .clickable(
                            onClick = {
                                navController.navigate(Homeclass.UploadImage.route)
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gallery),
                        contentDescription = "Gallery",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Button(
                    modifier = Modifier.padding(10.dp),
                    onClick = {
                        navController.navigate(Homeclass.UploadImage.route)
                    },
                    colors = ButtonDefaults.buttonColors(bttnColor)
                ) {
                    Text("Send Image")
                }
            }
        }
    }
