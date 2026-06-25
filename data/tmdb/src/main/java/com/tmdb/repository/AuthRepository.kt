package com.tmdb.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun createRequestToken(): String?

    fun approveTokenFlow(requestToken: String): String

    suspend fun createSession(requestToken: String): Boolean

    fun getSessionId(): Flow<String?>

    suspend fun saveV4AccessToken(token: String)

    fun getV4AccessToken(): Flow<String?>
}
