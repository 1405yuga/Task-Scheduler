package com.example.taskscheduler.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

//instance of preference data store
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "layout_preferences")

class PreferencesDataStore(context: Context) {

}