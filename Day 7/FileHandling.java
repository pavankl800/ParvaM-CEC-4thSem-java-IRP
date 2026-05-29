// Library for File Handling methods
import java.io.*;
import java.util.Scanner;

public class FileHandling {
    public static void main(String[] args) {

        // Creating a new file: "demo.txt"
        try {
            File file = new File("demo.txt");

            if (file.createNewFile()) {
                System.out.println("New File Created Successfully!");
            } else {
                System.out.println("File Already Exists!");
            }
        } catch (IOException e) {
            System.out.println("Error occured while creating the file!");
            System.out.println("Reason: " + e.getMessage());
        }

        // Writing in the file
        try {
            FileWriter writer = new FileWriter("demo.txt");

            System.out.println("\nWriting from Java File Handler!");

            writer.write("Name: Akshay Rao\n");
            writer.write("Company: ParvaM\n");
            writer.write("Role: Technical Trainer\n");

            writer.close();
        } catch (IOException e) {
            System.out.println("Error occurred while writing in the file");
            System.out.println("Reason: " + e.getMessage());
        }

        // Reading from the file
        try {
            File file = new File("demo.txt");

            Scanner sc = new Scanner(file);

            System.out.println("\nFile Content: \n");

            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }

            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }

        // Appending the Content in the existing file
        try {
            FileWriter writer = new FileWriter("demo.txt", true);

            writer.write("Program: Industry Readines Program");
            writer.write("\nProgram Topic: Java + AI IRP");
            writer.write("\nCollege: City Engineering College");
            writer.write("\nBranch: CSE");
            writer.write("\nSection: B");
            writer.write("\nSemester: 4th Sem");

            writer.close();

            System.out.println("\nContent Appended Successfully!");
        } catch (IOException e) {
            System.out.println("Error Appending Data!");
        }

        // Deleting the file
        File file = new File("demo.txt");
        if (file.delete()) {
            System.out.println("\nFile Deleted Successfully!");
        } else {
            System.out.println("Unable to Delete File!");
        }
    }
}