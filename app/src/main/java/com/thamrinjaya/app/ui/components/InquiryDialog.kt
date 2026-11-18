package com.thamrinjaya.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.thamrinjaya.app.theme.*
import com.thamrinjaya.app.util.IntentUtils

@Composable
fun InquiryDialog(
    companyName: String,
    whatsappNumber: String,
    initialProduct: String = "",
    accentColor: Color = Slate900,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var companyOrBrand by remember { mutableStateOf("") }
    var productAndQty by remember { mutableStateOf(initialProduct) }
    var notes by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "FORMULIR PERMINTAAN PENAWARAN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate500,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = companyName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "Kirimkan rincian kebutuhan Anda untuk penawaran resmi via WhatsApp tim sales:",
                    fontSize = 12.sp,
                    color = Slate600,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                // Full Name
                InquiryTextField(
                    label = "Nama Lengkap *",
                    placeholder = "Contoh: Budi Santoso",
                    value = fullName,
                    onValueChange = { fullName = it }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Phone
                InquiryTextField(
                    label = "Nomor WhatsApp / HP *",
                    placeholder = "0812-XXXX-XXXX",
                    value = phone,
                    keyboardType = KeyboardType.Phone,
                    onValueChange = { phone = it }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Brand
                InquiryTextField(
                    label = "Nama Perusahaan / Merek",
                    placeholder = "PT / CV / Brand Anda",
                    value = companyOrBrand,
                    onValueChange = { companyOrBrand = it }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Product & Qty
                InquiryTextField(
                    label = "Kebutuhan Produk & Perkiraan Jumlah",
                    placeholder = "Contoh: Folding Carton 5.000 pcs",
                    value = productAndQty,
                    onValueChange = { productAndQty = it }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Notes
                InquiryTextField(
                    label = "Pesan Tambahan / Catatan Spesifikasi",
                    placeholder = "Jelaskan dimensi, jenis bahan, atau waktu pengiriman yang diinginkan...",
                    value = notes,
                    singleLine = false,
                    minLines = 3,
                    onValueChange = { notes = it }
                )

                if (showError) {
                    Text(
                        text = "Mohon lengkapi Nama dan Nomor WhatsApp",
                        color = Color.Red,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Batal", color = Slate700)
                    }

                    Button(
                        onClick = {
                            if (fullName.isBlank() || phone.isBlank()) {
                                showError = true
                            } else {
                                val message = buildString {
                                    appendLine("Halo $companyName, saya ingin meminta penawaran resmi:")
                                    appendLine("• Nama: $fullName")
                                    appendLine("• Kontak/WA: $phone")
                                    if (companyOrBrand.isNotBlank()) appendLine("• Perusahaan/Brand: $companyOrBrand")
                                    if (productAndQty.isNotBlank()) appendLine("• Produk & Jumlah: $productAndQty")
                                    if (notes.isNotBlank()) appendLine("• Catatan: $notes")
                                    appendLine("\nMohon informasi penawaran dan spesifikasi. Terima kasih.")
                                }
                                IntentUtils.openWhatsApp(context, whatsappNumber, message)
                                onDismissRequest()
                            }
                        },
                        modifier = Modifier.weight(1.5f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        Text(text = "Kirim WA ↗", color = White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun InquiryTextField(
    label: String,
    placeholder: String,
    value: String,
    singleLine: Boolean = true,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Slate800,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, fontSize = 12.sp, color = Slate400) },
            singleLine = singleLine,
            minLines = minLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Slate800,
                unfocusedBorderColor = Slate200,
                focusedContainerColor = White,
                unfocusedContainerColor = Slate50
            )
        )
    }
}
