package com.dicoding.submissionandroidfundamental.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingsPreferences private constructor(private val dataStore: DataStore<Preferences>){
    private val THEME_KEY = booleanPreferencesKey("theme_settings")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map {preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkThemeActive: Boolean){
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkThemeActive
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = SettingsPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}