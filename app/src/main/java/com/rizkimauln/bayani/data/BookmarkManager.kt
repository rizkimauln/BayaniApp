package com.rizkimauln.bayani.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class BookmarkManager(private val context: Context) {

    companion object {
        val BOOKMARK_IDS_KEY = stringPreferencesKey("bookmark_ids")

        // Key untuk fitur Harian
        val DAILY_DATE_KEY = stringPreferencesKey("daily_date")
        val DAILY_ID_KEY = stringPreferencesKey("daily_verse_id")
    }

    // --- FITUR BOOKMARK (TETAP) ---
    val savedBookmarks: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            val savedString = preferences[BOOKMARK_IDS_KEY] ?: ""
            if (savedString.isEmpty()) emptySet() else savedString.split(",").toSet()
        }

    suspend fun saveBookmarks(ids: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[BOOKMARK_IDS_KEY] = ids.joinToString(",")
        }
    }

    // --- FITUR HARIAN (DIUPDATE) ---
    val dailyVerseInfo: Flow<Pair<String, String>> = context.dataStore.data
        .map { preferences ->
            val id = preferences[DAILY_ID_KEY] ?: ""
            val date = preferences[DAILY_DATE_KEY] ?: ""
            Pair(id, date)
        }

    // Simpan data harian baru
    suspend fun saveDailyVerseInfo(verseId: String, date: String) {
        context.dataStore.edit { preferences ->
            preferences[DAILY_ID_KEY] = verseId
            preferences[DAILY_DATE_KEY] = date
        }
    }
}