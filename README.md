# Aplikasi Mobile Thamrin Jaya Group (Android Native)

Aplikasi profil perusahaan dan katalog spesifikasi interaktif untuk **PT Thamrin Jaya Group** dan 6 unit bisnisnya, dibangun dengan **Kotlin & Jetpack Compose**.

## Fitur Utama Sesuai Desain Figma

1. **Master Hub / Beranda Utama (`00 Mobile`)**
   - Header & Hero dengan filosofi holding: *"ONE GROUP. MORE POSSIBILITIES. Enam keahlian. Satu visi ke depan."*
   - Pintasan Fitur Utama:
     - 📦 Fitur 01: Katalog Produk & Spesifikasi Transparan
     - ⚙️ Fitur 02: Alur Proses Produksi & SLA Quality Check
     - 📍 Fitur 03: Saluran Langsung WhatsApp, Kontak & Lokasi
   - Direktori 6 Unit Bisnis dengan aksi cepat Buka Profil, WhatsApp Sales, dan Google Maps.
   - Sinergi ekosistem holding & informasi kantor pusat.

2. **Menu Switcher Unit Bisnis (`01 Mobile`)**
   - Bottom Sheet modal untuk berganti unit bisnis secara instan.
   - Akses cepat kembali ke Master Hub Holding.
   - Pintasan langsung ke katalog, alur proses, dan hotline WhatsApp.

3. **Profil Unit Bisnis & Tab Fitur (`03 Mobile` s/d `11 Mobile`)**
   - **Tab 1 - Tentang**: Slogan, visi, komitmen, sejarah, dan 4 Pilar Keunggulan Mutu.
   - **Tab 2 - Katalog**: Filter kategori produk, kartu spesifikasi transparan (material, dimensi, finishing, MOQ, lead time), tombol tanya WA dan minta penawaran.
   - **Tab 3 - Alur Proses**: Estimasi total lead time, 5 tahapan kerja berurutan dengan SLA pasti dan Checklist Jaminan Mutu (QC).
   - **Tab 4 - Kontak & Peta**: Saluran WhatsApp sales (<15 menit respons), telepon kantor, email, alamat fisik, navigasi GPS/Maps, dan formulir permintaan penawaran resmi (Inquiry).

4. **Integrasi Intent Sistem (Tanpa Database)**
   - WhatsApp Sales: membuka WhatsApp secara instan dengan pesan otomatis terformat rapi.
   - Google Maps / GPS: membuka navigasi alamat pabrik/kantor.
   - Telepon & Email: langsung membuka aplikasi dialer dan email klien.

## 6 Unit Bisnis Terdaftar

| No | Unit Bisnis | Bidang / Kategori | Wilayah |
|---|---|---|---|
| 01 | **PT. Globalindo Thamrin Jaya** | Offset & Packaging | Candi, Semarang |
| 02 | **PT. Multipack Thamrin Jaya** | Kardus & Karton | Terboyo, Semarang |
| 03 | **PT. HTE Rotopack Indonesia** | Flexible Packaging | Sayung, Demak |
| 04 | **Maxtech Solution Indonesia** | Cigarette Machinery | Wijayakusuma, Semarang |
| 05 | **CV. Sinar Jaya** | Cigarette Materials | Kaligawe, Semarang |
| 06 | **Top Printing** | Digital Printing | M.T. Haryono, Semarang |

## Cara Membuka & Menjalankan di Android Studio

1. Buka **Android Studio**.
2. Pilih **Open** dan arahkan ke folder `android/`.
3. Tunggu proses Gradle Sync selesai.
4. Jalankan pada emulator atau perangkat Android fisik (Run 'app').
