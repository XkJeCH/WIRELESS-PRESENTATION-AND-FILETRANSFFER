package com.example.wirelessproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.io.InputStream
import java.util.UUID

class Utill {
    companion object {
        fun uploadToStorage(uri: Uri, context: Context, type: String) {
            val storage = Firebase.storage
            var storageRef = storage.reference
            val unique_image_name = UUID.randomUUID().toString()
            var spaceRef: StorageReference

            if (type == "image") {
                spaceRef = storageRef.child("images/$unique_image_name.jpg")
            } else {
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let {

                var uploadTask = spaceRef.putBytes(byteArray)
                val url = "http://192.168.32.170:3000/"
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "upload successed",
                        Toast.LENGTH_SHORT
                    ).show()
                    spaceRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        val imageUrl = downloadUri.toString()
                        storeImageUrlInFirestore(imageUrl)

                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                }
            }


        }
        fun uploadToFileStorage(uri: Uri, context: Context,filename:String) {
            val storage = Firebase.storage
            val storageRef = storage.reference
            val uniqueFileName = filename



            val fileRef = storageRef.child("files/$uniqueFileName")

            val byteArray: ByteArray? = readBytesFromUri(uri, context)

            byteArray?.let {

                val uploadTask = fileRef.putBytes(byteArray)
                val url = "http://192.168.32.170:3000/"

                uploadTask.addOnFailureListener {
                    Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(context, "Upload succeeded", Toast.LENGTH_SHORT).show()

                    fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        val fileUrl = downloadUri.toString()
                        saveFileUrlToFirestore(fileUrl,uniqueFileName)

                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                }
            }
        }
        private fun storeImageUrlInFirestore(imageUrl: String) {
            val db = Firebase.firestore
            val imagesCollection = db.collection("images")
            val document = imagesCollection.document()

            document.set(mapOf("imageUrl" to imageUrl))
        }
        private fun readBytesFromUri(uri: Uri, context: Context): ByteArray? {
            var inputStream: InputStream? = null
            try {
                inputStream = context.contentResolver.openInputStream(uri)
                return inputStream?.readBytes()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                inputStream?.close()
            }
            return null
        }

        private fun saveFileUrlToFirestore(fileUrl: String,filename:String) {
            var id = filename

            val documentRef = Firebase.firestore.collection("File").document(id)

            documentRef.set(mapOf("FilePath" to fileUrl))

        }
         fun getFirstImageUri(context: Context): Uri? {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA
            )

            val selection = "${MediaStore.Images.Media.DISPLAY_NAME} IS NOT NULL"

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                sortOrder
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val uriColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    return Uri.parse(cursor.getString(uriColumnIndex))
                }
            }

            return null
        }

        }
}