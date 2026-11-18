package com.thamrinjaya.app.data

import com.thamrinjaya.app.R
import com.thamrinjaya.app.model.Company
import com.thamrinjaya.app.model.Product
import com.thamrinjaya.app.model.ProductionStep
import com.thamrinjaya.app.model.QualityPillar
import com.thamrinjaya.app.theme.*

object ThamrinJayaData {

    const val HOLDING_NAME = "Thamrin Jaya Group"
    const val HOLDING_SLOGAN = "Enam keahlian. Satu visi ke depan."
    const val HOLDING_SUBTITLE = "ONE GROUP. MORE POSSIBILITIES."
    const val HOLDING_OVERVIEW = "Dari kemasan presisi hingga teknologi mesin produksi. Menghubungkan seluruh kebutuhan industri dalam satu ekosistem terpadu."
    const val HOLDING_ADDRESS = "Pusat Bisnis Thamrin Jaya, Jl. Thamrin No. 88, Semarang, Jawa Tengah 50134"
    const val HOLDING_PHONE = "024-7654-3200"
    const val HOLDING_WHATSAPP = "6281234567890"
    const val HOLDING_EMAIL = "info@thamrinjayagroup.com"
    const val HOLDING_HOURS = "Senin – Jumat: 08.00 – 17.00 WIB"

    private val defaultPillars = listOf(
        QualityPillar(
            number = "01",
            title = "Kualitas",
            description = "Perhatian pada detail dan pemeriksaan hasil bertingkat di setiap tahap pekerjaan produksi."
        ),
        QualityPillar(
            number = "02",
            title = "Harga Transparan",
            description = "Penawaran yang disusun objektif sesuai material, dimensi, dan skala pesanan tanpa biaya tersembunyi."
        ),
        QualityPillar(
            number = "03",
            title = "Ketepatan Waktu",
            description = "Perencanaan tahapan kerja dengan komunikasi jadwal dan kepastian lead time terukur."
        ),
        QualityPillar(
            number = "04",
            title = "Fasilitas Produksi",
            description = "Peralatan modern terintegrasi dengan presisi tinggi untuk menghasilkan standar mutu konsisten."
        )
    )

