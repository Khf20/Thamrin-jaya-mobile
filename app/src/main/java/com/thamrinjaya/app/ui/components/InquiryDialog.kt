package com.thamrinjaya.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    // Dipertahankan untuk kompatibilitas pemanggil; di v2 tombol kirim selalu hitam.
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
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate500,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = companyName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate900,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "Kirimkan rincian kebutuhan Anda untuk penawaran resmi via WhatsApp tim sales:",
                    fontSize = 14.sp,
                    color = Slate600,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                // Full Name
                InquiryTextField(
                    label = "Nama Lengkap *",
                    placeholder = "Contoh: Budi Santoso",
                    value = fullName,
                    isError = showError && fullName.isBlank(),
                    onValueChange = { fullName = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Phone
                InquiryTextField(
                    label = "Nomor WhatsApp / HP *",
                    placeholder = "0812-XXXX-XXXX",
                    value = phone,
                    keyboardType = KeyboardType.Phone,
                    isError = showError && phone.isBlank(),
                    onValueChange = { phone = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Brand
                InquiryTextField(
                    label = "Nama Perusahaan / Merek",
                    placeholder = "PT / CV / Brand Anda",
                    value = companyOrBrand,
                    onValueChange = { companyOrBrand = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Product & Qty
                InquiryTextField(
                    label = "Kebutuhan Produk & Perkiraan Jumlah",
                    placeholder = "Contoh: Folding Carton 5.000 pcs",
                    value = productAndQty,
                    onValueChange = { productAndQty = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

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
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = ErrorBg,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Text(
                            text = "Mohon lengkapi Nama dan Nomor WhatsApp",
                            color = ErrorColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismissRequest,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
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
                        modifier = Modifier
                            .weight(1.5f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Slate900)
                    ) {
                        Text(text = "Kirim via WhatsApp", color = White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }

                Text(
                    text = "Respon < 15 menit pada hari kerja.",
                    fontSize = 12.sp,
                    color = Slate500,
                    modifier = Modifier.padding(top = 12.dp)
                )
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
    isError: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isError) ErrorColor else Slate900,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, fontSize = 14.sp, color = Slate400) },
            singleLine = singleLine,
            minLines = minLines,
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Slate900,
                unfocusedBorderColor = Slate200,
                errorBorderColor = ErrorColor,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                errorContainerColor = ErrorBg
            )
        )
    }
}
