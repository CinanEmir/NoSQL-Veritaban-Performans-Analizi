Öğrenci No: 22060328 Ad: Muhammed Emir Soyad: Cinan
NoSQL Veritabanı Performans Analizi Raporu
1. Proje Özeti ve Amacı
Bu proje, bilgisayar mühendisliği laboratuvar çalışmaları kapsamında Redis, Hazelcast ve MongoDB NoSQL veritabanlarının performans değerlerini ölçmek amacıyla geliştirilmiştir. Deneyde 10.000 adet öğrenci kaydı (student_no, name, department) kullanılarak yüksek trafik altında sistemlerin tepki süreleri analiz edilmiştir.

2. Uygulama Mimarisi
Model: Student sınıfı ile veriler modellenmiştir.
Backend: SparkJava framework'ü ile 3 farklı REST endpoint oluşturulmuştur.
Veritabanı Yapılandırması:
Redis: Docker üzerinde JedisPool (Bağlantı Havuzu) ile yönetilmiştir.
Hazelcast: Uygulama içine gömülü (Embedded) olarak başlatılmıştır.
MongoDB: Docker konteynerı üzerinden standart Java sürücüsü ile bağlanılmıştır.

3. Koşum Zamanı (Execution Time) Testleri
100 adet isteğin paralel (P10) olarak gönderilmesiyle elde edilen toplam çalışma süreleri (real time):
Redis Execution Time: 0.535s
Hazelcast Execution Time: 0.552s
MongoDB Execution Time: 0.553s

4. Genel Değerlendirme
İşlem Hızı: MongoDB, saniyede 1041.67 işlem yaparak Siege testinde en yüksek performansı sergilemiştir.
Yanıt Süresi: Her üç veritabanı da 0.01 - 0.02 saniye aralığında yanıt vererek düşük gecikme (latency) hedefini başarıyla karşılamıştır.
Koşum Zamanı Analizi: time komutuyla yapılan testte Redis (0.535s), en hızlı toplam çalışma süresine ulaşmıştır. Bu durum, Redis'in basit anahtar-değer (Key-Value) yapısının ardışık isteklerdeki verimliliğini kanıtlamaktadır.
Hata Yönetimi: Testler sırasında başlangıçta karşılaşılan bağlantı sorunları, Redis tarafında JedisPool mimarisine geçilerek ve ağ yapılandırmaları düzeltilerek giderilmiştir. Sistem tüm testleri %100 erişilebilirlik ile tamamlamıştır.
