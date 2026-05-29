// Syntax:
// class ClassName {
// data members
// member_functions();
// }
// Class is a blueprint
// Object is an instance of class or real-world entity

class Student {
    String name;
    String usn;
    String branch;
    int semester;

    // Setter Method (Assigning the values to the data members)
    void addInfo(String Name, String USN, String Branch, int sem){
        name = Name;
        usn = USN;
        branch = Branch;
        semester = sem;
    }

    // Getter Method (Fetching the values from the data members)
    void showInfo(){
        System.out.println("Student Details are as follows:");
        System.out.println("Name: " + name);
        System.out.println("USN: " + usn);
        System.out.println("Branch: " + branch);
        System.out.println("Semester: " + semester);
    }
}

class Car{
    // Data Members - Attributes
    // Member methods - Behavior
    String carBrand;
    String carModel;
    String carColor;
    int launchYear;
    double price;
    // Access Modifiers: public, private & protected
    private String fuelType;

    // Constructor
    // It is having the same name as that of the class name
    // It is invoked or called automatically when the object is created
    // It doesn't return any values and it doesn't have any return type
    Car(String carBrand, String carModel, String carColor, int launchYear, double price){
        // this keyword
        // this.data_member = argument
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carColor = carColor;
        this.launchYear = launchYear;
        this.price = price;
    }

    // Setter method
    void setFuelInfo(String typeOfFuel){
        fuelType = typeOfFuel;
    }

    // Getter Method
    void showFuelType(){
        System.out.println("Fuel Info: " + fuelType);
    }
}

public class ClassAndObject {
    public static void main(String[] args){
        // Object(stu1) is created for the Student Class
        Student stu1 = new Student();
        stu1.addInfo("Akshay Rao", "1RI18IS100", "ISE", 9);
        stu1.showInfo();

        // stu2 object is initialed here
        // ClassName ObjName = new ClassName();
        Student stu2 = new Student();
        // Directly assigning the values for the data members
        // 2nd way of assigning the values without setter
        stu2.name = "Ajay Rao";
        stu2.usn = "1CE20CS001";
        stu2.branch = "CSE";
        stu2.semester = 5;  

        stu2.showInfo();
 
        Car innova = new Car("Toyato", "Innova", "Black", 2015, 2099999.00);
        innova.setFuelInfo("Diesel");
        innova.showFuelType();

        // private data members cannot be accessed out of the class via object

        // It can be only accessed through setter and getters
        // innova.fuelType = "Petrol";
        // System.out.println(innova.fuelType);
        innova.carColor = "Dark Blue";
        System.out.println(innova.carColor);
    }
}
