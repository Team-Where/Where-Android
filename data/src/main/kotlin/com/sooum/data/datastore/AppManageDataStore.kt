package com.sooum.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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
        private val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
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

    /**
     * 첫 실행인지 확인 한다. 값이 없으면 true로 준다
     */
    fun getIsFirstLaunch(): Flow<Boolean> {
        return appDataStore.data.map { preferences ->
            preferences[FIRST_LAUNCH] ?: true
        }
    }

    /**
     * 첫 앱 실행이 끝난 경우 구분
     */
    suspend fun setNotFirstLaunch(){
        appDataStore.edit { preferences ->
            preferences[FIRST_LAUNCH] = false
        }
    }
}