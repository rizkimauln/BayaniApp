package com.rizkimauln.bayani.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rizkimauln.bayani.ui.screens.*
import com.rizkimauln.bayani.ui.theme.*
import com.rizkimauln.bayani.viewmodel.BayaniViewModel

@Composable
fun BayaniApp(viewModel: BayaniViewModel = viewModel()) {
    val navController = rememberNavController()
    // Sembunyikan BottomBar jika sedang di Detail Screen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute?.startsWith("detail") == false

    Scaffold(
        bottomBar = {
            if (showBottomBar) BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding).background(BgBody)
        ) {
            composable("home") { HomeScreen(navController, viewModel) }
            composable("explore") { ExploreScreen(navController, viewModel) }
            composable("saved") { SavedScreen(navController, viewModel) }
            composable("about") { AboutScreen() }
            composable("detail/{verseId}") { backStackEntry ->
                val verseId = backStackEntry.arguments?.getString("verseId")
                DetailScreen(navController, viewModel, verseId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf("Beranda", "Jelajah", "Disimpan", "Tentang")
    val icons = listOf(Icons.Outlined.Home, Icons.Outlined.CompassCalibration, Icons.Outlined.BookmarkBorder, Icons.Outlined.Info)
    val routes = listOf("home", "explore", "saved", "about")

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, item ->
            val targetRoute = routes[index]
            val isSelected = currentRoute == targetRoute

            // ---MENGHILANGKAN ANIMASI KLIK (RIPPLE) ---
            // Kita bungkus NavigationBarItem dengan konfigurasi Ripple = null
            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                NavigationBarItem(
                    icon = { Icon(icons[index], contentDescription = item) },
                    label = { Text(item) },
                    selected = isSelected,
                    colors = NavigationBarItemDefaults.colors(
                        // 1. Warna saat AKTIF
                        selectedIconColor = Primary,
                        selectedTextColor = Primary,

                        // 2. Hilangkan Background Shape (Transparan)
                        indicatorColor = Color.Transparent,

                        // 3. Warna saat MATI
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    onClick = {
                        navController.navigate(targetRoute) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}