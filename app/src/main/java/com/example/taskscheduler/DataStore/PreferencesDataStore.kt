package com.example.taskscheduler.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

//instance of preference data store
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "layout_preferences")

class PreferencesDataStore(context: Context) {
    private val IS_LINEAR_LAYOUT_MANAGER = booleanPreferencesKey("is_linear_layout_manager")

    //save preferences
    suspend fun saveLayoutPrefrence(context: Context,isLinearLayout : Boolean){
        context.datastore.edit {mutablePreferences ->
            mutablePreferences[IS_LINEAR_LAYOUT_MANAGER] = isLinearLayout
        }
    }

    //get preference
    val preferences : Flow<Boolean> = context.datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {preferences ->
            preferences[IS_LINEAR_LAYOUT_MANAGER] ?: true
        }
}