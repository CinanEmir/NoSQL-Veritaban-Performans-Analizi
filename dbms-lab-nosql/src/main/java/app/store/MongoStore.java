package app.store;

import app.model.Student;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MongoStore {
    private MongoCollection<Document> collection;

    public MongoStore() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("nosql_lab");
        this.collection = database.getCollection("students");
    }

    public void saveStudent(Student student) {
        Document doc = new Document("student_no", student.getStudent_no())
                .append("name", student.getName())
                .append("department", student.getDepartment());
        collection.insertOne(doc);
    }

    public Student getStudent(String studentNo) {
        Document doc = collection.find(Filters.eq("student_no", studentNo)).first();
        if (doc != null) {
            return new Student(doc.getString("student_no"), doc.getString("name"), doc.getString("department"));
        }
        return null;
    }
}