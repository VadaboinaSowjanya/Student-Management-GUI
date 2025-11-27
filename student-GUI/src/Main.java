import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    int age;
    int rollNumber;
    double[] marks;

    Student(String name, int age, int rollNumber, double[] marks) {
        this.name = name;
        this.age = age;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    double calculateAverage() {
        double sum = 0;
        for (double mark : marks) {
            sum += mark;
        }
        return sum / marks.length;
    }

    String getInfo() {
        return "Name: " + name + "\nAge: " + age + "\nRoll No: " + rollNumber +
                "\nAverage Marks: " + calculateAverage() + "\n------------------\n";
    }
}

class StudentManagementGUI {
    private static final ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Management System");
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField rollField = new JTextField();
        JTextField marksField = new JTextField();

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Roll Number:"));
        inputPanel.add(rollField);
        inputPanel.add(new JLabel("Marks (comma-separated):"));
        inputPanel.add(marksField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Student");
        JButton viewButton = new JButton("View All");
        JButton searchButton = new JButton("Search");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);

        // Output Area
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add panels to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Button Logic
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                int roll = Integer.parseInt(rollField.getText());
                String[] markStrings = marksField.getText().split(",");
                double[] marks = new double[markStrings.length];
                for (int i = 0; i < markStrings.length; i++) {
                    marks[i] = Double.parseDouble(markStrings[i].trim());
                }
                Student student = new Student(name, age, roll, marks);
                students.add(student);
                outputArea.setText("✅ Student added successfully!\n");
                nameField.setText("");
                ageField.setText("");
                rollField.setText("");
                marksField.setText("");
            } catch (Exception ex) {
                outputArea.setText("⚠️ Error: Please check your input.\n");
            }
        });

        viewButton.addActionListener(e -> {
            if (students.isEmpty()) {
                outputArea.setText("📭 No students to display.\n");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Student s : students) {
                    sb.append(s.getInfo());
                }
                outputArea.setText(sb.toString());
            }
        });

        searchButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter Roll Number to Search:");
            try {
                int roll = Integer.parseInt(input);
                boolean found = false;
                for (Student s : students) {
                    if (s.rollNumber == roll) {
                        outputArea.setText(s.getInfo());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    outputArea.setText("❌ Student not found.\n");
                }
            } catch (Exception ex) {
                outputArea.setText("⚠️ Invalid roll number.\n");
            }
        });

        updateButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter Roll Number to Update:");
            try {
                int roll = Integer.parseInt(input);
                for (Student s : students) {
                    if (s.rollNumber == roll) {
                        String newName = JOptionPane.showInputDialog(frame, "Enter new name:", s.name);
                        String newAgeStr = JOptionPane.showInputDialog(frame, "Enter new age:", s.age);
                        String newMarksStr = JOptionPane.showInputDialog(frame, "Enter new marks (comma-separated):");

                        s.name = newName;
                        s.age = Integer.parseInt(newAgeStr);
                        String[] markStrings = newMarksStr.split(",");
                        double[] newMarks = new double[markStrings.length];
                        for (int i = 0; i < markStrings.length; i++) {
                            newMarks[i] = Double.parseDouble(markStrings[i].trim());
                        }
                        s.marks = newMarks;
                        outputArea.setText("✅ Student updated successfully.\n");
                        return;
                    }
                }
                outputArea.setText("❌ Student not found.\n");
            } catch (Exception ex) {
                outputArea.setText("⚠️ Invalid input.\n");
            }
        });

        deleteButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter Roll Number to Delete:");
            try {
                int roll = Integer.parseInt(input);
                boolean removed = students.removeIf(s -> s.rollNumber == roll);
                if (removed) {
                    outputArea.setText("🗑️ Student deleted successfully.\n");
                } else {
                    outputArea.setText("❌ Student not found.\n");
                }
            } catch (Exception ex) {
                outputArea.setText("⚠️ Invalid roll number.\n");
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }
}