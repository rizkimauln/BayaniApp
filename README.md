# 📱 Bayani App: Integrasi Al-Qur'an & Sains

![Language](https://img.shields.io/badge/Language-Kotlin-purple)
![UI Framework](https://img.shields.io/badge/UI-Jetpack%20Compose-green)
![Backend](https://img.shields.io/badge/Backend-Firebase-orange)
![Status](https://img.shields.io/badge/Status-Release%20v1.0.0-blue)

**Bayani** adalah aplikasi Al-Qur'an digital berbasis Android yang berfokus pada integrasi ayat-ayat *Kauniyah* (Sains). Aplikasi ini dirancang khusus untuk mempermudah mahasiswa (khususnya Saintek) dan akademisi dalam menemukan referensi ayat Al-Qur'an yang berkaitan dengan ilmu pengetahuan dan teknologi.

---

## 📸 Screenshots

| Halaman Home | Baca Al-Qur'an | Penjelasan Sains | Dark Mode |
|:---:|:---:|:---:|:---:|
| <img src="path/to/screenshot1.png" width="200"/> | <img src="path/to/screenshot2.png" width="200"/> | <img src="path/to/screenshot3.png" width="200"/> | <img src="path/to/screenshot4.png" width="200"/> |

---

## 🧐 Latar Belakang

Proyek ini lahir dari keresahan mahasiswa Teknik Informatika (UIN Sunan Gunung Djati Bandung) saat menempuh mata kuliah **Ulumul Qur'an**. Tantangan utama yang dihadapi adalah sulitnya mencari dan mengumpulkan ayat-ayat yang memiliki korelasi dengan sains secara cepat tanpa harus membuka banyak literatur tafsir fisik.

**Bayani hadir sebagai solusi:** Sebuah *pocket library* yang mengkurasi dan menandai ayat-ayat sains secara sistematis.

---

## ✨ Fitur Unggulan

* **🔍 Smart Science Tagging:** Penandaan visual khusus pada ayat-ayat yang mengandung fakta ilmiah.
* **🧪 Scientific Facts Integration:** Penjelasan mendalam mengenai korelasi ayat dengan fakta sains modern (Biologi, Astronomi, Fisika, dll).
* **📖 Digital Quran Reader:** Antarmuka membaca yang bersih, mendukung font standar IndoPak/Utsmani.
* **🌙 Dark Mode Support:** Kenyamanan membaca di kondisi minim cahaya.
* **🔖 Smart Bookmark:** Simpan ayat favorit atau bahan tugas ke penyimpanan lokal (Offline Support).
* **☁️ Cloud Content:** Data fakta sains tersimpan di Cloud (Firebase) sehingga dapat diperbarui secara *real-time*.

---

## 🛠️ Tech Stack & Libraries

Aplikasi ini dibangun menggunakan teknologi Android modern (Modern Android Development):

* **Bahasa:** [Kotlin](https://kotlinlang.org/) (100%)
* **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material Design 3)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Local Database:** [Room Database](https://developer.android.com/training/data-storage/room) (SQLite Wrapper)
* **Cloud Backend:** [Firebase Firestore](https://firebase.google.com/docs/firestore)
* **Asynchronous:** Kotlin Coroutines & Flow
* **Dependency Injection:** Hilt / Koin (Opsional, sesuaikan jika pakai)

---

## 📥 Download

Aplikasi Bayani sudah tersedia dan dapat diunduh melalui Google Play Store:

[![Get it on Google Play](https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png)](LINK_PLAY_STORE_KAMU_DISINI)

*(Ganti link di atas dengan link aplikasi Play Store kamu)*

---

## 💻 Setup Project (Untuk Developer)

Jika ingin menjalankan *source code* ini di komputer lokal:

1.  **Clone repository ini:**
    ```bash
    git clone [https://github.com/username-kamu/bayani-app.git](https://github.com/username-kamu/bayani-app.git)
    ```
2.  **Buka di Android Studio:**
    Pastikan menggunakan Android Studio versi terbaru (Hedgehog/Iguana atau lebih baru).
3.  **Setup Firebase:**
    * Buat project baru di Firebase Console.
    * Download `google-services.json`.
    * Letakkan file tersebut di folder `app/`.
4.  **Sync Gradle & Run:**
    Tunggu proses sinkronisasi selesai, lalu jalankan di Emulator atau Device fisik.

---

## 👤 Author

**Rizki Maulana**
* Mahasiswa Teknik Informatika - UIN Sunan Gunung Djati Bandung (2023)
* Anggota Divisi Kominfo HIMATIF
* [LinkedIn](LINK_LINKEDIN_KAMU) | [Instagram](LINK_INSTAGRAM_KAMU) | [Website](LINK_WEBSITE_KAMU)

---

## 📄 Lisensi

Copyright © 2024 Bayani App. All rights reserved.
