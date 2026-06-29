package com.tmdb.auth

import com.network.HttpClient

class AuthRepository(private val httpClient: HttpClient) {
    private val baseUrl = "https://api.themoviedb.org/3"

    suspend fun createRequestToken(): RequestTokenResponse {
        return httpClient.get("$baseUrl/authentication/token/new")
    }

    suspend fun createSession(requestToken: String): SessionResponse {
        return httpClient.post("$baseUrl/authentication/session/new", mapOf("request_token" to requestToken))
    }
}