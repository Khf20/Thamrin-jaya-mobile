package com.thamrinjaya.app.data

import com.thamrinjaya.app.model.Company
import com.thamrinjaya.app.model.Product
import com.thamrinjaya.app.model.ProductionStep
import com.thamrinjaya.app.model.QualityPillar
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Penghubung aplikasi <-> admin.
 *
 * Mengambil katalog terbaru dari API server admin, lalu menggabungkannya
 * dengan data tertanam: ISI TEKS (nama, spek, harga/MOQ, SLA, checklist QC,
 * kontak) berasal dari server, sedangkan DATA VISUAL (logo, foto, warna
 * aksen) tetap dari aplikasi karena server tidak menyimpannya.
 *
 * Bila pengambilan gagal — tidak ada internet, server gratis Render sedang
 * tidur, atau respons tidak wajar — fungsi ini diam saja dan aplikasi terus
 * memakai data tertanam. Tidak pernah ada layar kosong gara-gara sinkronisasi.
 */
object CatalogSync {

    private const val API_URL = "https://thamrin-jaya-admin-mobile.onrender.com/api/catalog"

    suspend fun syncFromServer() {
        // Server gratis bisa butuh waktu untuk bangun, jadi dicoba sampai
        // 3 kali dengan jeda. Berhasil sekali saja sudah cukup.
        repeat(3) { attempt ->
            if (attemptSync()) return
            if (attempt < 2) delay(4_000)
        }
    }

    /**
     * Cek berkala selama aplikasi terbuka: setiap beberapa menit mengambil
     * katalog lagi dari server, supaya perubahan di admin tampil sendiri
     * tanpa aplikasi harus ditutup-buka. Berhenti otomatis saat aplikasi
     * ditutup (coroutine-nya dibatalkan).
     */
    suspend fun syncPeriodically(intervalMillis: Long = 180_000L) {
        while (true) {
            delay(intervalMillis)
            attemptSync()
        }
    }

    /** Satu kali percobaan ambil + gabung. True bila data server diterapkan. */
    private suspend fun attemptSync(): Boolean {
        val merged = try {
            fetchAndMerge()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            null
        }
        if (merged == null) return false
        ThamrinJayaData.applyCatalog(merged)
        return true
    }

    private suspend fun fetchAndMerge(): List<Company>? = withContext(Dispatchers.IO) {
        val connection = URL(API_URL).openConnection() as HttpURLConnection
        connection.connectTimeout = 15_000
        connection.readTimeout = 45_000
        connection.setRequestProperty("Accept", "application/json")
        if (connection.responseCode != 200) return@withContext null
        val body = connection.inputStream.bufferedReader().use { it.readText() }
        merge(JSONObject(body))
    }

    private fun merge(root: JSONObject): List<Company>? {
        val array = root.optJSONArray("companies") ?: return null
        if (array.length() == 0) return null

        val bundledById = ThamrinJayaData.bundled().associateBy { it.id }
        val result = mutableListOf<Company>()
        for (i in 0 until array.length()) {
            val json = array.getJSONObject(i)
            // Unit yang belum dikenal aplikasi dilewati dulu: belum ada logo
            // dan warna aksen lokal untuknya. Keenam unit yang ada selalu cocok.
            val base = bundledById[json.optString("id")] ?: continue
            result += mergeCompany(base, json)
        }
        return result.ifEmpty { null }
    }

    private fun mergeCompany(base: Company, json: JSONObject): Company {
        val products = json.optJSONArray("products")?.let { mergeProducts(base, it) }
            ?: base.products
        val categories = json.optJSONArray("categories")?.toStringList()
            ?.takeIf { it.isNotEmpty() } ?: base.categories

        return base.copy(
            number = json.text("number", base.number),
            name = json.text("name", base.name),
            shortName = json.text("short_name", base.shortName),
            category = json.text("category", base.category),
            slogan = json.text("slogan", base.slogan),
            description = json.text("description", base.description),
            history = json.text("history", base.history),
            commitment = json.text("commitment", base.commitment),
            address = json.text("address", base.address),
            area = json.text("area", base.area),
            phone = json.text("phone", base.phone),
            whatsapp = json.text("whatsapp", base.whatsapp),
            email = json.text("email", base.email),
            operationalHours = json.text("operating_hours", base.operationalHours),
            mapQuery = json.text("map_query", base.mapQuery),
            totalLeadTime = json.text("total_lead_time", base.totalLeadTime),
            categories = categories,
            pillars = json.optJSONArray("pillars")?.let { mergePillars(it) } ?: base.pillars,
            products = products,
            steps = json.optJSONArray("steps")?.let { mergeSteps(it) } ?: base.steps
        )
    }

    private fun mergeProducts(base: Company, array: JSONArray): List<Product> {
        // Foto produk dicocokkan dari data tertanam berdasarkan nama produk.
        val imageByName = base.products.associate { it.name.trim().lowercase() to it.imageResId }
        val result = mutableListOf<Product>()
        for (i in 0 until array.length()) {
            val json = array.getJSONObject(i)
            val name = json.text("name", "")
            if (name.isBlank()) continue
            val baseProduct = base.products.find { it.name.equals(name, ignoreCase = true) }
            result += Product(
                id = json.text("id", baseProduct?.id ?: name),
                name = name,
                category = json.text("category", baseProduct?.category ?: ""),
                badge = json.text("badge", baseProduct?.badge ?: ""),
                description = json.text("description", baseProduct?.description ?: ""),
                material = json.text("material", baseProduct?.material ?: ""),
                dimensions = json.text("dimension", baseProduct?.dimensions ?: ""),
                finishing = json.text("finishing", baseProduct?.finishing ?: ""),
                moq = json.text("moq", baseProduct?.moq ?: ""),
                leadTime = json.text("lead_time", baseProduct?.leadTime ?: ""),
                imageResId = imageByName[name.trim().lowercase()]
            )
        }
        return result.ifEmpty { base.products }
    }

    private fun mergeSteps(array: JSONArray): List<ProductionStep> {
        val result = mutableListOf<ProductionStep>()
        for (i in 0 until array.length()) {
            val json = array.getJSONObject(i)
            result += ProductionStep(
                stepNumber = json.text("step_number", "%02d".format(i + 1)),
                title = json.text("title", ""),
                description = json.text("description", ""),
                sla = json.text("sla", ""),
                qcChecklist = json.optJSONArray("qc_checklist")?.toStringList() ?: emptyList()
            )
        }
        return result
    }

    private fun mergePillars(array: JSONArray): List<QualityPillar> {
        val result = mutableListOf<QualityPillar>()
        for (i in 0 until array.length()) {
            val json = array.getJSONObject(i)
            result += QualityPillar(
                number = json.text("number", ""),
                title = json.text("title", ""),
                description = json.text("description", "")
            )
        }
        return result
    }

    /** Ambil teks dari JSON; kosong/null di server berarti pakai nilai cadangan. */
    private fun JSONObject.text(key: String, fallback: String): String {
        if (isNull(key)) return fallback
        return optString(key, "").ifBlank { fallback }
    }

    private fun JSONArray.toStringList(): List<String> =
        (0 until length()).map { optString(it) }.filter { it.isNotBlank() }
}
