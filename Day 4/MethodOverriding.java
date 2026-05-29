// Method Overriding
// Decision taken by the JVM on Run-time
// Used with Inheritance
// Derived Class will have the same method name as that of the base class and it will override the method of Base Class

class TelePhone{
    void answerCall(){
        System.out.println("Answering the call from Telephone");
    }
    
    void rejectCall(){
        System.out.println("Rejected the call from Telephone");
    }
}

class SmartPhone extends TelePhone{
    void answerCall(){
        System.out.println("Answering the call from Smartphone");
    }
    
    void rejectCall(){
        // Super Keyword is used to call the overriden method of Parent Class
        super.rejectCall();
        System.out.println("Rejected the call from Smartphone");
    }
}

// Example 2
class Vehicle{
    String brand;
    
    // Constructor of Base(Vehicle) Class
    Vehicle(String brand){
        this.brand = brand;
    }
    
    void start(){
        System.out.println("Turned on the " + brand + " Vehicle");
    }
    
    void drive(){
        System.out.println("Started Driving the " + brand + " Vehicle");
    }
    
    void stop(){
        System.out.println("Stopped the " + brand + " Vehicle");
    }
    
    void park(){
        System.out.println("Parked the " + brand + " Vehicle");
    }
}

class Car extends Vehicle{
    String model;
    
    // Constructor of Derived(Car) Class
    Car(String brand, String model){
        // super() method is used to Initialize the value for Base Class members or in other way to call the Constructor method of Base Class
        super(brand);
        this.model = model;
    }

    // Override annotation is used to mention that Override is going to happen for the methods of Base Class and the Derived Class methods will be called in Run-time
    @Override
    void start(){
        super.start();
        System.out.println("Turned on the " + model + " Car");
    }
    
    @Override
    void drive(){
        System.out.println("Started Driving the " + model + " Car");
    }
    
    @Override
    void stop(){
        System.out.println("Stopped the " + model + " Car");
    }
    
    @Override
    void park(){
        System.out.println("Parked the " + model + " Car");
    }
}

public class MethodOverriding {
    public static void main(String[] args){
        SmartPhone s = new SmartPhone();
        s.answerCall();
        s.rejectCall();
        // Parent Object is referred to Child Class, so overrding the method
        TelePhone t = new SmartPhone();
        t.answerCall();

        Car car = new Car("Toyota", "Innova");

        car.start();
        car.drive();
        car.stop();
        car.park();

        Vehicle v = new Car("Tata", "Harrier");

        v.start();
        v.park();
    }   
}