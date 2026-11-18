package com.thamrinjaya.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
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
import com.thamrinjaya.app.data.ThamrinJayaData
import com.thamrinjaya.app.model.Company
import com.thamrinjaya.app.model.Product
import com.thamrinjaya.app.theme.*
import com.thamrinjaya.app.ui.components.BottomContactBar
import com.thamrinjaya.app.ui.components.InquiryDialog
import com.thamrinjaya.app.ui.components.MainTopBar
import com.thamrinjaya.app.util.IntentUtils

@Composable
fun CompanyProfileScreen(
    companyId: String,
    initialTab: Int = 0,
    onBackClick: () -> Unit,
    onNavigateToAdmin: () -> Unit = {},
    onOpenMenu: () -> Unit
) {
    val context = LocalContext.current
    val company = remember(companyId) {
        ThamrinJayaData.getCompany(companyId) ?: ThamrinJayaData.companies.first()
    }

    var selectedTab by remember(initialTab) { mutableIntStateOf(initialTab.coerceIn(0, 3)) }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var inquiryProduct by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            MainTopBar(
                title = company.name,
                subtitle = when (selectedTab) {
                    0 -> "PROFIL PERUSAHAAN"
                    1 -> "KATALOG PRODUK"
                    2 -> "ALUR PROSES & QC"
                    else -> "KONTAK & LOKASI"
                },
                logoResId = company.logoResId,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoClick = onNavigateToAdmin,
                subtitleColor = company.accentColor,
                onMenuClick = onOpenMenu
            )
        },
        bottomBar = {
            BottomContactBar(
                accentColor = company.accentColor,
                onWhatsAppClick = {
                    IntentUtils.openWhatsApp(
                        context,
                        company.whatsapp,
                        "Halo ${company.name}, saya ingin konsultasi produk dan layanan."
                    )
                },
                onPhoneClick = {
                    IntentUtils.openDialer(context, company.phone)
                },
                onMapClick = {
                    IntentUtils.openMapLocation(context, company.address)
                }
            )
        },
        containerColor = PageBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Tab Navigation v2 — wadah pill abu lembut, tab aktif pill putih
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50))
                        .background(Slate100)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val tabs = listOf("Tentang", "Katalog", "Proses", "Kontak")
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTab == index
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .then(
                                    if (isSelected) {
                                        Modifier.shadow(2.dp, RoundedCornerShape(50))
                                    } else {
                                        Modifier
                                    }
                                )
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) White else Color.Transparent)
                                .clickable { selectedTab = index }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                color = if (isSelected) Slate900 else Slate600,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            // Tab Content
            when (selectedTab) {
                0 -> AboutTabContent(
                    company = company,
                    onGoToTab = { tab -> selectedTab = tab },
                    onDownloadProfile = { inquiryProduct = "" }
                )
                1 -> ProductsTabContent(
                    company = company,
                    selectedCategory = selectedCategory,
                    onSelectCategory = { selectedCategory = it },
                    onRequestInquiry = { product -> inquiryProduct = product.name }
                )
                2 -> ProcessTabContent(company = company)
                3 -> ContactTabContent(
                    company = company,
                    onOpenInquiryDialog = { inquiryProduct = "" }
                )
            }
        }
    }

    inquiryProduct?.let { prod ->
        InquiryDialog(
            companyName = company.name,
            whatsappNumber = company.whatsapp,
            initialProduct = prod,
            accentColor = company.accentColor,
            onDismissRequest = { inquiryProduct = null }
        )
    }
}

