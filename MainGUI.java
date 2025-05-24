package usman_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI {
    private JFrame frame;

    // Mukhtalif screens ke panels
    private JPanel signupPanel, loginPanel, enrollmentPanel;

    // Signup ke fields
    private JTextField signupUsernameField;
    private JPasswordField signupPasswordField;
    private JButton signupBtn;
    private JLabel signupMsgLabel;

    // Login ke fields
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JButton loginBtn;
    private JLabel loginMsgLabel;

    // Enrollment ke fields
    private JTextField studentNameField;
    private JTextField fatherNameField;
    private JTextField semesterField;
    private JTextField programField;
    private JComboBox<String> courseDropdown;
    private JButton enrollBtn;
    private JTextArea enrollmentStatusArea;

    // Simple user "database" (map)
    private java.util.Map<String, String> userDatabase = new java.util.HashMap<>();

    private EnrollmentService enrollmentService;

    public MainGUI() {
        enrollmentService = new EnrollmentService();
        initializeCourses();  // Courses initialize karo
        initializeFrame();    // Frame setup karo
        showSignupPanel();    // Pehla signup panel dikhayo
    }

    private void initializeCourses() {
        // Instructor create karo
        Instructor instructor = new Instructor("I01", "Dr. Ali");

        // Courses ki list
        String[] courseTitles = {
            "Intro to Quranic Arabic",
            "Islamic Ethics in Computing",
            "Object-Oriented Programming",
            "Data Structures",
            "Software Engineering Principles",
            "Islamic History for IT",
            "Java Development",
            "Web Development with Islamic Content",
            "Database Management",
            "Cybersecurity and Islamic Values"
        };

        for (int i = 0; i < courseTitles.length; i++) {
            Course course = new Course("CSE10" + (i + 1), courseTitles[i], 5, instructor);
            FakeDatabase.courseList.add(course);
        }
    }

    private void initializeFrame() {
        frame = new JFrame("Course Enrollment System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);  // Screen ke center mein frame rakho
        frame.setVisible(true);
    }

    private void showSignupPanel() {
        signupPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        signupPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        signupPanel.add(new JLabel("Create Username:"));
        signupUsernameField = new JTextField();
        signupPanel.add(signupUsernameField);

        signupPanel.add(new JLabel("Create Password:"));
        signupPasswordField = new JPasswordField();
        signupPanel.add(signupPasswordField);

        signupBtn = new JButton("Sign Up");
        signupPanel.add(signupBtn);

        signupMsgLabel = new JLabel("");
        signupMsgLabel.setForeground(Color.RED);
        signupPanel.add(signupMsgLabel);

        signupBtn.addActionListener(e -> {
            String username = signupUsernameField.getText().trim();
            String password = new String(signupPasswordField.getPassword());

            // Validation: fields empty nahi honay chahiye
            if (username.isEmpty() || password.isEmpty()) {
                signupMsgLabel.setText("Username and password cannot be empty.");
                return;
            }
            // Username pehle se maujood hai?
            if (userDatabase.containsKey(username)) {
                signupMsgLabel.setText("Username already exists.");
                return;
            }

            userDatabase.put(username, password);  // Naya user add karo
            signupMsgLabel.setForeground(Color.GREEN);
            signupMsgLabel.setText("Signup successful! Please login.");
            // Fields clear karo
            signupUsernameField.setText("");
            signupPasswordField.setText("");

            // Signup ke baad login panel dikhao
            Timer timer = new Timer(1500, ev -> showLoginPanel());
            timer.setRepeats(false);
            timer.start();
        });

        frame.getContentPane().removeAll();
        frame.getContentPane().add(signupPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showLoginPanel() {
        loginPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        loginPanel.add(new JLabel("Username:"));
        loginUsernameField = new JTextField();
        loginPanel.add(loginUsernameField);

        loginPanel.add(new JLabel("Password:"));
        loginPasswordField = new JPasswordField();
        loginPanel.add(loginPasswordField);

        loginBtn = new JButton("Login");
        loginPanel.add(loginBtn);

        loginMsgLabel = new JLabel("");
        loginMsgLabel.setForeground(Color.RED);
        loginPanel.add(loginMsgLabel);

        loginBtn.addActionListener(e -> {
            String username = loginUsernameField.getText().trim();
            String password = new String(loginPasswordField.getPassword());

            // User authentication check karo
            if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
                showEnrollmentPanel();
            } else {
                loginMsgLabel.setText("Invalid username or password!");
            }
        });

        frame.getContentPane().removeAll();
        frame.getContentPane().add(loginPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showEnrollmentPanel() {
        enrollmentPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        formPanel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        formPanel.add(studentNameField);

        formPanel.add(new JLabel("Father's Name:"));
        fatherNameField = new JTextField();
        formPanel.add(fatherNameField);

        formPanel.add(new JLabel("Semester:"));
        semesterField = new JTextField();
        formPanel.add(semesterField);

        formPanel.add(new JLabel("Program / Department:"));
        programField = new JTextField();
        formPanel.add(programField);

        formPanel.add(new JLabel("Select Course:"));
        courseDropdown = new JComboBox<>();
        for (Course c : FakeDatabase.courseList) {
            courseDropdown.addItem(c.getId() + " - " + c.getTitle());
        }
        formPanel.add(courseDropdown);

        enrollBtn = new JButton("Enroll");
        formPanel.add(enrollBtn);

        enrollmentPanel.add(formPanel, BorderLayout.NORTH);

        enrollmentStatusArea = new JTextArea(10, 40);
        enrollmentStatusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(enrollmentStatusArea);
        enrollmentPanel.add(scrollPane, BorderLayout.CENTER);

        enrollBtn.addActionListener(e -> {
            String name = studentNameField.getText().trim();
            String father = fatherNameField.getText().trim();
            String semester = semesterField.getText().trim();
            String program = programField.getText().trim();
            int courseIndex = courseDropdown.getSelectedIndex();

            // Check karo ke sab fields fill hain
            if (name.isEmpty() || father.isEmpty() || semester.isEmpty() || program.isEmpty()) {
                enrollmentStatusArea.append("❌ Please fill all fields!\n");
                return;
            }

            Course selectedCourse = FakeDatabase.courseList.get(courseIndex);
            Student student = new Student(
                "S" + (FakeDatabase.studentList.size() + 1),
                name,
                program,
                father,
                semester
            );
            FakeDatabase.studentList.add(student);

            boolean enrolled = enrollmentService.enroll(student, selectedCourse);
            if (enrolled) {
                enrollmentStatusArea.append("✅ " + name + " enrolled in " + selectedCourse.getTitle() + "\n");
            } else {
                enrollmentStatusArea.append("❌ Enrollment failed. No seats available in " + selectedCourse.getTitle() + "\n");
            }
        });

        frame.getContentPane().removeAll();
        frame.getContentPane().add(enrollmentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
