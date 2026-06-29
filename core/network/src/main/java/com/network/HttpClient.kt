package com.network

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class HttpClient {
    val okHttpClient = OkHttpClient()
    val json = Json { ignoreUnknownKeys = true }
    val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    suspend inline fun <reified T> get(url: String): T = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Error: ${response.code}")
            json.decodeFromString(response.body?.string().orEmpty())
        }
    }

    suspend inline fun <reified T> post(url: String, body: Any): T = withContext(Dispatchers.IO) {
        val requestBody = json.encodeToString(body).toRequestBody(jsonMediaType)
        val request = Request.Builder().url(url).post(requestBody).build()
        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Error: ${response.code}")
            json.decodeFromString(response.body?.string().orEmpty())
        }
    }
}