package com.thamrinjaya.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thamrinjaya.app.theme.*

@Composable
fun BottomContactBar(
    onWhatsAppClick: () -> Unit,
    onPhoneClick: () -> Unit,
    onMapClick: () -> Unit,
    accentColor: Color = Slate900,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
            .border(width = 1.dp, color = Slate200)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        color = White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // WhatsApp Button
            Box(
                modifier = Modifier
                    .weight(1.8f)
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(accentColor)
                    .clickable { onWhatsAppClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "💬 WhatsApp Sales",
                    color = White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Phone Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(8.dp))
                    .background(White)
                    .clickable { onPhoneClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📞 Telp",
                    color = Slate800,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Location Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(8.dp))
                    .background(White)
                    .clickable { onMapClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📍 Lokasi",
                    color = Slate800,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
