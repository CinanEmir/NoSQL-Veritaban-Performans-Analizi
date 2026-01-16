package app;

import app.model.Student;
import app.store.HazelcastStore;
import app.store.MongoStore;
import app.store.RedisStore;
import com.google.gson.Gson;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        // 1. Sunucu Ayarları
        port(8080);

        // Store'ları başlat (Hata çıkarsa terminalde görürüz)
        RedisStore redisStore = new RedisStore();
        MongoStore mongoStore = new MongoStore();
        HazelcastStore hazelcastStore = new HazelcastStore();
        Gson gson = new Gson();

        // 2. Basit bir Test Rotası (404 alıp almadığımızı kontrol etmek için)
        get("/test", (req, res) -> "SparkJava Calisiyor!");

        // 3. Ödev Rotaları
        // Not: "student_no=" kısmını rotanın içine sabit metin olarak yazdık
        // REDIS ENDPOINT
        get("/nosql-lab-rd/*", (req, res) -> {
            res.type("application/json");
            // splat()[0] bize "student_no=2025000001" kısmının tamamını verir
            String fullParam = req.splat()[0];
            String id = fullParam.replace("student_no=", ""); // Sadece ID'yi alıyoruz

            Student s = redisStore.getStudent(id);
            return s != null ? gson.toJson(s) : "{\"error\": \"Ogrenci bulunamadi!\"}";
        });

// HAZELCAST ENDPOINT
        get("/nosql-lab-hz/*", (req, res) -> {
            res.type("application/json");
            String fullParam = req.splat()[0];
            String id = fullParam.replace("student_no=", "");

            Student s = hazelcastStore.getStudent(id);
            return s != null ? gson.toJson(s) : "{\"error\": \"Ogrenci bulunamadi!\"}";
        });

// MONGODB ENDPOINT
        get("/nosql-lab-mon/*", (req, res) -> {
            res.type("application/json");
            String fullParam = req.splat()[0];
            String id = fullParam.replace("student_no=", "");

            Student s = mongoStore.getStudent(id);
            return s != null ? gson.toJson(s) : "{\"error\": \"Ogrenci bulunamadi!\"}";
        });

        // Sunucunun hazır olduğundan emin olalım
        init();
        awaitInitialization();

        // 4. Veri Yükleme (Sunucu başladıktan sonra)
        System.out.println("Veritabanlarina 10.000 kayit ekleniyor...");
        for (int i = 1; i <= 10000; i++) {
            String studentNo = String.valueOf(2025000000L + i);
            Student s = new Student(studentNo, "Ogrenci-" + i, "Computer Engineering");
            redisStore.saveStudent(s);
            mongoStore.saveStudent(s);
            hazelcastStore.saveStudent(s);
        }
        System.out.println("Yukleme TAMAMLANDI. Sunucu aktif!");
    }
}