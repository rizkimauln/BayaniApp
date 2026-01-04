package com.rizkimauln.bayani.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox // Import ini
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rizkimauln.bayani.ui.components.VerseCard
import com.rizkimauln.bayani.ui.theme.*
import com.rizkimauln.bayani.viewmodel.BayaniViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController, viewModel: BayaniViewModel) {
    val verses by viewModel.filteredVerses.collectAsState()
    val searchQuery by viewModel.getSearchText().collectAsState()
    val activeCategory by viewModel.getCategory().collectAsState()
    val categories = listOf("Semua", "Astronomi", "Biologi", "Teknologi", "Botani", "Geologi", "Kedokteran", "Fisika", "Embriologi", "Lainnya")
    val isRefreshing by viewModel.isRefreshing.collectAsState() // State Refresh

    Column(modifier = Modifier.fillMaxSize().background(BgBody)) {
        // Search Header
        Column(modifier = Modifier.background(White).padding(20.dp)) {
            Text("Jelajah", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextMain)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Cari", fontSize = 14.sp, color = TextMuted) },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = TextMuted) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF9FAFB),
                    focusedContainerColor = White,
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedBorderColor = Primary
                ),
                singleLine = true
            )
        }

        // Category Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            modifier = Modifier.background(White)
        ) {
            items(categories) { cat ->
                val isActive = activeCategory == cat
                Surface(
                    shape = CircleShape,
                    color = if (isActive) Primary else White,
                    border = if (isActive) null else BorderStroke(1.dp, Color(0xFFE5E7EB)),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable { viewModel.onCategorySelect(cat) }
                ) {
                    Text(
                        text = cat,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = if (isActive) White else TextMuted,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Result List dengan PullToRefreshBox (tanpa indikator visual)
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier.weight(1f),
            indicator = {} // Hilangkan indikator loading visual
        ) {
            LazyColumn(contentPadding = PaddingValues(20.dp), modifier = Modifier.fillMaxSize()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Hasil Pencarian", fontWeight = FontWeight.Bold, color = TextMain)
                        Icon(Icons.Outlined.List, contentDescription = null, tint = TextMain)
                    }
                }

                if (verses.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize().padding(top = 50.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Outlined.Description, contentDescription = null, modifier = Modifier.size(48.dp), tint = TextMuted.copy(alpha = 0.5f))
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("Belum ada data ditampilkan.", color = TextMuted)
                        }
                    }
                } else {
                    items(verses) { verse ->
                        VerseCard(verse = verse) { navController.navigate("detail/${verse.id}") }
                    }
                }
            }
        }
    }
}