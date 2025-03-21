package com.example.wirelessproject

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import io.grpc.okhttp.internal.Util
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadImage(navController: NavHostController) {
    val BttnColor = Color(android.graphics.Color.parseColor("#9c6644"))
    val contextForToast = LocalContext.current.applicationContext
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }


    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                selectedImageUri = it
            }
        }
    )
    var startY by remember {
        mutableFloatStateOf(0f)
    }
    var filePickerLaunched by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(filePickerLaunched) {
        if (filePickerLaunched) {

            filePickerLaunched = false
        }
    }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { _, delta ->
                            if (delta < 0 && startY - delta <= 200 && !filePickerLaunched) {
                                // Swipe up gesture detected, trigger file picker
                                selectedImageUri?.let {
                                    Utill.uploadToStorage(it, contextForToast,type="image")
                                    selectedImageUri = null
                                }
                                filePickerLaunched = true
                                navController.navigate(Homeclass.Home.route)
                            }
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (selectedImageUri != null) {

                    Text("Swipe UP to Upload", modifier = Modifier.padding(50.dp))

                }
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(25.dp))
                    Text(text = "Add File Here", fontSize = 25.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .border(1.dp, Color.White, RoundedCornerShape(30.dp))
                    ) {
                        Image(
                            painter = if (selectedImageUri != null) rememberAsyncImagePainter(
                                selectedImageUri
                            ) else painterResource(R.drawable.resource_interface),
                            contentDescription = null,
                            modifier = Modifier.size(270.dp).align(Alignment.Center)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            photoPickerLauncher.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(BttnColor)
                    ) {
                        Text("Select Image")
                    }

                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate(Homeclass.Home.route) },
                            colors = ButtonDefaults.buttonColors(BttnColor)
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }



