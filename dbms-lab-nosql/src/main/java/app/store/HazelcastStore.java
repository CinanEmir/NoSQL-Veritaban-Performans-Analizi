package app.store;

import app.model.Student;
import com.hazelcast.core.Hazelcast; // HazelcastClient yerine Hazelcast
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HazelcastStore {
    private IMap<String, Student> studentMap;

    public HazelcastStore() {
        // Dışarıdaki bir sunucuya bağlanmak yerine (Client),
        // Uygulamanın içinde bir Hazelcast sunucusu başlatıyoruz (Embedded).
        // Bu yöntem versiyon uyum sorununu tamamen ortadan kaldırır.
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        this.studentMap = hz.getMap("students");
    }

    public void saveStudent(Student student) {
        studentMap.put(student.getStudent_no(), student);
    }

    public Student getStudent(String studentNo) {
        return studentMap.get(studentNo);
    }
}