package app.store;

import app.model.Student;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisStore {
    private JedisPool jedisPool;
    private Gson gson = new Gson();

    public RedisStore() {
        // Bağlantı havuzu ayarları
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10); // Aynı anda maksimum 10 bağlantı

        // Docker Redis'e bağlanıyoruz
        this.jedisPool = new JedisPool(poolConfig, "localhost", 6379);
    }

    public void saveStudent(Student student) {
        // Try-with-resources kullanarak bağlantıyı otomatik kapatıyoruz
        try (Jedis jedis = jedisPool.getResource()) {
            String json = gson.toJson(student);
            jedis.set("student:" + student.getStudent_no(), json);
        } catch (Exception e) {
            System.err.println("Redis Kayıt Hatası: " + e.getMessage());
        }
    }

    public Student getStudent(String studentNo) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.get("student:" + studentNo);
            if (json != null) {
                return gson.fromJson(json, Student.class);
            }
        } catch (Exception e) {
            System.err.println("Redis Okuma Hatası: " + e.getMessage());
        }
        return null;
    }
}