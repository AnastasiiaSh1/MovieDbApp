package com.tmdb.auth

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RequestTokenResponse(
    val success: Boolean,
    @SerialName("request_token") val requestToken: String
)

@Serializable
data class SessionResponse(
    val success: Boolean,
    @SerialName("session_id") val sessionId: String
)