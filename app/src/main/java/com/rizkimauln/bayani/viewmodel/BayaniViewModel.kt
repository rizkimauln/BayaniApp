package com.rizkimauln.bayani.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rizkimauln.bayani.data.BookmarkManager
import com.rizkimauln.bayani.data.Verse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BayaniViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val bookmarkManager = BookmarkManager(application.applicationContext)

    // Raw Data
    private val _verses = MutableStateFlow<List<Verse>>(emptyList())

    // UI State Inputs
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow("Semua")

    // Bookmark
    private val _bookmarkedIds = bookmarkManager.savedBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    // Refresh & Loading
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // --- STATE BARU: INSPIRASI HARIAN ---
    private val _dailyVerse = MutableStateFlow<Verse?>(null)
    val dailyVerse: StateFlow<Verse?> = _dailyVerse.asStateFlow()

    init {
        fetchVerses()
        // Panggil logika daily verse saat inisialisasi
        handleDailyVerseLogic()
    }

    private fun fetchVerses() {
        viewModelScope.launch {
            db.collection("verses")
                .get()
                .addOnSuccessListener { result ->
                    val list = result.documents.map { doc ->
                        Verse(
                            id = doc.id,
                            arabic = doc.getString("arabic") ?: "",
                            ayah = doc.getString("ayah") ?: "",
                            category = doc.getString("category") ?: "",
                            explanation = doc.getString("explanation") ?: "",
                            scienceTitle = doc.getString("scienceTitle") ?: "",
                            surah = doc.getString("surah") ?: "",
                            translation = doc.getString("translation") ?: ""
                        )
                    }
                    _verses.value = list
                    _isLoading.value = false
                    _isRefreshing.value = false
                }
                .addOnFailureListener { e ->
                    _isLoading.value = false
                    _isRefreshing.value = false
                    Log.e("BayaniViewModel", "Error fetching data", e)
                }
        }
    }

    // --- LOGIKA UTAMA DAILY VERSE ---
    private fun handleDailyVerseLogic() {
        viewModelScope.launch {
            // Gabungkan data ayat (dari firebase) dan data simpanan (dari DataStore)
            combine(_verses, bookmarkManager.dailyVerseInfo) { verses, (savedId, savedDate) ->
                if (verses.isEmpty()) return@combine

                // Ambil tanggal hari ini (Format: YYYY-MM-DD)
                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                // Cek logic
                if (savedDate != today) {
                    // KASUS 1: Hari Baru (Tanggal beda) -> Pilih Random Baru & Simpan
                    val newRandom = verses.random()
                    bookmarkManager.saveDailyVerseInfo(newRandom.id, today)
                    _dailyVerse.value = newRandom
                } else {
                    // KASUS 2: Hari Sama -> Cari ayat yang ID-nya sudah disimpan
                    val found = verses.find { it.id == savedId }
                    // Jika ketemu pakai itu, jika tidak (misal data terhapus) ambil random baru
                    _dailyVerse.value = found ?: verses.random().also {
                        bookmarkManager.saveDailyVerseInfo(it.id, today)
                    }
                }
            }.collect()
        }
    }

    // Fungsi Refresh
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            fetchVerses()
        }
    }

    // Filter Logic
    val filteredVerses: StateFlow<List<Verse>> = combine(_verses, _searchQuery, _selectedCategory) { list, query, cat ->
        list.filter { verse ->
            val matchesCategory = if (cat == "Semua") true else verse.category.equals(cat, ignoreCase = true)
            val matchesSearch = verse.surah.contains(query, ignoreCase = true) ||
                    verse.scienceTitle.contains(query, ignoreCase = true) ||
                    verse.translation.contains(query, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Bookmark Logic
    val bookmarkedVerses: StateFlow<List<Verse>> = combine(_verses, _bookmarkedIds) { list, ids ->
        list.filter { it.id in ids }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }
    fun onCategorySelect(category: String) { _selectedCategory.value = category }

    fun toggleBookmark(id: String) {
        viewModelScope.launch {
            val current = _bookmarkedIds.value.toMutableSet()
            if (current.contains(id)) current.remove(id) else current.add(id)
            bookmarkManager.saveBookmarks(current)
        }
    }

    fun getCategory() = _selectedCategory
    fun getSearchText() = _searchQuery
    fun getBookmarksIds() = _bookmarkedIds

    // Expose dailyVerseInfo for other usage if needed
    val dailyVerseInfo = bookmarkManager.dailyVerseInfo
}