package com.example.aws_profile_picture_app

import android.content.Context
import android.net.Uri
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

suspend fun uploadProfilePicture(context: Context, imageUri: Uri) {

    Log.d("uploadProfilePicture","uploadProfilePicture started")

    val contentResolver =context.contentResolver
    Log.d("uploadProfilePicture","contentResolver done")

    val inputStream = contentResolver.openInputStream(imageUri)
    Log.d("uploadProfilePicture","inputStream done")

    val fileBytes = inputStream?.readBytes() ?: return
    Log.d("uploadProfilePicture","fileBytes done")

    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart(
            name = "file",
            filename = "pfp.jpg",
            body = fileBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
        ).build()
    Log.d("uploadProfilePicture","val requestBody called")


    val request = Request.Builder()
        .url("http://<ip_address>:<port>/uploadPfp")
        .post(requestBody)
        .build()
    Log.d("uploadProfilePicture","val request called")


    val client = OkHttpClient()
    val response = client.newCall(request).execute()
    Log.d("uploadProfilePicture","val response called")

    if (!response.isSuccessful) {
        throw IOException("Failed to upload: ${response.message}")
    }
}