@Composable
private fun AboutTabContent(
    company: Company,
    onGoToTab: (Int) -> Unit,
    onDownloadProfile: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Hero unit — kartu aksen-subtle
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(company.lightColor)
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = company.category.uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = company.accentColor,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = company.slogan,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate900,
                        lineHeight = 26.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                    Text(
                        text = company.description,
                        fontSize = 14.sp,
                        color = Slate700,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }

        // Kartu gambar unggulan
        val featuredImage = company.serviceImageResId ?: company.heroImageResId
        if (featuredImage != null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(16.dp))
                        .background(White)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = featuredImage),
                            contentDescription = company.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "PRODUK UNGGULAN",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = company.accentColor,
                                letterSpacing = 1.2.sp
                            )
                            Text(
                                text = company.products.take(3).joinToString(" • ") { it.name },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Slate900,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Tentang Kami
        item {
            Column {
                SectionHeader(
                    overline = "TENTANG KAMI",
                    overlineColor = company.accentColor,
                    title = "Mengenal ${company.name}"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = company.commitment,
                    fontSize = 14.sp,
                    color = Slate700,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(16.dp))
                        .background(White)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Sejarah & komitmen",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Text(
                            text = company.history,
                            fontSize = 14.sp,
                            color = Slate600,
                            lineHeight = 20.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        // 4 Pilar Keunggulan — satu kartu, pilar bernomor, pemisah tipis
        item {
            Column {
                SectionHeader(
                    overline = "4 PILAR KEUNGGULAN",
                    overlineColor = company.accentColor,
                    title = "Standar mutu kami"
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(16.dp))
                        .background(White)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Column {
                        company.pillars.forEachIndexed { index, pillar ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(company.lightColor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = pillar.number,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = company.accentColor
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = pillar.title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate900
                                    )
                                    Text(
                                        text = pillar.description,
                                        fontSize = 13.sp,
                                        color = Slate600,
                                        lineHeight = 19.sp,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                            if (index < company.pillars.lastIndex) {
                                HorizontalDivider(color = Slate200)
                            }
                        }
                    }
                }
            }
        }

        // Trust per unit — slot dashed sesuai desain (data klien asli belum ada)
        item {
            Column {
                SectionHeader(
                    overline = "DIPERCAYA OLEH",
                    overlineColor = company.accentColor,
                    title = "Klien & sertifikasi"
                )
                Spacer(modifier = Modifier.height(12.dp))
                val slotLabels = listOf("Logo klien", "Sertifikasi", "Logo klien", "Sertifikasi", "Logo klien", "Sertifikasi")
                slotLabels.chunked(3).forEach { rowLabels ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowLabels.forEach { label ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(72.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(White)
                                    .dashedBorder(Slate300, 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Slate500
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        // CTA bawah Tentang
        item {
            Column {
                Text(
                    text = "Lihat produk & alur kerja",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate900
                )
                Text(
                    text = "Tinjau spesifikasi transparan dan alur kerja langkah-demi-langkah pada tab khusus.",
                    fontSize = 14.sp,
                    color = Slate600,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Slate900)
                        .clickable { onGoToTab(1) },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Lihat Katalog Produk",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = White,
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
                        .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                        .background(White)
                        .clickable { onGoToTab(2) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Lihat Alur Proses & QC",
                        color = Slate900,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDownloadProfile() },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        tint = LinkBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Unduh Company Profile (PDF)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LinkBlue
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductsTabContent(
    company: Company,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    onRequestInquiry: (Product) -> Unit
) {
    val filteredProducts = remember(selectedCategory, company) {
        if (selectedCategory == "Semua") company.products
        else company.products.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner sukses v2
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SuccessBg)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = SuccessText,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Spesifikasi & MOQ transparan",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = SuccessText
                        )
                        Text(
                            text = "Penawaran langsung dari tim sales, tanpa perantara.",
                            fontSize = 13.sp,
                            color = Slate700,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }

        // Chip filter v2 — dipilih: hitam; belum: putih border
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                company.categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .height(44.dp)
                            .clip(RoundedCornerShape(50))
                            .border(
                                1.dp,
                                if (isSelected) Slate900 else Slate200,
                                RoundedCornerShape(50)
                            )
                            .background(if (isSelected) Slate900 else White)
                            .clickable { onSelectCategory(cat) }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cat,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) White else Slate900
                        )
                    }
                }
            }
        }

        // Hitungan hasil + hapus filter (hanya bila filter aktif)
        if (selectedCategory != "Semua") {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredProducts.size} produk • $selectedCategory",
                        fontSize = 12.sp,
                        color = Slate600
                    )
                    Row(
                        modifier = Modifier.clickable { onSelectCategory("Semua") },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = LinkBlue,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Hapus filter",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LinkBlue
                        )
                    }
                }
            }
        }

        // Product Cards
        items(filteredProducts) { product ->
            ProductDetailCard(
                product = product,
                company = company,
                onRequestInquiry = { onRequestInquiry(product) }
            )
        }
    }
}

@Composable
private fun ProductDetailCard(
    product: Product,
    company: Company,
    onRequestInquiry: () -> Unit
) {
    var specsExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Slate200, RoundedCornerShape(16.dp))
            .background(White)
    ) {
        Column {
            if (product.imageResId != null) {
                Image(
                    painter = painterResource(id = product.imageResId),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(company.lightColor)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(company.lightColor)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = product.badge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = company.accentColor,
                        letterSpacing = 0.8.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate900
                )

                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = Slate600,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Chip MOQ & lead time
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Slate100)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "MOQ ${product.moq}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Slate900
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Slate100)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Lead time ${product.leadTime}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Slate900
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Akordeon spesifikasi teknis
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { specsExpanded = !specsExpanded }
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Spesifikasi teknis",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Icon(
                        imageVector = if (specsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (specsExpanded) "Tutup spesifikasi" else "Buka spesifikasi",
                        tint = Slate700,
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (specsExpanded) {
                    Column(
                        modifier = Modifier.padding(top = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SpecRow(label = "Material", value = product.material)
                        SpecRow(label = "Dimensi", value = product.dimensions)
                        SpecRow(label = "Finishing", value = product.finishing)
                        SpecRow(label = "MOQ", value = product.moq)
                        SpecRow(label = "Lead time", value = product.leadTime)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Slate900)
                        .clickable { onRequestInquiry() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Minta Penawaran",
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRequestInquiry() },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        tint = LinkBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Unduh spec sheet (PDF)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LinkBlue
                    )
                }
            }
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Slate500
        )
        Text(
            text = value,
            fontSize = 13.sp,
            color = Slate900
        )
    }
}

