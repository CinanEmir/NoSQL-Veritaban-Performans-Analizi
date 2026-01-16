package app.model;

import java.io.Serializable;

public class Student implements Serializable {
    private String student_no;
    private String name;
    private String department;

    // Boş constructor (JSON dönüşümleri için gerekli)
    public Student() {}

    public Student(String student_no, String name, String department) {
        this.student_no = student_no;
        this.name = name;
        this.department = department;
    }

    // Getter ve Setter metotları
    public String getStudent_no() { return student_no; }
    public void setStudent_no(String student_no) { this.student_no = student_no; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}