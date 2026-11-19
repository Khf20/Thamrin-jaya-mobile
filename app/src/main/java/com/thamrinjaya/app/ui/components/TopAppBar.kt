package com.thamrinjaya.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thamrinjaya.app.theme.*

@Composable
fun MainTopBar(
    title: String,
    subtitle: String,
    logoResId: Int? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onLogoClick: (() -> Unit)? = null,
    subtitleColor: Color = Slate500,
    onMenuClick: () -> Unit
) {
    // Diberi ruang untuk status bar HP (karena aplikasi edge-to-edge), supaya logo
    // dan judul tidak tertutup jam/ikon baterai. Latar putih tetap dicat sampai ke atas.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(65.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .then(
                    if (onLogoClick != null) {
                        Modifier.clickable { onLogoClick() }
                    } else {
                        Modifier
                    }
                )
        ) {
            if (showBackButton) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Slate100)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Slate900,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }

            if (logoResId != null) {
                Image(
                    painter = painterResource(id = logoResId),
                    contentDescription = title,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Slate900),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "TJ",
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Slate900,
                    maxLines = 1
                )
                Text(
                    text = subtitle.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = subtitleColor,
                    letterSpacing = 1.sp,
                    maxLines = 1
                )
            }
        }

        // Tombol menu v2: kotak abu lembut radius 12, ikon Menu saja tanpa teks.
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Slate100)
                .clickable { onMenuClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu Switcher",
                modifier = Modifier.size(20.dp),
                tint = Slate900
            )
        }
    }
}
