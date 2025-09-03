# 🚀 JForce Human Resources

Bu proje, **JForce** için geliştirilmiş bir **İnsan Kaynakları Yönetim Sistemi**dir.  
Amaç; personel yönetimi, envanter takibi, zimmetleme, rol bazlı yetkilendirme ve raporlama süreçlerini dijitalleştirerek daha **kolay**, **hızlı** ve **güvenli** hale getirmektir.

---

## 🔑 Temel Özellikler

- 🔐 **JWT tabanlı kimlik doğrulama**  
- 👥 **Rol bazlı yetkilendirme** (Admin, Human Resources, Inventory Manager)  
- 📋 **Personel Yönetimi**
  - Ekleme, güncelleme, silme
  - Filtreleme (İsim, Soyisim, TCKN, Birim)
  - CSV / PDF rapor export
- 🖥 **Envanter Yönetimi**
  - Yeni envanter ekleme, güncelleme
  - Zimmetleme (On_Staff, Available, Warehouse)
  - Otomatik e-posta bildirimleri
- 📊 **Dashboard Arayüzleri**
  - İnsan Kaynakları Dashboard
  - Envanter Yönetim Dashboard
  - İstatistiksel grafikler & raporlama

---

## 🛠 Kullanılan Teknolojiler

**Backend:**
- Spring Boot  
- Spring Security (JWT)  
- Hibernate / JPA  
- Lombok  

**Frontend:**
- React.js / Angular  

**Database:**
- PostgreSQL  

**Diğer:**
- Docker  
- REST API  
- GitHub Actions  

---

## 📂 Proje Yapısı

```
JForce-Human-Resources/
│── .idea/                        # IDE ayarları
│── JForce/                       # Backend kaynak kodları
│── Staj_Programı_2025.docx.pdf   # Staj programı dokümantasyonu
│── README.md                     # Proje açıklamaları
```

---

## ⚙️ Kurulum

1. Repoyu klonla:
   ```bash
   git clone https://github.com/itsmert/JForce-Human-Resources.git
   cd JForce-Human-Resources
   ```
2. Backend başlat:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Frontend kurulum:
   ```bash
   cd frontend
   npm install
   npm start
   ```
4. `.env` veya `application.properties` içine veritabanı ve API key bilgilerini ekle.

---

## 👤 Roller

- **Admin:** Tüm işlemlere erişim  
- **Human Resources:** Personel işlemleri, raporlar  
- **Inventory Manager:** Envanter ve zimmet yönetimi  

---

## 📌 Yol Haritası

- [x] Authentication & Authorization  
- [x] Personel Yönetimi  
- [x] Envanter Yönetimi  
- [ ] Frontend tamamlanması  
- [ ] Gelişmiş raporlama & grafikler  

---

## 📄 Lisans

📌 Bu proje **JForce** için geliştirilmiştir.  
Her hakkı saklıdır.
