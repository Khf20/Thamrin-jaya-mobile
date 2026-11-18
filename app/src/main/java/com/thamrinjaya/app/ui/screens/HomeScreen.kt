package com.thamrinjaya.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.thamrinjaya.app.ui.components.MainTopBar
import com.thamrinjaya.app.util.IntentUtils

@Composable
fun HomeScreen(
    onNavigateToCompany: (Company, tab: Int) -> Unit,
    onNavigateToAdmin: () -> Unit,
    onOpenMenu: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            MainTopBar(
                title = ThamrinJayaData.HOLDING_NAME,
                subtitle = "Portal Utama Penghubung",
                logoResId = R.drawable.logo_thamrin_jaya,
                onLogoClick = onNavigateToAdmin,
                onMenuClick = onOpenMenu
            )
        },
        containerColor = Slate50
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Hero Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White)
                        .padding(20.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .width(14.dp)
                                    .height(3.dp)
                                    .background(Slate900)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = ThamrinJayaData.HOLDING_SUBTITLE,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate700,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = ThamrinJayaData.HOLDING_SLOGAN,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900,
                            lineHeight = 28.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = ThamrinJayaData.HOLDING_OVERVIEW,
                            fontSize = 13.sp,
                            color = Slate600,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Slate900)
                                    .clickable {
                                        IntentUtils.openWhatsApp(
                                            context,
                                            ThamrinJayaData.HOLDING_WHATSAPP,
                                            "Halo Thamrin Jaya Group, saya ingin informasi seputar holding dan unit bisnis."
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "💬 WhatsApp Hotline",
                                    color = White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Slate300, RoundedCornerShape(8.dp))
                                    .background(White)
                                    .clickable {
                                        IntentUtils.openMapLocation(context, ThamrinJayaData.HOLDING_ADDRESS)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "📍 Peta Lokasi Group",
                                    color = Slate800,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // Section 1: Pintasan Fitur Utama
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                    Text(
                        text = "PINTASAN FITUR UTAMA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate500,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Eksplorasi Transparansi Layanan & Operasional:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = Slate700
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Fitur 1: Katalog & Spesifikasi
                    FeatureShortcutCard(
                        icon = "📦",
                        tag = "FITUR 01 • SPESIFIKASI & MOQ",
                        title = "Katalog Produk & Spesifikasi Transparan",
                        desc = "Buka rincian bahan material, gramatur, toleransi dimensi, minimal order (MOQ), dan lead time tanpa biaya tersembunyi.",
                        action = "Buka Halaman Produk & Spesifikasi ↗",
                        onClick = {
                            val defaultCo = ThamrinJayaData.companies.first()
                            onNavigateToCompany(defaultCo, 1)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Fitur 2: Alur Proses & QC
                    FeatureShortcutCard(
                        icon = "⚙️",
                        tag = "FITUR 02 • STANDAR MUTU & SLA",
                        title = "Alur Proses Produksi & Transparansi QC",
                        desc = "Tinjau alur 5 tahapan kerja mulai dari konsultasi, dummy proofing, cetak offset, die-cut hingga checklist jaminan mutu kelayakan.",
                        action = "Buka Halaman Alur Proses & QC ↗",
                        onClick = {
                            val defaultCo = ThamrinJayaData.companies.first()
                            onNavigateToCompany(defaultCo, 2)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Fitur 3: Kontak & Lokasi
                    FeatureShortcutCard(
                        icon = "📍",
                        tag = "FITUR 03 • SALURAN LANGSUNG & PETA",
                        title = "Kontak Resmi, WhatsApp & Lokasi Pabrik",
                        desc = "Akses kontak langsung WhatsApp sales respon cepat (<15 menit), telepon kantor, alamat fisik pabrik dan rute Google Maps GPS.",
                        action = "Buka Halaman Kontak & Lokasi Pabrik ↗",
                        onClick = {
                            val defaultCo = ThamrinJayaData.companies.first()
                            onNavigateToCompany(defaultCo, 3)
                        }
                    )
                }
            }

            // Section 2: Direktori 6 Profil Perusahaan
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "DIREKTORI 6 PROFIL PERUSAHAAN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate500,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Pilih unit bisnis untuk membuka profil & layanan spesifik:",
                        fontSize = 13.sp,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            items(ThamrinJayaData.companies) { company ->
                CompanyDirectoryCard(
                    company = company,
                    onOpenProfile = { onNavigateToCompany(company, 0) },
                    onWhatsApp = {
                        IntentUtils.openWhatsApp(
                            context,
                            company.whatsapp,
                            "Halo ${company.name}, saya ingin konsultasi mengenai layanan ${company.category}."
                        )
                    },
                    onMap = {
                        IntentUtils.openMapLocation(context, company.address)
                    },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            // Section 3: Sinergi Ekosistem
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                        .background(White)
                        .padding(18.dp)
                ) {
                    Column {
                        Text(
                            text = "SINERGI & EKOSISTEM THAMRIN JAYA GROUP",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate500,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "06 Unit Bisnis Terpadu • 01 Semangat Kolaborasi",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900,
                            modifier = Modifier.padding(top = 4.dp, bottom = 6.dp)
                        )
                        Text(
                            text = "Menghubungkan rantai pasok industri dari bahan baku, produksi kemasan, hingga mesin industri rokok dalam satu holding.",
                            fontSize = 12.sp,
                            color = Slate600,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Section 4: Kantor Pusat & Info
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Slate900)
                        .padding(18.dp)
                ) {
                    Column {
                        Text(
                            text = "KANTOR PUSAT & LAYANAN TERPADU:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate400,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = ThamrinJayaData.HOLDING_ADDRESS,
                            fontSize = 12.sp,
                            color = White,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "📞 Telp: ${ThamrinJayaData.HOLDING_PHONE} | ✉️ ${ThamrinJayaData.HOLDING_EMAIL}",
                            fontSize = 12.sp,
                            color = Slate300
                        )
                        Text(
                            text = ThamrinJayaData.HOLDING_HOURS,
                            fontSize = 11.sp,
                            color = Slate400,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureShortcutCard(
    icon: String,
    tag: String,
    title: String,
    desc: String,
    action: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Slate200, RoundedCornerShape(12.dp))
            .background(White)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = icon, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = tag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate500,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = desc,
                fontSize = 12.sp,
                color = Slate600,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = action,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Slate900
            )
        }
    }
}

@Composable
private fun CompanyDirectoryCard(
    company: Company,
    onOpenProfile: () -> Unit,
    onWhatsApp: () -> Unit,
    onMap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Slate200, RoundedCornerShape(12.dp))
            .background(White)
            .padding(16.dp)
    ) {
        Column {
            if (company.heroImageResId != null) {
                Image(
                    painter = painterResource(id = company.heroImageResId),
                    contentDescription = company.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = company.logoResId),
                    contentDescription = company.name,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = company.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "${company.category} • ${company.slogan}",
                        fontSize = 11.sp,
                        color = Slate500,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = company.description,
                fontSize = 12.sp,
                color = Slate600,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "📍 ${company.area}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Slate500
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1.5f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(company.accentColor)
                        .clickable { onOpenProfile() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Buka Profil Unit ↗",
                        color = White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(6.dp))
                        .background(White)
                        .clickable { onWhatsApp() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💬 WA",
                        color = Slate800,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(0.9f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(6.dp))
                        .background(White)
                        .clickable { onMap() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📍 Peta",
                        color = Slate800,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