    val companies: List<Company> = listOf(
        Company(
            id = "globalindo",
            number = "01",
            name = "PT. Globalindo Thamrin Jaya",
            shortName = "Globalindo",
            category = "Offset & Packaging",
            slogan = "Kemasan yang memberi kesan.",
            description = "Solusi cetak offset dan kemasan untuk menerjemahkan identitas merek Anda menjadi pengalaman yang berkesan dan presisi.",
            history = "Berdiri sebagai bagian dari Thamrin Jaya Group untuk melayani cetak offset modern, folding carton, dan packaging custom bernilai tambah.",
            commitment = "Kami menghubungkan kebutuhan bisnis dengan pendekatan produksi yang terencana, komunikasi yang terbuka, dan perhatian pada setiap detail.",
            logoResId = R.drawable.globalindo,
            heroImageResId = R.drawable.hero_globalindo,
            serviceImageResId = R.drawable.service_globalindo,
            accentColor = GlobalindoAccent,
            lightColor = GlobalindoLight,
            address = "Jl. Industri Candi Raya No. 18, Kawasan Industri Candi, Ngaliyan, Kota Semarang, Jawa Tengah 50181",
            area = "Candi, Semarang",
            phone = "024-7654-3210",
            whatsapp = "6281234567890",
            email = "sales@globalindo.co",
            operationalHours = "Senin – Jumat: 08.00 – 17.00 | Sabtu: 08.00 – 14.00 WIB",
            mapQuery = "Kawasan Industri Candi Blok XI No. 18 Semarang",
            totalLeadTime = "7 – 14 Hari Kerja • Transparan & Terlacak",
            categories = listOf("Semua", "Folding Carton", "Paper Sleeve", "Custom Box"),
            pillars = defaultPillars,
            products = listOf(
                Product(
                    id = "gl-1",
                    name = "Folding Carton Box",
                    category = "Folding Carton",
                    badge = "OFFSET PACKAGING",
                    description = "Kemasan karton lipat berpresisi tinggi untuk produk makanan, farmasi, kosmetik, dan FMCG dengan visual menarik.",
                    material = "Ivory Board / Duplex Coated Food Grade (250 - 400 gsm)",
                    dimensions = "Custom presisi mengikuti cetak biru die-cut produk",
                    finishing = "Cetak Offset CMYK / Pantone, UV Varnish, Foil Stamping",
                    moq = "1.000 pcs (Penyesuaian skala ekonomis)",
                    leadTime = "7 – 10 hari kerja setelah approval dummy fisik",
                    imageResId = R.drawable.prod_gl_folding_carton
                ),
                Product(
                    id = "gl-2",
                    name = "Paper Sleeve Branding",
                    category = "Paper Sleeve",
                    badge = "SLEEVE PACKAGING",
                    description = "Selongsong kertas slip-on untuk branding wadah makanan, botol, kemasan frozen food, atau bundel apparel promosi.",
                    material = "Art Paper / Kraft Eco Paper (150 – 260 gsm)",
                    dimensions = "Menyesuaikan keliling kelopak wadah / lock interlock",
                    finishing = "Die-cut akurat, laminasi tahan uap/air dingin (water-resistant)",
                    moq = "500 pcs per varian desain",
                    leadTime = "5 – 7 hari kerja sejak approval",
                    imageResId = R.drawable.prod_gl_paper_sleeve
                ),
                Product(
                    id = "gl-3",
                    name = "Custom Exclusive Rigid Box",
                    category = "Custom Box",
                    badge = "PREMIUM PACKAGING",
                    description = "Kotak kemasan mewah bertipe hardbox dengan insert custom untuk hampers eksklusif, parfum, kado perusahaan, dan elektronik.",
                    material = "Greyboard tebal 2mm dilapisi Art Paper tercetak / Fancy Paper",
                    dimensions = "Kustom flap magnetik, model buku, atau penutup atas-bawah",
                    finishing = "Emboss/Deboss logo, Hot Stamping Emas, Foam/Satin Insert",
                    moq = "500 pcs (Dapat bertahap)",
                    leadTime = "10 – 14 hari kerja termasuk pembuatan sampel",
                    imageResId = R.drawable.prod_gl_custom_packaging
                )
            ),
            steps = listOf(
                ProductionStep(
                    stepNumber = "01",
                    title = "Internal Marketing & Konsultasi Spesifikasi",
                    description = "Diskusi kebutuhan jenis kemasan, rekomendasi gramatur material, kalkulasi penawaran harga transparan tanpa biaya tersembunyi.",
                    sla = "1 Hari Kerja",
                    qcChecklist = listOf(
                        "Quotation resmi dengan rincian biaya transparan",
                        "Ketersediaan stok material di gudang terverifikasi"
                    )
                ),
                ProductionStep(
                    stepNumber = "02",
                    title = "Desain Struktur & Proofing Cetak (Pre-Press)",
                    description = "Pemeriksaan format file grafis (pre-flight), pembuatan dummy fisik untuk uji dimensi lipatan, dan persetujuan warna sebelum cetak massal.",
                    sla = "2 Hari Kerja",
                    qcChecklist = listOf(
                        "Dummy fisik dan digital proof disetujui klien",
                        "Pembuatan plat cetak CTP presisi mikron"
                    )
                ),
                ProductionStep(
                    stepNumber = "03",
                    title = "Cetak Mesin Offset Presisi & Pelapisan Varnish",
                    description = "Proses produksi cetak di mesin Heidelberg berkecepatan tinggi dengan sensor kerapatan warna konsisten, dilanjutkan laminasi/varnish.",
                    sla = "3 – 5 Hari Kerja",
                    qcChecklist = listOf(
                        "Pemeriksaan densitas warna CMYK/Pantone setiap 500 lembar",
                        "Uji daya rekat tinta dan ketahanan gesek permukaan"
                    )
                ),
                ProductionStep(
                    stepNumber = "04",
                    title = "Finishing Pond Die-Cut, Lem & 100% QC Inspection",
                    description = "Pemotongan presisi menggunakan pisau pond akurat, pengeleman sisi dengan lem kuat, dan sortir visual bebas cacat.",
                    sla = "2 – 3 Hari Kerja",
                    qcChecklist = listOf(
                        "Inspeksi lipatan bebas retak dan potongan presisi",
                        "Cek kekuatan rekat lem dan bebas bercak oli/kotoran"
                    )
                ),
                ProductionStep(
                    stepNumber = "05",
                    title = "Pengemasan Rapi & Pengiriman Langsung ke Lokasi",
                    description = "Kemasan dibungkus plastik anti-lembab, dimasukkan kardus master berlabel, dan diantar ke gudang mitra tepat waktu.",
                    sla = "1 – 2 Hari Kerja",
                    qcChecklist = listOf(
                        "Surat jalan resmi dan packing list detail terlampir",
                        "Notifikasi status pengiriman langsung ke WhatsApp klien"
                    )
                )
            )
        ),
        Company(
            id = "multipack",
            number = "02",
            name = "PT. Multipack Thamrin Jaya",
            shortName = "Multipack",
            category = "Kardus & Karton",
            slogan = "Perlindungan di setiap perjalanan.",
            description = "Kemasan kardus dan karton bergelombang yang dirancang untuk penyimpanan, distribusi logistik, dan display produk.",
            history = "Didirikan untuk memenuhi kebutuhan pengemasan transportasi dan distribusi industri manufaktur nasional dengan standar ketahanan beban tinggi.",
            commitment = "Mendukung kebutuhan penyimpanan dan distribusi melalui kemasan kardus dan karton yang presisi dan kuat.",
            logoResId = R.drawable.multipack,
            heroImageResId = R.drawable.hero_multipack,
            serviceImageResId = R.drawable.service_multipack,
            accentColor = MultipackAccent,
            lightColor = MultipackLight,
            address = "Kawasan Industri Terboyo Blok E No. 9, Genuk, Kota Semarang, Jawa Tengah 50118",
            area = "Terboyo, Semarang",
            phone = "024-7654-3211",
            whatsapp = "6281234567891",
            email = "sales@multipack-tj.co.id",
            operationalHours = "Senin – Jumat: 08.00 – 17.00 WIB",
            mapQuery = "Kawasan Industri Terboyo Blok E No. 9 Semarang",
            totalLeadTime = "5 – 10 Hari Kerja",
            categories = listOf("Semua", "Corrugated Box", "Mailer Box", "Partisi Karton"),
            pillars = defaultPillars,
            products = listOf(
                Product(
                    id = "mp-1",
                    name = "Corrugated Master Box",
                    category = "Corrugated Box",
                    badge = "CARTON PACKAGING",
                    description = "Bahan Single/Double Wall (Flute B, C, E), Kraft tahan beban tumpukan tinggi.",
                    material = "Kraft / Medium Board Single Wall & Double Wall (Flute B, C, E, BC)",
                    dimensions = "Custom sesuai dimensi produk dan standar pallet logistik",
                    finishing = "Cetak Flexo 1-4 Warna, Slotter Creasing, Stitching/Glue",
                    moq = "500 pcs",
                    leadTime = "5 – 7 hari kerja",
                    imageResId = R.drawable.prod_mp_corrugated_box
                ),
                Product(
                    id = "mp-2",
                    name = "Mailer Box E-Commerce",
                    category = "Mailer Box",
                    badge = "RETAIL & E-COMMERCE",
                    description = "Die-cut kuncian presisi tanpa lakban, cocok untuk branding produk retail dan pengiriman online.",
                    material = "E-Flute Corrugated Board dengan lapisan White Kraft / Brown Kraft",
                    dimensions = "Custom kuncian model kuping (ear-lock mailer)",
                    finishing = "Sablon / Flexo printing, die-cut pond presisi",
                    moq = "300 pcs",
                    leadTime = "5 – 7 hari kerja",
                    imageResId = R.drawable.prod_mp_mailer_box
                ),
                Product(
                    id = "mp-3",
                    name = "Karton Partisi & Divider",
                    category = "Partisi Karton",
                    badge = "PROTECTIVE PACKAGING",
                    description = "Sekat karton pelindung botol, gelas, dan komponen industri rapuh agar tidak saling berbenturan.",
                    material = "Corrugated Board atau Solid Board tebal",
                    dimensions = "Custom interlock slotting sesuai jumlah cell isi",
                    finishing = "Slotting presisi mudah dirakit",
                    moq = "1.000 set",
                    leadTime = "4 – 6 hari kerja",
                    imageResId = R.drawable.prod_mp_karton_partisi
                )
            ),
            steps = listOf(
                ProductionStep(
                    stepNumber = "01",
                    title = "Konsultasi & Kalkulasi Beban (Bursting Test)",
                    description = "Penentuan jenis flute dan gramatur kraft sesuai berat barang dan kondisi tumpukan gudang.",
                    sla = "1 Hari Kerja",
                    qcChecklist = listOf("Perhitungan Edge Crush Test (ECT) & Box Compression Test (BCT)")
                ),
                ProductionStep(
                    stepNumber = "02",
                    title = "Pembuatan Desain Struktur & Pisau Pond",
                    description = "Pengujian sampel dummy kardus untuk memastikan kesesuaian dimensi produk klien.",
                    sla = "2 Hari Kerja",
                    qcChecklist = listOf("Uji pas dan kunci kuncian kardus")
                ),
                ProductionStep(
                    stepNumber = "03",
                    title = "Corrugating Sheet & Cetak Flexo",
                    description = "Pembentukan gelombang karton dan pencetakan informasi/logo perusahaan berkecepatan tinggi.",
                    sla = "2 – 3 Hari Kerja",
                    qcChecklist = listOf("Ketahanan daya rekat lem gelombang flute")
                ),
                ProductionStep(
                    stepNumber = "04",
                    title = "Slitting, Creasing & Pengeleman / Stitching",
                    description = "Pemotongan garis lipatan akurat dan penggabungan sambungan tepi kardus.",
                    sla = "2 Hari Kerja",
                    qcChecklist = listOf("Inspeksi kelurusan lipatan dan kekuatan kawat stitching")
                ),
                ProductionStep(
                    stepNumber = "05",
                    title = "Bundling & Distribusi Truk Pabrik",
                    description = "Pengepakan per 25-50 lembar dengan tali strapping pelindung, siap dikirim ke pabrik mitra.",
                    sla = "1 Hari Kerja",
                    qcChecklist = listOf("Surat jalan resmi dan verifikasi kuantitas")
                )
            )
        ),
        Company(
            id = "hte",
            number = "03",
            name = "PT. HTE Rotopack Indonesia",
            shortName = "HTE Rotopack",
            category = "Flexible Packaging",
            slogan = "Fleksibel dalam bentuk. Kuat dalam fungsi.",
            description = "Pilihan kemasan fleksibel barrier tinggi untuk aneka bentuk produk dengan ketahanan bocor dan visual memikat.",
            history = "Fasilitas modern dengan teknologi rotogravure dan flexo berkecepatan tinggi untuk melayani industri makanan, minuman, dan kemasan khusus.",
            commitment = "Mendukung perlindungan produk melalui pilihan kemasan fleksibel, material barrier, dan proses finishing sesuai kebutuhan.",
            logoResId = R.drawable.hterootopack,
            heroImageResId = R.drawable.hero_rotopack,
            serviceImageResId = R.drawable.service_rotopack,
            accentColor = HteAccent,
            lightColor = HteLight,
            address = "Jl. Raya Semarang-Demak KM 9.5, Sayung Industri, Kab. Demak, Jawa Tengah 59563",
            area = "Sayung, Demak",
            phone = "024-7654-3212",
            whatsapp = "6281234567892",
            email = "sales@hterotopack.co.id",
            operationalHours = "Senin – Jumat: 08.00 – 17.00 WIB",
            mapQuery = "Sayung Industri Semarang Demak KM 9.5",
            totalLeadTime = "10 – 15 Hari Kerja",
            categories = listOf("Semua", "Standing Pouch", "Roll Packaging", "Sachet"),
            pillars = defaultPillars,
            products = listOf(
                Product(
                    id = "hte-1",
                    name = "Standing Pouch Zipper",
                    category = "Standing Pouch",
                    badge = "BARRIER POUCH",
                    description = "Bahan laminated foil PET/ALU/LLDPE, proteksi oksigen dan kelembaban maksimal untuk makanan kering & snack.",
                    material = "Multi-layer Laminated (PET / VM-PET / LLDPE atau Alu Foil Food Grade)",
                    dimensions = "100g, 250g, 500g, 1kg dan custom ukuran",
                    finishing = "Rotogravure hingga 9 warna, Zipper lock, Tear notch, Round corner",
                    moq = "5.000 pcs",
                    leadTime = "10 – 12 hari kerja",
                    imageResId = R.drawable.prod_hte_standing_pouch
                ),
                Product(
                    id = "hte-2",
                    name = "Roll Packaging Otomatis",
                    category = "Roll Packaging",
                    badge = "AUTOMATED PACKAGING",
                    description = "Film roll multi-layer untuk mesin pengisi horizontal/vertical form-fill-seal (FFS) otomatis berkecepatan tinggi.",
                    material = "BOPP / LLDPE, PET / CPP, Nylon Foil Food Contact",
                    dimensions = "Lebar roll custom sesuai spesifikasi form tube mesin klien",
                    finishing = "Cetak Rotogravure presisi dengan sensor eye-mark akurat",
                    moq = "100 kg",
                    leadTime = "10 – 14 hari kerja",
                    imageResId = R.drawable.prod_hte_roll_packaging
                ),
                Product(
                    id = "hte-3",
                    name = "Sachet 3-Side Seal",
                    category = "Sachet",
                    badge = "SINGLE SERVE PACKAGING",
                    description = "Kemasan sachet kedap udara untuk kopi, bubuk minuman, bumbu racik instan, dan kosmetik.",
                    material = "Aluminium Foil Barrier Laminated",
                    dimensions = "Custom mulai dari 5x7 cm hingga 12x18 cm",
                    finishing = "V-Notch sobekan mudah, seal kuat anti bocor",
                    moq = "10.000 pcs",
                    leadTime = "8 – 10 hari kerja",
                    imageResId = R.drawable.prod_hte_sachet_packaging
                )
            ),
            steps = listOf(
                ProductionStep(
                    stepNumber = "01",
                    title = "Analisis Bahan & Uji Transmisi Gas (OTR/WVTR)",
                    description = "Memilih formula lapisan film plastik / aluminium foil yang pas untuk umur simpan produk klien.",
                    sla = "2 Hari Kerja",
                    qcChecklist = listOf("Verifikasi sertifikat food grade material")
                ),
                ProductionStep(
                    stepNumber = "02",
                    title = "Pembuatan Silinder Cetak Gravure (Engraving)",
                    description = "Ukiran silinder tembaga presisi tinggi untuk menghasilkan detail gambar visual tajam.",
                    sla = "3 Hari Kerja",
                    qcChecklist = listOf("Proofing warna cetak di media film uji")
                ),
                ProductionStep(
                    stepNumber = "03",
                    title = "Cetak Rotogravure & Dry Lamination",
                    description = "Proses cetak multi-warna berkecepatan tinggi dilanjutkan penempelan antar lapisan film.",
                    sla = "4 Hari Kerja",
                    qcChecklist = listOf("Uji daya rekat laminasi (peel strength test)")
                ),
                ProductionStep(
                    stepNumber = "04",
                    title = "Slitting Rewind & Bag Making (Converting)",
                    description = "Pembelahan gulungan roll sesuai lebar serta pembentukan pouch dengan sealing panas.",
                    sla = "3 Hari Kerja",
                    qcChecklist = listOf("Uji kebocoran udara bertekanan (vacuum leak test)")
                ),
                ProductionStep(
                    stepNumber = "05",
                    title = "Packaging Bebas Kontaminasi & Pengiriman",
                    description = "Pouch dibungkus plastik pelindung dalam box corrugated higienis.",
                    sla = "1 Hari Kerja",
                    qcChecklist = listOf("Laporan uji kualitas (COA) terlampir")
                )
            )
        ),
        Company(
            id = "maxtech",
            number = "04",
            name = "Maxtech Solution Indonesia",
            shortName = "Maxtech",
            category = "Cigarette Machinery",
            slogan = "Teknologi untuk langkah berikutnya.",
            description = "Solusi kebutuhan mesin dan komponen presisi bagi industri rokok, dari konsultasi teknik hingga dukungan purna jual.",
            history = "Didukung tim teknisi ahli permesinan presisi dengan pengalaman puluhan tahun dalam rekondisi dan pembuatan mesin industri tembakau.",
            commitment = "Mendukung kebutuhan teknologi produksi melalui konsultasi teknis, mesin, komponen presisi, dan layanan purna jual.",
            logoResId = R.drawable.maxtech,
            heroImageResId = R.drawable.hero_maxtech,
            serviceImageResId = R.drawable.service_maxtech,
            accentColor = MaxtechAccent,
            lightColor = MaxtechLight,
            address = "Kawasan Industri Wijayakusuma Blok B No. 4, Tugurejo, Semarang, Jawa Tengah 50152",
            area = "Wijayakusuma, Semarang",
            phone = "024-7654-3213",
            whatsapp = "6281234567893",
            email = "support@maxtech.co.id",
            operationalHours = "Senin – Jumat: 08.00 – 17.00 WIB",
            mapQuery = "Kawasan Industri Wijayakusuma Blok B No. 4 Semarang",
            totalLeadTime = "Sesuai Lingkup Kerja Mesin",
            categories = listOf("Semua", "Mesin Pembuat", "Spare Parts", "Retrofit"),
            pillars = defaultPillars,
            products = listOf(
                Product(
                    id = "mx-1",
                    name = "Mesin Cigarette Maker & Packer",
                    category = "Mesin Pembuat",
                    badge = "TOBACCO MACHINERY",
                    description = "Unit mesin pembuat dan pengemas rokok otomatis berkecepatan tinggi dengan sistem sensor elektronik terintegrasi.",
                    material = "Baja paduan tahan aus & stainless food grade",
                    dimensions = "Konfigurasi modular sesuai tata letak pabrik",
                    finishing = "Powder coating industri, proteksi korosi",
                    moq = "1 Unit",
                    leadTime = "30 – 60 hari kerja (termasuk instalasi)",
                    imageResId = R.drawable.prod_mx_mesin_produksi
                ),
                Product(
                    id = "mx-2",
                    name = "Spare Parts Presisi Mesin",
                    category = "Spare Parts",
                    badge = "PRECISION PARTS",
                    description = "Pisau potong, suction belt, drum, roller, dan gear berstandar toleransi tinggi ukuran mikron.",
                    material = "Tungsten Carbide, Hardened Tool Steel, Special Polymer",
                    dimensions = "Toleransi dimensi presisi hingga 0.005 mm",
                    finishing = "Grinding presisi, heat treatment pendinginan stabil",
                    moq = "Sesuai Part",
                    leadTime = "3 – 7 hari kerja",
                    imageResId = R.drawable.prod_mx_spare_parts
                ),
                Product(
                    id = "mx-3",
                    name = "Layanan Retrofit & Overhaul",
                    category = "Retrofit",
                    badge = "TECHNICAL SERVICE",
                    description = "Rekondisi performa mesin lama, instalasi kontrol PLC layar sentuh, dan technical on-site support.",
                    material = "Komponen elektronik Siemens / Omron standar industri",
                    dimensions = "Disesuaikan dengan tipe mesin yang ada",
                    finishing = "Kalibrasi mekanik dan uji running test pabrik",
                    moq = "Kontrak Servis",
                    leadTime = "14 – 21 hari kerja",
                    imageResId = R.drawable.prod_mx_dukungan_teknis
                )
            ),
            steps = listOf(
                ProductionStep(
                    stepNumber = "01",
                    title = "Survei Lapangan & Analisis Kebutuhan Mesin",
                    description = "Pemeriksaan langsung kondisi lini produksi dan target kapasitas output yang diinginkan.",
                    sla = "2 Hari Kerja",
                    qcChecklist = listOf("Audit teknis spesifikasi mesin")
                ),
                ProductionStep(
                    stepNumber = "02",
                    title = "Desain Teknik & Rekayasa Spare Part (CAD/CAM)",
                    description = "Pemodelan 3D dan kalkulasi tegangan mekanis komponen.",
                    sla = "3 Hari Kerja",
                    qcChecklist = listOf("Simulasi toleransi gerakan mesin")
                ),
                ProductionStep(
                    stepNumber = "03",
                    title = "Fabrikasi Mesin CNC & Machining Presisi",
                    description = "Pengerjaan bubut, milling, dan grinding menggunakan mesin CNC akurasi mikron.",
                    sla = "7 – 14 Hari Kerja",
                    qcChecklist = listOf("Pengukuran dimensi CMM (Coordinate Measuring Machine)")
                ),
                ProductionStep(
                    stepNumber = "04",
                    title = "Assembling & Factory Acceptance Test (FAT)",
                    description = "Perakitan mekanikal-elektrikal dan uji putaran kecepatan operasional di workshop.",
                    sla = "5 Hari Kerja",
                    qcChecklist = listOf("Uji kebisingan, getaran, dan keandalan sensor")
                ),
                ProductionStep(
                    stepNumber = "05",
                    title = "Instalasi On-Site & Commissioning",
                    description = "Pemasangan unit di pabrik klien serta pelatihan teknisi operator sampai produksi lancar.",
                    sla = "3 – 5 Hari Kerja",
                    qcChecklist = listOf("Berita acara serah terima & garansi resmi")
                )
            )
        ),
        Company(
            id = "sinarjaya",
            number = "05",
            name = "CV. Sinar Jaya",
            shortName = "Sinar Jaya",
            category = "Cigarette Materials",
            slogan = "Material tepat. Hasil yang konsisten.",
            description = "Pilihan material pendukung industri rokok untuk melengkapi kebutuhan proses produksi Anda dengan kestabilan mutu teruji.",
            history = "Berpengalaman sejak 2012 sebagai pemasok terpercaya material rokok bagi berbagai pabrik rokok skala menengah hingga besar.",
            commitment = "Mendukung kelancaran produksi melalui penyediaan material pendukung yang sesuai dengan kebutuhan proses industri.",
            logoResId = R.drawable.sinarjaya,
            heroImageResId = R.drawable.hero_sinarjaya,
            serviceImageResId = R.drawable.service_sinarjaya,
            accentColor = SinarJayaAccent,
            lightColor = SinarJayaLight,
            address = "Jl. Kaligawe Raya KM 5.2, Genuk, Kota Semarang, Jawa Tengah 50118",
            area = "Kaligawe, Semarang",
            phone = "024-7654-3214",
            whatsapp = "6281234567894",
            email = "sales@sinarjaya-tj.co.id",
            operationalHours = "Senin – Jumat: 08.00 – 17.00 WIB",
            mapQuery = "Jl. Kaligawe Raya KM 5.2 Genuk Semarang",
            totalLeadTime = "3 – 7 Hari Kerja",
            categories = listOf("Semua", "Cigarette Paper", "Tipping Paper", "Plug Wrap"),
            pillars = defaultPillars,
            products = listOf(
                Product(
                    id = "sj-1",
                    name = "Cigarette Paper Roll",
                    category = "Cigarette Paper",
                    badge = "TOBACCO PAPER",
                    description = "Porositas udara terkontrol, pembakaran stabil, dan abu putih bersih tanpa rasa mengganggu aroma tembakau.",
                    material = "Serat rami / flax alami food grade (24 – 32 gsm)",
                    dimensions = "Bobbin lebar 26.5 mm, 27 mm, panjang hingga 6.000 meter",
                    finishing = "Vergie garis halus / plain, pembakaran terkontrol",
                    moq = "100 bobbin",
                    leadTime = "3 – 5 hari kerja",
                    imageResId = R.drawable.prod_sj_cigarette_paper
                ),
                Product(
                    id = "sj-2",
                    name = "Tipping Paper (Laser Perforated)",
                    category = "Tipping Paper",
                    badge = "FILTER WRAP",
                    description = "Kertas gabus filter bermotif/polos dengan perforasi laser ventilasi udara untuk mengurangi kadar tar.",
                    material = "Kertas tipping khusus anti-lengket bibir (34 – 40 gsm)",
                    dimensions = "Bobbin lebar 34 mm, 36 mm, 40 mm, 50 mm",
                    finishing = "Cetak motif gabus/emas/putih, hot stamping, laser perforasi",
                    moq = "50 bobbin",
                    leadTime = "4 – 6 hari kerja",
                    imageResId = R.drawable.prod_sj_tipping_paper
                ),
                Product(
                    id = "sj-3",
                    name = "Plug Wrap Paper",
                    category = "Plug Wrap",
                    badge = "FILTER CORE",
                    description = "Kertas pembungkus filter tow berkekuatan tarik tinggi dan daya serap lem cepat untuk mesin perakitan filter.",
                    material = "Porous & Non-porous paper berporositas tinggi",
                    dimensions = "Bobbin standar mesin filter rod",
                    finishing = "Tensile strength tinggi tahan putus di mesin kencang",
                    moq = "50 bobbin",
                    leadTime = "3 – 5 hari kerja",
                    imageResId = R.drawable.prod_sj_plug_wrap
                )
            ),
            steps = listOf(
                ProductionStep(
                    stepNumber = "01",
                    title = "Konsultasi Spesifikasi & Karakter Pembakaran",
                    description = "Penyesuaian porositas Coresta unit (CU) dengan racikan tembakau klien.",
                    sla = "1 Hari Kerja",
                    qcChecklist = listOf("Verifikasi standar CU & berat gramatur")
                ),
                ProductionStep(
                    stepNumber = "02",
                    title = "Uji Laboratorium Sampel Bobbin",
                    description = "Pemeriksaan ketahanan tarik, opasitas, dan daya serap lem.",
                    sla = "1 Hari Kerja",
                    qcChecklist = listOf("Uji uji coba di mesin sampel")
                ),
                ProductionStep(
                    stepNumber = "03",
                    title = "Slitting Bobbin & Rewinding Presisi",
                    description = "Pemotongan gulungan jumbo menjadi ukuran bobbin dengan tepi gulungan rata tanpa serat.",
                    sla = "2 – 3 Hari Kerja",
                    qcChecklist = listOf("Cek diameter inti core dan kelurusan gulungan")
                ),
                ProductionStep(
                    stepNumber = "04",
                    title = "Pengepakan Kedap Udara & Pengiriman",
                    description = "Bobbin dibungkus plastik seal anti lembab dan dimasukkan box khusus agar tidak penyok.",
                    sla = "1 Hari Kerja",
                    qcChecklist = listOf("Pemberian label batch nomor produksi terverifikasi")
                )
            )
        ),
        Company(
            id = "topprinting",
            number = "06",
            name = "Top Printing",
            shortName = "Top Printing",
            category = "Digital Printing",
            slogan = "Dari ide menjadi impresi.",
            description = "Layanan digital printing cepat untuk materi promosi, brosur, katalog bisnis, dan percetakan personalisasi bernilai tinggi.",
            history = "Menyediakan layanan cetak digital on-demand dengan mesin terbaru untuk memenuhi kebutuhan promosi bisnis yang memerlukan ketepatan waktu tinggi.",
            commitment = "Mendukung komunikasi merek melalui layanan cetak materi promosi, kebutuhan bisnis, dan personalisasi produk.",
            logoResId = R.drawable.topprintingjaya,
            heroImageResId = R.drawable.hero_topprinting,
            serviceImageResId = R.drawable.service_topprinting,
            accentColor = TopPrintingAccent,
            lightColor = TopPrintingLight,
            address = "Jl. M.T. Haryono No. 128, Karangkidul, Kota Semarang, Jawa Tengah 50136",
            area = "M.T. Haryono, Semarang",
            phone = "024-7654-3215",
            whatsapp = "6281234567895",
            email = "order@topprinting-tj.co.id",
            operationalHours = "Senin – Jumat: 08.00 – 17.00 | Sabtu: 08.00 – 15.00 WIB",
            mapQuery = "Jl. M.T. Haryono No. 128 Semarang",
            totalLeadTime = "1 – 3 Hari Kerja (Cepat Jadi)",
            categories = listOf("Semua", "Brosur & Katalog", "Kartu Nama", "Media Promosi"),
            pillars = defaultPillars,
            products = listOf(
                Product(
                    id = "tp-1",
                    name = "Brosur & Katalog Produk",
                    category = "Brosur & Katalog",
                    badge = "MARKETING COLLATERAL",
                    description = "Cetak digital resolusi tinggi, kertas Art Paper 150-260 gsm, lipat presisi 2-fold / 3-fold warna tajam.",
                    material = "Art Paper 150 / 210 / 260 gsm",
                    dimensions = "A4, A5, Folio, Square, Custom",
                    finishing = "Laminasi Doff/Glossy, Lipat mesin akurat",
                    moq = "100 pcs",
                    leadTime = "1 – 2 hari kerja",
                    imageResId = R.drawable.prod_tp_brosur_katalog
                ),
                Product(
                    id = "tp-2",
                    name = "Kartu Nama Bisnis Premium",
                    category = "Kartu Nama",
                    badge = "BUSINESS ESSENTIAL",
                    description = "Kertas Art Carton 310 gsm, laminasi doff/glossy halus, opsi Spot UV timbul & Gold Foil mewah.",
                    material = "Art Carton 310 gsm / Fancy Texture Paper",
                    dimensions = "9 x 5.5 cm standar",
                    finishing = "Doff velvety, Spot UV timbul, Foil emas/perak, Round corner",
                    moq = "2 box (200 pcs)",
                    leadTime = "1 hari kerja",
                    imageResId = R.drawable.prod_tp_kartu_nama
                ),
                Product(
                    id = "tp-3",
                    name = "Media Promosi & POSM",
                    category = "Media Promosi",
                    badge = "EVENT & RETAIL",
                    description = "Flyer, tent card meja, poster, dan standing roll-up banner warna akurat cepat jadi untuk event promosi.",
                    material = "Luster, Albatros, Flexi Korea, Akrilik",
                    dimensions = "A3+, 60x160 cm, 80x200 cm",
                    finishing = "Include kaki standing banner alumunium kokoh",
                    moq = "1 set / 50 pcs",
                    leadTime = "1 hari kerja",
                    imageResId = R.drawable.prod_tp_media_promosi
                ),
                Product(
                    id = "tp-4",
                    name = "Cetak Koper & Merchandise Custom",
                    category = "Media Promosi",
                    badge = "CUSTOM MERCHANDISE",
                    description = "Cetak sablon & digital koper, jersey, tas spunbond, dan aneka merchandise bisnis berkualitas tinggi.",
                    material = "Polycarbonate, Kain Jersey, Kanvas, Akrilik",
                    dimensions = "Custom sesuai model koper & merchandise",
                    finishing = "Sablon presisi, cetak UV tahan gores & cuaca",
                    moq = "10 pcs",
                    leadTime = "3 – 5 hari kerja",
                    imageResId = R.drawable.prod_tp_cetak_koper
                )
            ),
            steps = listOf(
                ProductionStep(
                    stepNumber = "01",
                    title = "Konsultasi Cepat & Kirim File Desain",
                    description = "Kirim file melalui WhatsApp / email dalam format PDF/TIFF siap cetak.",
                    sla = "30 Menit",
                    qcChecklist = listOf("Preflight cek resolusi gambar min 300 DPI")
                ),
                ProductionStep(
                    stepNumber = "02",
                    title = "Digital Proofing & Persetujuan Warna",
                    description = "Pengecekan preview digital atau cetak satu lembar bukti warna.",
                    sla = "2 Jam",
                    qcChecklist = listOf("Kalibrasi profil warna CMYK mesin digital")
                ),
                ProductionStep(
                    stepNumber = "03",
                    title = "Cetak Mesin Digital Press Berkecepatan Tinggi",
                    description = "Proses cetak laser digital toner ultra-fine resolusi 2400 DPI.",
                    sla = "3 – 5 Jam",
                    qcChecklist = listOf("Pemeriksaan ketajaman teks kecil & gradasi warna")
                ),
                ProductionStep(
                    stepNumber = "04",
                    title = "Finishing Potong Presisi & Pengemasan",
                    description = "Potong mesin polar program komputer, laminasi, dan pengemasan boks rapi.",
                    sla = "2 Jam",
                    qcChecklist = listOf("Sortir cacat potong & hitungan kuantitas tepat")
                )
            )
        )
    )

    fun getCompany(id: String): Company? = companies.find { it.id == id }
}
