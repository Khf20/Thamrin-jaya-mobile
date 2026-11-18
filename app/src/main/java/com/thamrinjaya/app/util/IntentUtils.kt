package com.thamrinjaya.app.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.net.URLEncoder

object IntentUtils {

    fun openWhatsApp(context: Context, phoneNumber: String, message: String = "") {
        try {
            val cleanNumber = phoneNumber.replace(Regex("[^0-9]"), "")
            val encodedMsg = URLEncoder.encode(message, "UTF-8")
            val url = if (encodedMsg.isNotEmpty()) {
                "https://wa.me/$cleanNumber?text=$encodedMsg"
            } else {
                "https://wa.me/$cleanNumber"
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Tidak dapat membuka WhatsApp", Toast.LENGTH_SHORT).show()
        }
    }

    fun openDialer(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Tidak dapat membuka Telepon", Toast.LENGTH_SHORT).show()
        }
    }

    fun openEmail(context: Context, email: String, subject: String = "", body: String = "") {
        try {
            val encodedSubject = Uri.encode(subject)
            val encodedBody = Uri.encode(body)
            val uri = Uri.parse("mailto:$email?subject=$encodedSubject&body=$encodedBody")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Tidak dapat membuka Email", Toast.LENGTH_SHORT).show()
        }
    }

    fun openMapLocation(context: Context, query: String, label: String = "") {
        try {
            val encodedQuery = Uri.encode(query)
            val uri = Uri.parse("geo:0,0?q=$encodedQuery")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to Google Maps Web
            try {
                val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(query)}")
                val webIntent = Intent(Intent.ACTION_VIEW, webUri)
                context.startActivity(webIntent)
            } catch (err: Exception) {
                Toast.makeText(context, "Tidak dapat membuka Peta", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openWebUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Tidak dapat membuka Browser", Toast.LENGTH_SHORT).show()
        }
    }
}
