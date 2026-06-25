package com.tmdb.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "tmdb_session")

class SessionManager(private val context: Context) {

    companion object {
        private val SESSION_ID_KEY = stringPreferencesKey("session_id")
        private val V4_TOKEN_KEY = stringPreferencesKey("v4_token")
    }

    val sessionId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SESSION_ID_KEY]
    }

    val v4Token: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[V4_TOKEN_KEY]
    }

    suspend fun saveV4Token(token: String) {
        context.dataStore.edit { preferences ->
            preferences[V4_TOKEN_KEY] = token
        }
    }

    suspend fun saveSessionId(sessionId: String) {
        context.dataStore.edit { preferences ->
            preferences[SESSION_ID_KEY] = sessionId
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(SESSION_ID_KEY)
        }
    }
}