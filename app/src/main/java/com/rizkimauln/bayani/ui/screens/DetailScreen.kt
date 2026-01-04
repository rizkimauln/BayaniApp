package com.rizkimauln.bayani.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rizkimauln.bayani.ui.theme.*
import com.rizkimauln.bayani.viewmodel.BayaniViewModel

@Composable
fun DetailScreen(navController: NavController, viewModel: BayaniViewModel, verseId: String?) {
    val verses by viewModel.filteredVerses.collectAsState()
    val bookmarksIds by viewModel.getBookmarksIds().collectAsState()

    val verse = verses.find { it.id == verseId }
    val isSaved = bookmarksIds.contains(verseId)

    if (verse != null) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().background(White).padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextMain,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                    Text(
                        "QS. ${verse.surah} : ${verse.ayah}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = TextMain
                    )
                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Save",
                        tint = if (isSaved) Accent else TextMuted,
                        modifier = Modifier.clickable { viewModel.toggleBookmark(verse.id) }
                    )
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(padding)
                    .padding(horizontal = 24.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = verse.arabic,
                        fontSize = 24.sp,
                        lineHeight = 48.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = IsepMisbahFontFamily,
                        color = TextMain,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "\"${verse.translation}\"",
                        fontSize = 16.sp,
                        color = TextMain,
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic,
                        lineHeight = 26.sp
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    // Science Explanation Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(Brush.linearGradient(listOf(PrimaryDark, Primary)))
                            .padding(24.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Science, contentDescription = null, tint = White)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    "PENJELASAN ILMIAH",
                                    color = White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    letterSpacing = 1.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = verse.explanation,
                                color = White.copy(alpha = 0.95f),
                                lineHeight = 24.sp,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}