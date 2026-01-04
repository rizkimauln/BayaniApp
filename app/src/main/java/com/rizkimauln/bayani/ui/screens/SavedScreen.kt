package com.rizkimauln.bayani.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api // Import ini
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox // Import ini
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rizkimauln.bayani.ui.components.VerseCard
import com.rizkimauln.bayani.ui.theme.*
import com.rizkimauln.bayani.viewmodel.BayaniViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(navController: NavController, viewModel: BayaniViewModel) {
    val savedVerses by viewModel.bookmarkedVerses.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState() // State Refresh

    Column(modifier = Modifier.fillMaxSize().background(BgBody)) {
        Row(
            modifier = Modifier.fillMaxWidth().background(White).padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Disimpan", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextMain)
        }

        // PullToRefreshBox tanpa indikator visual
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier.weight(1f),
            indicator = {} // Hilangkan indikator loading visual
        ) {
            LazyColumn(contentPadding = PaddingValues(20.dp), modifier = Modifier.fillMaxSize()) {
                if (savedVerses.isEmpty()) {
                    item {
                        Text(
                            "Belum ada ayat disimpan.",
                            color = TextMuted,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                } else {
                    items(savedVerses) { verse ->
                        VerseCard(verse = verse) { navController.navigate("detail/${verse.id}") }
                    }
                }
            }
        }
    }
}