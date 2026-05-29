import java.util.Scanner;

public class ScannerMethods {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter your First Name: ");
        String fName = sc.next();

        System.out.println("Enter your Last Name: ");
        String lName = sc.next();

        // To avoid skipping the college variable scanner input
        sc.nextLine();
        
        System.out.println("Enter your College Name: ");
        String college = sc.nextLine();
        
        System.out.println("Enter your branch: ");
        String branch = sc.nextLine();

        System.out.println("Enter your section: ");
        String section = sc.next();
        
        System.out.println("Enter your semester: ");
        int semester = sc.nextInt();

        System.out.println("Enter your CGPA: ");
        float cgpa= sc.nextFloat();

        System.out.println("Your Details are as follows:");
        System.out.println("Name: " + fName + " " + lName);
        System.out.println("College: " + college);
        System.out.println("Branch: " + branch);
        System.out.println("Section: " + section);
        System.out.println("Semester: " + semester);
        System.out.println("CGPA: " + cgpa);
    }
}