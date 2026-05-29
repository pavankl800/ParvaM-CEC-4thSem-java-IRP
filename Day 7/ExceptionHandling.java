import java.sql.*;

public class ExceptionHandling {
    public static void main(String[] args) {

        // ArithmeticException - Caused due to Division by Zero Error
        try {
            int marks = 100;
            int subjects = 0;
            // Try block will work
            // int subjects = 3;

            int average = marks / subjects;

            System.out.println("Average Score: " + average);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException");
            System.out.println("Cannot divide by Zero!");
            System.out.println("Error: " + e.getMessage() + "\n");
        }

        // ArrayIndexOutOfBoundException - Caused by Accessing the value of index which is not found
        try {
            String[] programs = {
                // [0, 1, 2, 3, 4]
                    "Java",
                    "Python",
                    "JavaScript",
                    "C",
                    // Try block will work
                    // "C#",
                    "C++"
                };
                
                System.out.println(programs[5]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ArrayIndexOutOfBoundException");
                System.out.println("Data not found!");
                System.out.println("Error: " + e.getMessage() + "\n");
            }
            
            // NumberFormatException - Caused when number cannot be formatted or parsed from
            // string
            try {
                String feesInWords = "Five Thousand";
                // Try block will work
                // String feesInWords = "5000";
                
                int fees = Integer.parseInt(feesInWords);

                System.out.println("Fees in Words: " + feesInWords);
                System.out.println("Fees in Integer: " + fees);
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException");
                System.out.println("Invalid Number Format!");
                System.out.println("Error: " + e.getMessage() + "\n");
            }
            
            // NullPointerException - Caused when trying to access the data which is already
            // null
            try {
                String name = null;
                // Try block will work
                // String name = "Akshay";
                System.out.println(name.length());
        } catch (NullPointerException e) {
        System.out.println("NullPointerException");
            System.out.println("String is already null cannot find length!");
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
}