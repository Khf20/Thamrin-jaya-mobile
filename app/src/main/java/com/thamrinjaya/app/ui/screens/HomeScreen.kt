package com.thamrinjaya.app.ui.screens

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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
                subtitleColor = GroupAccent,
                onMenuClick = onOpenMenu
            )
        },
        containerColor = PageBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Hero v2 — kartu hitam
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Slate900)
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            text = "SATU GRUP. BANYAK KEMUNGKINAN.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = White,
                            letterSpacing = 1.2.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = ThamrinJayaData.HOLDING_SLOGAN,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = White,
                            lineHeight = 34.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = ThamrinJayaData.HOLDING_OVERVIEW,
                            fontSize = 14.sp,
                            color = Slate300,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Tombol "Lihat 6 Unit Bisnis" membuka menu switcher unit
                        // (aksi yang tersedia lewat callback yang sudah ada).
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(White)
                                .clickable { onOpenMenu() },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Lihat 6 Unit Bisnis",
                                    color = Slate900,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Slate900,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(White)
                                .clickable {
                                    IntentUtils.openMapLocation(context, ThamrinJayaData.HOLDING_ADDRESS)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = Slate900,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Peta Lokasi Grup",
                                    color = Slate900,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // Section: Trust — slot logo diisi logo 6 unit bisnis yang ada
            item {
                Column {
                    SectionHeader(
                        overline = "DIPERCAYA INDUSTRI",
                        overlineColor = GroupAccent,
                        title = "Klien & sertifikasi",
                        subtitle = "Logo klien utama dan sertifikasi mutu dari unit bisnis kami."
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ThamrinJayaData.companies.chunked(3).forEach { rowCompanies ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowCompanies.forEach { company ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(84.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(White)
                                        .dashedBorder(Slate300, 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Image(
                                            painter = painterResource(id = company.logoResId),
                                            contentDescription = company.name,
                                            modifier = Modifier.size(38.dp),
                                            contentScale = ContentScale.Fit
                                        )
                                        Text(
                                            text = company.shortName,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Slate600,
                                            modifier = Modifier.padding(top = 4.dp),
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                            repeat(3 - rowCompanies.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            // Section: Direktori 6 Unit Bisnis
            item {
                SectionHeader(
                    overline = "UNIT BISNIS",
                    overlineColor = GroupAccent,
                    title = "Enam unit bisnis kami",
                    subtitle = "Pilih unit bisnis untuk membuka profil & layanan spesifik:"
                )
            }

            items(ThamrinJayaData.companies) { company ->
                CompanyDirectoryCard(
                    company = company,
                    onOpenProfile = { onNavigateToCompany(company, 0) }
                )
            }

            // Section: Ekosistem
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(GroupAccentSubtle)
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            text = "EKOSISTEM TERPADU",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GroupAccent,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "6 unit bisnis, 1 ekosistem",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Slate900,
                            modifier = Modifier.padding(top = 4.dp, bottom = 6.dp)
                        )
                        Text(
                            text = "Menghubungkan rantai pasok industri dari bahan baku, produksi kemasan, hingga mesin industri rokok dalam satu holding.",
                            fontSize = 14.sp,
                            color = Slate700,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Section: Kantor Pusat
            item {
                Column {
                    SectionHeader(
                        overline = "KANTOR PUSAT",
                        overlineColor = GroupAccent,
                        title = "Pusat Bisnis Thamrin Jaya"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Slate200, RoundedCornerShape(16.dp))
                            .background(White)
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Column {
                            HoldingInfoRow(
                                icon = Icons.Default.LocationOn,
                                label = "Alamat",
                                value = ThamrinJayaData.HOLDING_ADDRESS,
                                showChevron = true,
                                onClick = {
                                    IntentUtils.openMapLocation(context, ThamrinJayaData.HOLDING_ADDRESS)
                                }
                            )
                            HorizontalDivider(color = Slate200)
                            HoldingInfoRow(
                                icon = Icons.Default.Phone,
                                label = "Telepon",
                                value = ThamrinJayaData.HOLDING_PHONE,
                                showChevron = true,
                                onClick = {
                                    IntentUtils.openDialer(context, ThamrinJayaData.HOLDING_PHONE)
                                }
                            )
                            HorizontalDivider(color = Slate200)
                            HoldingInfoRow(
                                icon = Icons.Default.Email,
                                label = "Email",
                                value = ThamrinJayaData.HOLDING_EMAIL,
                                showChevron = true,
                                onClick = {
                                    IntentUtils.openEmail(context, ThamrinJayaData.HOLDING_EMAIL, "Inquiry Thamrin Jaya Group")
                                }
                            )
                            HorizontalDivider(color = Slate200)
                            HoldingInfoRow(
                                icon = Icons.Default.Schedule,
                                label = "Jam operasional",
                                value = ThamrinJayaData.HOLDING_HOURS,
                                showChevron = false,
                                onClick = null
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    overline: String,
    overlineColor: Color,
    title: String,
    subtitle: String? = null
) {
    Column {
        Text(
            text = overline.uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = overlineColor,
            letterSpacing = 1.2.sp
        )
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Slate900,
            modifier = Modifier.padding(top = 2.dp)
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Slate600,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun CompanyDirectoryCard(
    company: Company,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Slate200, RoundedCornerShape(16.dp))
            .background(White)
            .padding(16.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = company.logoResId),
                    contentDescription = company.name,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = company.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = company.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = company.accentColor,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = company.description,
                fontSize = 14.sp,
                color = Slate600,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Slate500,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = company.area,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Slate500
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Slate100)
                    .clickable { onOpenProfile() },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Buka Profil Unit",
                        color = Slate900,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Slate900,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HoldingInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    showChevron: Boolean,
    onClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(GroupAccentSubtle),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GroupAccent,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Text(
                text = value,
                fontSize = 13.sp,
                color = Slate600
            )
        }
        if (showChevron) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Slate400,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

private fun Modifier.dashedBorder(color: Color, cornerRadius: Dp): Modifier = drawBehind {
    drawRoundRect(
        color = color,
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        style = Stroke(
            width = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
        )
    )
}
