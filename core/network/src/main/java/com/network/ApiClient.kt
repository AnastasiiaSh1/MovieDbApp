package com.network

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ApiClient {
    val okHttpClient = OkHttpClient.Builder().build()

    val json = Json {
        ignoreUnknownKeys = true
    }

    inline fun <reified T> get(url: String): T {
        val request = Request.Builder().url(url).get().build()

        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val bodyString = response.body?.string() ?: throw IOException("Empty response body")

            return json.decodeFromString(bodyString)
        }
    }
}