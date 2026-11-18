package com.thamrinjaya.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
        containerColor = White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
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
                                color = Slate500,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Slate200, RoundedCornerShape(8.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = Slate700,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Shortcut to Home Holding & Admin CMS
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Slate900)
                            .clickable {
                                onSelectHome()
                                onDismissRequest()
                            }
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "🏛️ Beranda Utama Holding",
                                    color = White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Eksplorasi sinergi 6 unit bisnis Thamrin Jaya",
                                    color = Slate300,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = "Buka →",
                                color = White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Slate300, RoundedCornerShape(10.dp))
                            .background(Slate50)
                            .clickable {
                                onSelectAdmin()
                                onDismissRequest()
                            }
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "⚙️ Dashboard Admin CMS (Kelola Isi)",
                                    color = Slate900,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Ubah profil, produk, spek & alur proses langsung",
                                    color = Slate600,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = "Masuk →",
                                color = Slate900,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Section 1: Business Units
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "PROFIL 6 PERUSAHAAN (UNIT BISNIS)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate500,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Pilih profil unit bisnis untuk eksplorasi spesifik:",
                    fontSize = 13.sp,
                    color = Slate700
                )
            }

            items(ThamrinJayaData.companies) { company ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                        .background(White)
                        .clickable {
                            onSelectCompany(company, 0)
                            onDismissRequest()
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = company.logoResId),
                            contentDescription = company.name,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Slate100, RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = company.name,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = Slate900,
                                maxLines = 1
                            )
                            Text(
                                text = company.category,
                                fontSize = 11.sp,
                                color = Slate500
                            )
                        }
                    }

                    Text(
                        text = "Buka →",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = company.accentColor
                    )
                }
            }

            // Section 2: Feature Shortcuts
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "HALAMAN FITUR PORTAL",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate500,
                    letterSpacing = 0.5.sp
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    PortalFeatureItem(
                        icon = "📦",
                        title = "Katalog Produk & Spesifikasi Transparan",
                        onClick = {
                            val defaultCo = ThamrinJayaData.companies.first()
                            onSelectCompany(defaultCo, 1)
                            onDismissRequest()
                        }
                    )
                    PortalFeatureItem(
                        icon = "⚙️",
                        title = "Alur Proses Produksi & SLA Quality Check",
                        onClick = {
                            val defaultCo = ThamrinJayaData.companies.first()
                            onSelectCompany(defaultCo, 2)
                            onDismissRequest()
                        }
                    )
                    PortalFeatureItem(
                        icon = "📍",
                        title = "Kontak Resmi, WhatsApp & Lokasi Pabrik",
                        onClick = {
                            val defaultCo = ThamrinJayaData.companies.first()
                            onSelectCompany(defaultCo, 3)
                            onDismissRequest()
                        }
                    )
                }
            }

            // Bottom Quick Contact
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                            .background(Slate50)
                            .clickable {
                                IntentUtils.openWhatsApp(
                                    context,
                                    ThamrinJayaData.HOLDING_WHATSAPP,
                                    "Halo Thamrin Jaya Group, saya ingin informasi mengenai unit bisnis."
                                )
                            }
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "WhatsApp Hotline",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate800
                            )
                            Text(
                                text = "Chat Langsung ↗",
                                fontSize = 11.sp,
                                color = WhatsAppDark,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                            .background(Slate50)
                            .clickable {
                                IntentUtils.openMapLocation(context, ThamrinJayaData.HOLDING_ADDRESS)
                            }
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "Peta Lokasi Group",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate800
                            )
                            Text(
                                text = "Buka Google Maps ↗",
                                fontSize = 11.sp,
                                color = Slate600,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun PortalFeatureItem(
    icon: String,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Slate200, RoundedCornerShape(8.dp))
            .background(White)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = icon, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Slate800
            )
        }
        Text(
            text = "›",
            fontSize = 18.sp,
            color = Slate400,
            fontWeight = FontWeight.Bold
        )
    }
}
