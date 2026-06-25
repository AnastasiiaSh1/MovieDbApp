package com.tmdb.repository

import com.network.dto.RequestTokenResponse
import com.network.dto.SessionResponse
import com.tmdb.storage.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class AuthRepositoryImpl(
    private val sessionManager: SessionManager,
    private val okHttpClient: OkHttpClient
) : AuthRepository {

    private val json = Json { ignoreUnknownKeys = true }
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()
    private val baseUrl = "https://api.themoviedb.org/3/"

    override suspend fun createRequestToken(): String? = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("${baseUrl}authentication/token/new")
                .build()

            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null

                val responseBody = response.body?.string() ?: return@withContext null
                val result = json.decodeFromString<RequestTokenResponse>(responseBody)

                if (result.success) result.requestToken else null
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun approveTokenFlow(requestToken: String): String {
        return "https://www.themoviedb.org/authenticate/$requestToken"
    }

    override suspend fun createSession(requestToken: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val jsonBody = "{\"request_token\":\"$requestToken\"}"
            val requestBody = jsonBody.toRequestBody(jsonMediaType)

            val request = Request.Builder()
                .url("${baseUrl}authentication/session/new")
                .post(requestBody)
                .build()

            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext false

                val responseBody = response.body?.string() ?: return@withContext false
                val result = json.decodeFromString<SessionResponse>(responseBody)

                if (result.success) {
                    sessionManager.saveSessionId(result.sessionId)
                    true
                } else {
                    false
                }
            }
        } catch (e: Exception) {
            false
        }
    }

    override fun getSessionId(): Flow<String?> {
        return sessionManager.sessionId
    }

    override suspend fun saveV4AccessToken(token: String) {
        sessionManager.saveV4Token(token)
    }

    override fun getV4AccessToken(): Flow<String?> {
        return sessionManager.v4Token
    }
}