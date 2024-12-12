package com.teknistest.todolist.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.datastore by preferencesDataStore(name = "user_preferences")
class UserPreferences(private val datastore: DataStore<Preferences>) {

    companion object {
        val USER_NAME = stringPreferencesKey("username")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

        @Volatile
        private var INSTANCE : UserPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>) : UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    val userName = datastore.data.map { pref -> pref[USER_NAME] ?:""}
    val isLoggedIn = datastore.data.map { pref -> pref[IS_LOGGED_IN]?:false}


    suspend fun saveUserName(name: String) {
        datastore.edit{ preferences ->
            preferences[USER_NAME] = name
        }
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        datastore.edit { preferences ->
            preferences[IS_LOGGED_IN] = loggedIn
        }
    }

}