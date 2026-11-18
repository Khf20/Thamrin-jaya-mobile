package com.thamrinjaya.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thamrinjaya.app.R
import com.thamrinjaya.app.data.ThamrinJayaData
import com.thamrinjaya.app.model.Company
import com.thamrinjaya.app.theme.*
import com.thamrinjaya.app.util.IntentUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitSwitcherSheet(
    onDismissRequest: () -> Unit,
    onSelectHome: () -> Unit,
    onSelectAdmin: () -> Unit = {},
    onSelectCompany: (Company, tab: Int) -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = PageBackground,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header — sama seperti header layar, tombol X di kotak subtle
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_thamrin_jaya),
                            contentDescription = "Thamrin Jaya Group",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Slate200, RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Thamrin Jaya Group",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Slate900
                            )
                            Text(
                                text = "PORTAL HOLDING & UNIT BISNIS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = GroupAccent,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Slate100)
                            .clickable { onDismissRequest() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = Slate900,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Section: Unit Bisnis
            item {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = "UNIT BISNIS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GroupAccent,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Pilih unit",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate900,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            items(ThamrinJayaData.companies) { company ->
                SwitcherRow(
                    icon = Icons.Default.Factory,
                    iconBoxColor = company.lightColor,
                    iconTint = company.accentColor,
                    title = company.name,
                    subtitle = company.category,
                    onClick = {
                        onSelectCompany(company, 0)
                        onDismissRequest()
                    }
                )
            }

            // Baris Admin — dipertahankan dari versi sebelumnya (tidak ada di
            // desain switcher v2) karena hosting admin sedang dikerjakan.
            item {
                SwitcherRow(
                    icon = Icons.Default.Settings,
                    iconBoxColor = GroupAccentSubtle,
                    iconTint = GroupAccent,
                    title = "Admin CMS",
                    subtitle = "Kelola isi aplikasi",
                    onClick = {
                        onSelectAdmin()
                        onDismissRequest()
                    }
                )
            }

            // Section: Kontak Grup
            item {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    Text(
                        text = "KONTAK GRUP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GroupAccent,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Butuh bantuan memilih unit?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate900,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            item {
                SwitcherRow(
                    icon = Icons.Default.Chat,
                    iconBoxColor = GroupAccentSubtle,
                    iconTint = GroupAccent,
                    title = "WhatsApp Hotline",
                    subtitle = "Chat langsung dengan tim grup",
                    onClick = {
                        IntentUtils.openWhatsApp(
                            context,
                            ThamrinJayaData.HOLDING_WHATSAPP,
                            "Halo Thamrin Jaya Group, saya ingin informasi mengenai unit bisnis."
                        )
                    }
                )
            }

            item {
                SwitcherRow(
                    icon = Icons.Default.LocationOn,
                    iconBoxColor = GroupAccentSubtle,
                    iconTint = GroupAccent,
                    title = "Peta lokasi grup",
                    subtitle = "Buka di Google Maps",
                    onClick = {
                        IntentUtils.openMapLocation(context, ThamrinJayaData.HOLDING_ADDRESS)
                    }
                )
            }

            item {
                SwitcherRow(
                    icon = Icons.Default.Home,
                    iconBoxColor = GroupAccentSubtle,
                    iconTint = GroupAccent,
                    title = "Beranda grup",
                    subtitle = "Kembali ke halaman utama",
                    onClick = {
                        onSelectHome()
                        onDismissRequest()
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun SwitcherRow(
    icon: ImageVector,
    iconBoxColor: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Slate200, RoundedCornerShape(16.dp))
            .background(White)
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBoxColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Slate900,
                maxLines = 1
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Slate600
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Slate400,
            modifier = Modifier.size(20.dp)
        )
    }
}
