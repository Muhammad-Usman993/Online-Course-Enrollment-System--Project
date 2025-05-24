package usman_package;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;                // Student ka unique ID
    private String name;              // Student ka naam
    private String department;        // Student ka department ya program
    private String fatherName;        // Student ke walid ka naam
    private String semester;          // Student ka current semester
    private List<Course> enrolledCourses;  // Wo courses jin mein student enrolled hai

    public Student(String id, String name, String department, String fatherName, String semester) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.fatherName = fatherName;
        this.semester = semester;
        this.enrolledCourses = new ArrayList<>();  // Enrolled courses ki list initialize karna
    }

    // Ye method student ko course mein enroll karta hai
    public void enroll(Course course) {
        enrolledCourses.add(course);
    }

    // Getter methods jo ke fields ki value return karti hain
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getFatherName() { return fatherName; }
    public String getSemester() { return semester; }
    public List<Course> getEnrolledCourses() { return enrolledCourses; }

    // Student ki basic information ko string form mein return karta hai
    @Override
    public String toString() {
        return id + " - " + name + " (" + department + ", Sem: " + semester + ")";
    }
}