@Composable
private fun ProcessTabContent(company: Company) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Kartu ringkasan lead time — aksen-subtle
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(company.lightColor)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Estimasi total",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = company.accentColor,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = company.totalLeadTime,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        item {
            SectionHeader(
                overline = "ALUR PROSES & QC",
                overlineColor = company.accentColor,
                title = "Tahapan kerja kami",
                subtitle = "Setiap langkah dilengkapi checklist jaminan mutu dan SLA yang jelas."
            )
        }

        items(company.steps) { step ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(16.dp))
                    .background(White)
                    .padding(16.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(company.accentColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = step.stepNumber,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = step.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Chip SLA
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Slate100)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Slate700,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "SLA: ${step.sla}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate900
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = step.description,
                        fontSize = 14.sp,
                        color = Slate600,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Checklist QC dengan ikon check
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        step.qcChecklist.forEach { item ->
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = company.accentColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = item,
                                    fontSize = 13.sp,
                                    color = Slate800,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // CTA WhatsApp bawah
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(WhatsAppGreen)
                    .clickable {
                        val msg = "Halo ${company.name}, saya ingin konsultasikan rencana produksi & lead time."
                        IntentUtils.openWhatsApp(context, company.whatsapp, msg)
                    }
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Diskusikan Rencana Produksi Anda",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                        Text(
                            text = "Konsultasikan lead time dan kebutuhan spesifikasi dengan tim teknis kami via WhatsApp.",
                            fontSize = 13.sp,
                            color = White.copy(alpha = 0.85f),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactTabContent(
    company: Company,
    onOpenInquiryDialog: () -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                overline = "KONTAK & LOKASI",
                overlineColor = company.accentColor,
                title = "Mulai dari sebuah percakapan",
                subtitle = "Pesan Anda langsung ditangani oleh tim representatif ${company.name}."
            )
        }

        // List rows kontak — gaya Kantor Pusat, tint aksen unit
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(16.dp))
                    .background(White)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Column {
                    ContactRow(
                        icon = Icons.Default.Chat,
                        company = company,
                        label = "WhatsApp Sales",
                        value = "+${company.whatsapp}",
                        note = "Respon cepat < 15 menit • Hari kerja",
                        showChevron = true,
                        onClick = {
                            IntentUtils.openWhatsApp(
                                context,
                                company.whatsapp,
                                "Halo ${company.name}, saya ingin konsultasi layanan."
                            )
                        }
                    )
                    HorizontalDivider(color = Slate200)
                    ContactRow(
                        icon = Icons.Default.Phone,
                        company = company,
                        label = "Telepon Kantor",
                        value = company.phone,
                        note = null,
                        showChevron = true,
                        onClick = { IntentUtils.openDialer(context, company.phone) }
                    )
                    HorizontalDivider(color = Slate200)
                    ContactRow(
                        icon = Icons.Default.Email,
                        company = company,
                        label = "Email Resmi",
                        value = company.email,
                        note = null,
                        showChevron = true,
                        onClick = { IntentUtils.openEmail(context, company.email, "Inquiry ${company.name}") }
                    )
                    HorizontalDivider(color = Slate200)
                    ContactRow(
                        icon = Icons.Default.LocationOn,
                        company = company,
                        label = "Alamat Pabrik & Kantor",
                        value = company.address,
                        note = null,
                        showChevron = true,
                        onClick = { IntentUtils.openMapLocation(context, company.address) }
                    )
                    HorizontalDivider(color = Slate200)
                    ContactRow(
                        icon = Icons.Default.Schedule,
                        company = company,
                        label = "Jam Operasional",
                        value = company.operationalHours,
                        note = null,
                        showChevron = false,
                        onClick = null
                    )
                }
            }
        }

        // Peta
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(16.dp))
                    .background(White)
                    .padding(16.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(company.lightColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = company.accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Peta Lokasi",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                            Text(
                                text = company.mapQuery,
                                fontSize = 12.sp,
                                color = Slate600
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Slate900)
                                .clickable { IntentUtils.openMapLocation(context, company.address) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Buka Maps", color = White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                                .background(White)
                                .clickable { IntentUtils.openMapLocation(context, company.address) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Petunjuk Arah", color = Slate900, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // Pintu masuk formulir penawaran (dialog tetap dipakai)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(16.dp))
                    .background(White)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Formulir Permintaan Penawaran",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate900
                    )
                    Text(
                        text = "Isi rincian kebutuhan Anda untuk respon resmi cepat dari tim sales.",
                        fontSize = 14.sp,
                        color = Slate600,
                        modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Slate900)
                            .clickable { onOpenInquiryDialog() },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Buka Formulir",
                                color = White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Text(
                        text = "Respon < 15 menit pada hari kerja.",
                        fontSize = 12.sp,
                        color = Slate500,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactRow(
    icon: ImageVector,
    company: Company,
    label: String,
    value: String,
    note: String?,
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
                .background(company.lightColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = company.accentColor,
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
            if (note != null) {
                Text(
                    text = note,
                    fontSize = 12.sp,
                    color = Slate500
                )
            }
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
