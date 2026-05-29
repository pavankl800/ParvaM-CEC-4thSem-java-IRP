// Abstract Class
// IF the method definition is not known eaelier, we will leave the method of base class empty or incomplete and later define in the Derived Class
abstract class Employee {
    String name;
    int hoursWorked;

    Employee(String name, int hoursWorked){
        this.name = name;
        this.hoursWorked = hoursWorked;        
    }

    abstract double calculateSalary();

    void printPaySlip(){
        System.out.println("---------------------");
        System.out.println("Employee: " + name);
        System.out.println("Number of hours worked: " + hoursWorked);
        System.out.println("Salary: Rs." + calculateSalary());
        System.out.println("---------------------");
    }
}

class FullTimeEmployee extends Employee{
    double monthlySalary;

    FullTimeEmployee(String name, double monthlySalary){
        super(name, 160);
        this.monthlySalary = monthlySalary;
    }

    // Calculating Salary later for the Full Time Employee
    @Override
    double calculateSalary() {
        return monthlySalary;
    }
}

class ContractEmployee extends Employee {
    double hourlyRate;
    
    ContractEmployee(String name, int hoursWorked, double hourlyRate) {
        super(name, hoursWorked);
        this.hourlyRate = hourlyRate;
    }
    
    // Calculating Salary later for the Hourly Based Employee with different logic
    @Override
    double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

// Example 2:
abstract class Rental{
    String vehicleType;
    String vehicle;
    int numberOfDays;
    String addOn;
    int addOnPrice;
    String fuelType;

    Rental(String vehicleType, String vehicle, int days, String addOn, int addOnPrice, String fuel){
        this.vehicleType = vehicleType;
        this.vehicle = vehicle;
        numberOfDays = days;
        this.addOn = addOn;
        this.addOnPrice = addOnPrice;
        fuelType = fuel;
    }

    // Left Incomplete
    abstract double caculateFare();

    void displayFare(){
        System.out.println("---------------");
        System.out.println("Vehicle Type: " + vehicleType);
        System.out.println("Vehicle: " + vehicle);
        System.out.println("Fuel Type: " + fuelType);
        System.out.println("No. of Days: " + numberOfDays);
        System.out.println("Add On: " + addOn);
        System.out.println("Add On Price: Rs." + addOnPrice + "/-");
        System.out.println("Total Fare: Rs." + caculateFare() + "/-");
        System.out.println("---------------");
    }
}

class Bike extends Rental{
    double pricePerDay;

    Bike(String vehicleType, String vehicle, int days, String addOn, int addOnPrice, String fuel, double pricePerDay){
        super(vehicleType, vehicle, days, addOn, addOnPrice, fuel);
        this.pricePerDay = pricePerDay;
    }

    // Later calculating the Fare in the Derived class
    double caculateFare(){
        double totalFare = pricePerDay * numberOfDays + (addOnPrice * numberOfDays);
        return totalFare;
    }
}

class Car extends Rental{
    double pricePerDay;
    double fuelPrice = 75.00;
    float distance;
    
    Car(String vehicleType, String vehicle, int days, String addOn, int addOnPrice, String fuel, double pricePerDay, float distance){
        super(vehicleType, vehicle, days, addOn, addOnPrice, fuel);
        this.pricePerDay = pricePerDay;
        this.distance = distance;
    }
    
    // Later calculating the Fare in the Derived class for Car Class
    double caculateFare(){
        double totalFare = pricePerDay * numberOfDays + (addOnPrice * numberOfDays);
        double fuelCost = fuelPrice * distance;
        totalFare += fuelCost;
        System.out.println("Total Distance Travelled: " + distance + " Kms and Fuel Cost: Rs." + fuelCost + "/-");
        return totalFare;
    }
}

public class AbstractClass {
    public static void main(String[] args){
        Employee e1 = new FullTimeEmployee("Abhishek", 32000.0);
        Employee e2 = new ContractEmployee("Avinash", 75, 550.0);

        e1.printPaySlip();
        e2.printPaySlip();

        Rental bike = new Bike("Bike", "Pulsar", 5, "Helmet", 50, "Petrol", 150);

        bike.caculateFare();
        bike.displayFare();

        Rental car = new Car("Car", "Kia Sonet", 7, "Spare Tyre", 85, "Diesel", 185.00, 275.5f);

        car.caculateFare();
        car.displayFare();
    }   
}