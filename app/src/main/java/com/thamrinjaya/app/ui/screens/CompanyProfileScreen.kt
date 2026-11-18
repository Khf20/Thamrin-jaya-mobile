package com.thamrinjaya.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.thamrinjaya.app.data.ThamrinJayaData
import com.thamrinjaya.app.model.Company
import com.thamrinjaya.app.model.Product
import com.thamrinjaya.app.model.ProductionStep
import com.thamrinjaya.app.model.QualityPillar
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
                    0 -> "PROFIL UNIT BISNIS"
                    1 -> "KATALOG PRODUK & SPESIFIKASI"
                    2 -> "ALUR PROSES PRODUKSI & SLA QC"
                    else -> "KONTAK RESMI & LOKASI PABRIK"
                },
                logoResId = company.logoResId,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoClick = onNavigateToAdmin,
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
        containerColor = Slate50
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Quick Info Banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Slate900)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "💬 WA: +${company.whatsapp}",
                    fontSize = 11.sp,
                    color = White,
                    modifier = Modifier.clickable {
                        IntentUtils.openWhatsApp(context, company.whatsapp)
                    }
                )
                Text(
                    text = "📍 ${company.area}",
                    fontSize = 11.sp,
                    color = Slate300,
                    modifier = Modifier.clickable {
                        IntentUtils.openMapLocation(context, company.address)
                    }
                )
            }

            // Tab Navigation Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Slate100)
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val tabs = listOf("Tentang", "Katalog", "Alur Proses", "Kontak")
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTab == index
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) White else Color.Transparent)
                                .clickable { selectedTab = index }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Slate900 else Slate500,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            // Tab Content
            when (selectedTab) {
                0 -> AboutTabContent(company = company, onGoToCatalog = { selectedTab = 1 })
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
    onGoToCatalog: () -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                    .background(White)
            ) {
                Column {
                    if (company.heroImageResId != null) {
                        Image(
                            painter = painterResource(id = company.heroImageResId),
                            contentDescription = company.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(modifier = Modifier.padding(18.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(company.lightColor)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = company.category.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = company.accentColor,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = company.slogan,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = company.description,
                            fontSize = 13.sp,
                            color = Slate600,
                            lineHeight = 19.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(company.accentColor)
                                    .clickable {
                                        IntentUtils.openWhatsApp(
                                            context,
                                            company.whatsapp,
                                            "Halo ${company.name}, saya ingin konsultasi produk."
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "💬 WA Sales",
                                    color = White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1.2f)
                                    .height(38.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .border(1.dp, Slate200, RoundedCornerShape(6.dp))
                                    .background(White)
                                    .clickable {
                                        IntentUtils.openMapLocation(context, company.address)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "📍 Petunjuk Lokasi",
                                    color = Slate800,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Get To Know Us
        item {
            Column {
                Text(
                    text = "01 / GET TO KNOW US",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate500,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Mengenal ${company.name}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                        .background(White)
                ) {
                    Column {
                        if (company.serviceImageResId != null) {
                            Image(
                                painter = painterResource(id = company.serviceImageResId),
                                contentDescription = "Layanan ${company.name}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = company.commitment,
                                fontSize = 12.sp,
                                color = Slate700,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Sejarah & Komitmen:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate900
                            )
                            Text(
                                text = company.history,
                                fontSize = 12.sp,
                                color = Slate600,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // Section 4 Pilar Keunggulan
        item {
            Column {
                Text(
                    text = "4 PILAR KEUNGGULAN KAMI",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate500,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Transparansi standar mutu pekerjaan kami:",
                    fontSize = 13.sp,
                    color = Slate700
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        items(company.pillars) { pillar ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                    .background(White)
                    .padding(14.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(company.lightColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pillar.number,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = company.accentColor
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = pillar.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = pillar.description,
                        fontSize = 12.sp,
                        color = Slate600,
                        lineHeight = 17.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        // CTA to Catalog
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                    .background(company.lightColor)
                    .clickable { onGoToCatalog() }
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Produk & Alur Proses Siap Ditinjau",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "Tinjau spesifikasi transparan dan alur kerja langkah-demi-langkah pada tab fitur khusus.",
                        fontSize = 12.sp,
                        color = Slate700,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                    Text(
                        text = "Lihat Fitur Katalog & Proses →",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = company.accentColor
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
    val context = LocalContext.current
    val filteredProducts = remember(selectedCategory, company) {
        if (selectedCategory == "Semua") company.products
        else company.products.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                    .background(White)
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = "✨ Transparansi Spesifikasi & Minimal Order (MOQ)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "Spesifikasi terbuka dan penawaran langsung via WhatsApp tanpa perantara.",
                        fontSize = 11.sp,
                        color = Slate600,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        // Category Filter
        item {
            Column {
                Text(
                    text = "FILTER KATEGORI PRODUK:",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate500,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    company.categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) company.accentColor else Slate200,
                                    RoundedCornerShape(6.dp)
                                )
                                .background(if (isSelected) company.accentColor else White)
                                .clickable { onSelectCategory(cat) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = cat,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) White else Slate700
                            )
                        }
                    }
                }
            }
        }

        // Product Cards
        items(filteredProducts) { product ->
            ProductDetailCard(
                product = product,
                company = company,
                onAskWa = {
                    val msg = "Halo ${company.name}, saya ingin tanya spesifikasi produk: ${product.name}."
                    IntentUtils.openWhatsApp(context, company.whatsapp, msg)
                },
                onRequestInquiry = { onRequestInquiry(product) }
            )
        }
    }
}

@Composable
private fun ProductDetailCard(
    product: Product,
    company: Company,
    onAskWa: () -> Unit,
    onRequestInquiry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Slate200, RoundedCornerShape(12.dp))
            .background(White)
            .padding(16.dp)
    ) {
        Column {
            if (product.imageResId != null) {
                Image(
                    painter = painterResource(id = product.imageResId),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Slate100, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(company.lightColor)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = product.badge,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = company.accentColor,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = product.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )

            Text(
                text = product.description,
                fontSize = 12.sp,
                color = Slate600,
                lineHeight = 17.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )

            // Transparent Specs Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(8.dp))
                    .background(Slate50)
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "SPESIFIKASI TEKNIS TRANSPARAN:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700,
                        letterSpacing = 0.5.sp
                    )

                    SpecRow(label = "Material Bahan", value = product.material)
                    SpecRow(label = "Dimensi & Bentuk", value = product.dimensions)
                    SpecRow(label = "Teknik Finishing", value = product.finishing)
                    SpecRow(label = "Minimal Order (MOQ)", value = product.moq)
                    SpecRow(label = "Estimasi Lead Time", value = product.leadTime)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1.3f)
                        .height(38.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .border(1.dp, Slate300, RoundedCornerShape(6.dp))
                        .background(White)
                        .clickable { onAskWa() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💬 Tanya via WA",
                        color = Slate800,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1.1f)
                        .height(38.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(company.accentColor)
                        .clickable { onRequestInquiry() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Minta Penawaran ↗",
                        color = White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
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
            text = "• $label:",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Slate600
        )
        Text(
            text = value,
            fontSize = 11.sp,
            color = Slate900,
            modifier = Modifier.padding(start = 8.dp)
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
        // Lead Time Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                    .background(White)
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = "ESTIMASI LEAD TIME TOTAL:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate500,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = company.totalLeadTime,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        item {
            Column {
                Text(
                    text = "5 TAHAP PROSES KERJA & JAMINAN MUTU",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate700
                )
                Text(
                    text = "Setiap langkah dilengkapi checklist transparansi dan SLA pasti:",
                    fontSize = 12.sp,
                    color = Slate600
                )
            }
        }

        items(company.steps) { step ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                    .background(White)
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(company.lightColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = step.stepNumber,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = company.accentColor
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = step.title,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Slate100)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "⏱ SLA: ${step.sla}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = Slate700
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = step.description,
                        fontSize = 12.sp,
                        color = Slate600,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // QC Checklist
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Slate50)
                            .border(1.dp, Slate200, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "CHECKLIST JAMINAN MUTU (QC TRANSPARAN):",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate600,
                                letterSpacing = 0.5.sp
                            )
                            step.qcChecklist.forEach { item ->
                                Text(
                                    text = "✓ $item",
                                    fontSize = 11.sp,
                                    color = Slate800
                                )
                            }
                        }
                    }
                }
            }
        }

        // Discussion Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                    .background(company.lightColor)
                    .clickable {
                        val msg = "Halo ${company.name}, saya ingin konsultasikan rencana produksi & lead time."
                        IntentUtils.openWhatsApp(context, company.whatsapp, msg)
                    }
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "💬 Diskusikan Rencana Produksi Anda",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "Konsultasikan lead time dan kebutuhan spesifikasi dengan tim teknis kami via WhatsApp.",
                        fontSize = 12.sp,
                        color = Slate700,
                        modifier = Modifier.padding(top = 4.dp)
                    )
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
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "SALURAN HUBUNGAN LANGSUNG",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate500,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Mulai Dari Sebuah Percakapan.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "Pesan Anda langsung ditangani oleh tim representatif ${company.name}.",
                    fontSize = 12.sp,
                    color = Slate600,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // WhatsApp Main Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                    .background(White)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "WHATSAPP SALES & KONSULTASI",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate500,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "+${company.whatsapp}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Respon cepat < 15 menit • Hari kerja",
                        fontSize = 11.sp,
                        color = Slate500
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(WhatsAppDark)
                            .clickable {
                                IntentUtils.openWhatsApp(
                                    context,
                                    company.whatsapp,
                                    "Halo ${company.name}, saya ingin konsultasi layanan."
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "💬 Chat WhatsApp Sales Sekarang ↗",
                            color = White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Phone & Email Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                        .background(White)
                        .clickable { IntentUtils.openDialer(context, company.phone) }
                        .padding(14.dp)
                ) {
                    Column {
                        Text(text = "TELEPON KANTOR", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Slate500)
                        Text(text = company.phone, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Slate900, modifier = Modifier.padding(vertical = 2.dp))
                        Text(text = "Panggil ↗", fontSize = 11.sp, color = company.accentColor, fontWeight = FontWeight.Medium)
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                        .background(White)
                        .clickable { IntentUtils.openEmail(context, company.email, "Inquiry ${company.name}") }
                        .padding(14.dp)
                ) {
                    Column {
                        Text(text = "EMAIL RESMI", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Slate500)
                        Text(text = company.email, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate900, modifier = Modifier.padding(vertical = 2.dp), maxLines = 1)
                        Text(text = "Kirim Email ↗", fontSize = 11.sp, color = company.accentColor, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        // Address & Hours
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                    .background(White)
                    .padding(16.dp)
            ) {
                Column {
                    Text(text = "ALAMAT PABRIK & KANTOR:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate500, letterSpacing = 0.5.sp)
                    Text(text = company.address, fontSize = 12.sp, color = Slate800, lineHeight = 17.sp, modifier = Modifier.padding(top = 4.dp, bottom = 10.dp))

                    Text(text = "JAM OPERASIONAL:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate500, letterSpacing = 0.5.sp)
                    Text(text = company.operationalHours, fontSize = 11.sp, color = Slate700, modifier = Modifier.padding(top = 2.dp))
                }
            }
        }

        // Map Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Slate200, RoundedCornerShape(12.dp))
                    .background(White)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "📍", fontSize = 32.sp)
                    Text(text = "PABRIK ${company.name.uppercase()}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate900, modifier = Modifier.padding(top = 6.dp))
                    Text(text = company.mapQuery, fontSize = 11.sp, color = Slate600, modifier = Modifier.padding(bottom = 12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Slate900)
                                .clickable { IntentUtils.openMapLocation(context, company.address) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🗺 Buka Maps ↗", color = White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .border(1.dp, Slate300, RoundedCornerShape(6.dp))
                                .background(White)
                                .clickable { IntentUtils.openMapLocation(context, company.address) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🧭 Petunjuk Arah", color = Slate800, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // Inquiry Button CTA
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(company.accentColor)
                    .clickable { onOpenInquiryDialog() }
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "FORMULIR PERMINTAAN PENAWARAN (INQUIRY)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Isi rincian kebutuhan Anda untuk respon resmi cepat",
                        fontSize = 12.sp,
                        color = White.copy(alpha = 0.85f),
                        modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(White)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Buka Formulir Inquiry ↗",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = company.accentColor
                        )
                    }
                }
            }
        }
    }
}
