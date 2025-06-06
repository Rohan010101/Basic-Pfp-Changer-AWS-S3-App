package com.example.aws_profile_picture_app

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONObject

suspend fun loadProfilePictureUrl(): String {

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://<ip_address>:<port>/getPfpUrl")
        .get()
        .build()

    val response = client.newCall(request).execute()
    if (!response.isSuccessful) {
        throw IOException("Failed to fetch URL: ${response.message}")
    }


    val json = JSONObject(response.body?.string() ?: "")
    return json.getString("url")
}