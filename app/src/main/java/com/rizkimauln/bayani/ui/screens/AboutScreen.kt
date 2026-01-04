package com.rizkimauln.bayani.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizkimauln.bayani.R
import com.rizkimauln.bayani.ui.theme.*

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgBody)
            .padding(24.dp), // Padding layar sedikit diperbesar
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- LOGO ---
        Image(
            painter = painterResource(id = R.drawable.logo_bayani),
            contentDescription = "Logo Aplikasi",
            modifier = Modifier.size(110.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- JUDUL ---
        Text(
            text = "Bayani Al-Qur'an Sains",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextMain,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- BADGE VERSI ---
        Surface(
            color = Color(0xFFE5E7EB),
            shape = CircleShape
        ) {
            Text(
                text = "Versi 1.0.0",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextMuted,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- DESKRIPSI ---
        Text(
            text = "Aplikasi interaktif yang menghubungkan ayat-ayat suci Al-Quran dengan fakta sains untuk memperluas wawasan dan keimanan.",
            textAlign = TextAlign.Center,
            color = TextMain.copy(alpha = 0.8f),
            fontSize = 14.sp,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- KARTU INFORMASI ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Efek bayangan
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                AboutInfoRow(label = "Pengembang", value = "Bayani Team")
                Divider(color = BgBody, thickness = 1.dp, modifier = Modifier.padding(vertical = 12.dp))

                AboutInfoRow(label = "Basis Data", value = "Riset Internal")
                Divider(color = BgBody, thickness = 1.dp, modifier = Modifier.padding(vertical = 12.dp))

                AboutInfoRow(label = "Kontak", value = "appbayani@gmail.com")
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Mendorong copyright ke bawah (opsional)

        // --- COPYRIGHT ---
        Text(
            text = "© 2025 Bayani App. All rights reserved.",
            fontSize = 11.sp,
            color = TextMuted,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}

@Composable
fun AboutInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = TextMuted,
            fontSize = 14.sp
        )
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            color = TextMain,
            fontSize = 14.sp
        )
    }
}