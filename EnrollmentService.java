package usman_package;

import usman_package.Student;

public class EnrollmentService {
    // Student ko course me enroll karne ka method
    public boolean enroll(Student student, Course course) {
        // Agar course me seat nahi hai to enrollment fail
        if (!course.hasSeat()) return false;

        // Student ko course me add karna
        course.enrollStudent(student);

        // Course ko student ke enrolled courses me add karna
        student.enroll(course);

        // Enrollment successful hai
        return true;
    }
}
