package com.rizkimauln.bayani.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rizkimauln.bayani.R
import com.rizkimauln.bayani.ui.theme.*
import com.rizkimauln.bayani.viewmodel.BayaniViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: BayaniViewModel) {
    // --- AMBIL DATA DARI VIEWMODEL ---
    val verses by viewModel.filteredVerses.collectAsState()

    // UPDATE: Ambil dailyVerse dari ViewModel (bukan random setiap kali render)
    val dailyVerse by viewModel.dailyVerse.collectAsState()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refresh() },
        modifier = Modifier.fillMaxSize(),
        indicator = {}
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            item {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Assalamualaikum,", fontSize = 14.sp, color = TextMuted)
                        Text("Temukan Kebesaran-Nya", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextMain)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.logo_bayani), // Pastikan ini sesuai nama file logomu
                        contentDescription = "Logo Aplikasi",
                        modifier = Modifier.size(40.dp)
                    )
                }

                // Hero Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Brush.linearGradient(listOf(Primary, PrimaryDark)))
                        .padding(24.dp)
                ) {
                    Column {
                        Text("Al-Quran adalah Petunjuk Semesta", color = White, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.fillMaxWidth(0.8f))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Pelajari bagaimana fakta sains membuktikan kebenaran ayat suci.", color = White.copy(alpha = 0.9f), fontSize = 13.sp, lineHeight = 20.sp, modifier = Modifier.fillMaxWidth(0.9f))
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                viewModel.onCategorySelect("Semua")
                                navController.navigate("explore") {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = Primary),
                            shape = RoundedCornerShape(30.dp),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text("Mulai Menjelajah", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(5.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                // Categories Grid
                Spacer(modifier = Modifier.height(25.dp))
                Text("Kategori", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextMain)
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val menuItems = listOf(
                        "Astronomi" to Icons.Outlined.Public,
                        "Biologi" to Icons.Outlined.BugReport,
                        "Teknologi" to Icons.Outlined.Computer,
                        "Lainnya" to Icons.Outlined.GridView
                    )

                    menuItems.forEach { (name, icon) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
                            viewModel.onCategorySelect(if(name == "Lainnya") "Semua" else name)
                            navController.navigate("explore") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(56.dp)
                                    .border(1.dp, PrimaryLight, RoundedCornerShape(18.dp))
                                    .background(Color(0xFFF0FDFA), RoundedCornerShape(18.dp))
                            ) {
                                Icon(icon, contentDescription = null, tint = Primary)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(name, fontSize = 12.sp, color = TextMuted, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                // Daily Inspiration Card
                Spacer(modifier = Modifier.height(25.dp))
                if (dailyVerse != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .background(Accent, RoundedCornerShape(20.dp))
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            ) {
                                Text("Ayat Hari Ini", color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(15.dp))

                            Text(
                                text = dailyVerse!!.arabic, // Force unwrap aman karena null check di atas
                                fontSize = 28.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 44.sp,
                                fontFamily = IsepMisbahFontFamily,
                                color = TextMain
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text("\"${dailyVerse!!.translation}\"", fontSize = 14.sp, color = TextMuted, textAlign = TextAlign.Center, fontStyle = FontStyle.Italic)

                            Spacer(modifier = Modifier.height(20.dp))
                            Divider(
                                color = Color(0xFFE5E7EB),
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(15.dp))

                            Row(
                                modifier = Modifier.clickable { navController.navigate("detail/${dailyVerse!!.id}") },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Baca Penjelasan Sains", color = Primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(5.dp))
                                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Primary, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}