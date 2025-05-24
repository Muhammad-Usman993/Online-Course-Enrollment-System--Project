package usman_package;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String id;                   // Course ka unique ID
    private String title;                // Course ka naam/title
    private int capacity;                // Kitne students course mein enroll ho sakte hain
    private List<Student> enrolledStudents = new ArrayList<>();  // Enrolled students ki list
    private Instructor instructor;       // Course ka instructor

    public Course(String id, String title, int capacity, Instructor instructor) {
        this.id = id;
        this.title = title;
        this.capacity = capacity;
        this.instructor = instructor;
    }

    // Check karta hai ke abhi seats available hain ya nahi
    public boolean hasSeat() {
        return enrolledStudents.size() < capacity;
    }

    // Student ko course mein enroll karta hai agar seat available ho
    public void enrollStudent(Student student) {
        if (hasSeat()) {
            enrolledStudents.add(student);
        }
    }

    // ✅ Getter methods jo ke MainGUI mein use hoti hain
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
