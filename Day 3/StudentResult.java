// Encapsulation: Wrapping up of Data Members & Member Methods to avoid the unauthorized access
import java.util.Scanner;

class StudentMarks {
    // 2 public variables or data members
    String name;
    String usn;
    // 3 private variables or data members
    private int sub1Marks;
    private int sub2Marks;
    private int sub3Marks;

    Scanner sc = new Scanner(System.in);
    
    // Assign the Student Details (Setter) 
    void getStudentDetails() {
        System.out.println("Enter the Student Name: ");
        String stdName = sc.nextLine();
        name = stdName;
        
        System.out.println("Enter the Student's USN: ");
        // Regex - Regular Expressions
        // Validation for Valid USN
        // 1(num) + 2(alpha) + 2(num) + 2(alpha) + 3(num)
        String pattern = "^[1-3][A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$";
        // 1CE24CS108
        String stdUSN;
        // To retry the USN input, if USN
        while (true) {
            stdUSN = sc.nextLine().toUpperCase().trim(); // Read the entire line and convert to uppercase
            
            if (stdUSN.matches(pattern)) {
                System.out.println("✓ Valid USN: " + stdUSN);
                usn = stdUSN;
                break;
            } else {
                System.out.println("✗ Invalid USN! Please enter again (Format: 1RI18IS007)");
                System.out.print("Enter the Student's USN: ");
            }
        }
    }

    void inputMarks(int m1, int m2, int m3) {
        // Validation for Valid marks b/w 0 to 100
        if (m1 >= 0 && m2 >= 0 && m3 >= 0 && m1 <= 100 && m2 <= 100 && m3 <= 100) {
            sub1Marks = m1;
            sub2Marks = m2;
            sub3Marks = m3;
            System.out.println("Marks assigned successfully!");
            System.out.println("Physics: " + sub1Marks);
            System.out.println("Chemistry: " + sub2Marks);
            System.out.println("Mathematics: " + sub3Marks);
        } else {
            System.out.println("Enter valid marks! (0-100 range)");
        }
    }

    void calculateResult() {
        int totalMarks = sub1Marks + sub2Marks + sub3Marks;
        System.out.println("Total Marks: " + totalMarks);
        
        float percentage = (float) totalMarks / 3;
        System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");
    
        if(percentage >= 85){
            System.out.println("Congratulations, You've scored Distinction!");
        } else if(percentage >= 75){
            System.out.println("Congratulations, You've scored First Class!");
        } else if(percentage >= 65){
            System.out.println("Congratulations, You've scored Second Class!");
        } else if(percentage >= 35){
            System.out.println("You need to add some efforts, You've scored Third Class!");
        } else {
            System.out.println("You've failed in the exam. Better luck next time!");
        }
    }
}

public class StudentResult {
    public static void main(String[] args) {
        StudentMarks std1 = new StudentMarks();
        std1.getStudentDetails();
        std1.inputMarks(45, 35, 55);
        std1.calculateResult();
        // Second Student
        StudentMarks std2 = new StudentMarks();
        std2.getStudentDetails();
        std2.inputMarks(-45, 35, 55);
        std2.calculateResult();
        // String example = "   Akshay Rao    ";
        // System.out.println(example);
        // System.out.println(example.trim());
    }
}