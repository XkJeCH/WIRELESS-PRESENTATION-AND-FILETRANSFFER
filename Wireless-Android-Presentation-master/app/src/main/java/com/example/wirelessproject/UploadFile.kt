package com.example.wirelessproject

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.InputStream
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation", "RestrictedApi")
@Composable
fun FileUploadScreen(navController: NavHostController) {
    val contextForToast = LocalContext.current.applicationContext
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current.applicationContext
    val BttnColor = Color(android.graphics.Color.parseColor("#9c6644"))
    val singleFilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            uri = it
        }
    )

    var sendFileAction by remember {
        mutableStateOf(false)
    }

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
    var filename by remember{
        mutableStateOf("")
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { _, delta ->
                            if (delta < 0 && startY - delta <= 200 && !filePickerLaunched) {
                                uri?.let {
                                    Utill.uploadToFileStorage(it, context, filename = filename)
                                    uri = null
                                }
                                filePickerLaunched = true
                                navController.navigate(Homeclass.Home.route)
                            }
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (uri != null) {

                    Text("Swipe UP to Upload", modifier = Modifier)

                }
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                uri?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("File selected: ${getFileName(it)}")
                    filename = getFileName(uri = it)
                }
                Button(
                    onClick = {
                        if (sendFileAction) {
                            uri?.let {
                                Utill.uploadToFileStorage(it, context,filename)
                            }
                        } else {
                            singleFilePicker.launch("*/*")
                        }
                        sendFileAction = false
                    },
                    colors = ButtonDefaults.buttonColors(BttnColor),
                    modifier = Modifier
                ) {
                    Text(if (sendFileAction) "Send File" else "Pick File")
                }
                
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate(Homeclass.UploadFile.route) },
                        colors = ButtonDefaults.buttonColors(BttnColor)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    )
}

@SuppressLint("Range")
@Composable
fun getFileName(uri: Uri): String {
    val cursor = LocalContext.current.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        it.moveToFirst()
        val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        displayName ?: "Unknown File"
    } ?: "Unknown File"
}
