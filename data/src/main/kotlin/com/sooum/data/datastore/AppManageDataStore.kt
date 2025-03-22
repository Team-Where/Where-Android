package com.sooum.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private const val APP_CONFIG_NAME = "where_app_config.pb"
private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = APP_CONFIG_NAME)

@Singleton
class AppManageDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val appDataStore = context.appDataStore

    companion object {

        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

    suspend fun saveAccessToken(token: String) {
        appDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    fun getAccessToken(): Flow<String?> {
        return appDataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    }
}