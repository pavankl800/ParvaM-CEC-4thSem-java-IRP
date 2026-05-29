// Interface - Common behavior across unrelated classes
// Reusable methods accross multiple classes
// Flexible to use
// During Inheritance b/w Base Class & Derived Class we use "extends" keyword for Relationship but In Interfaces we use "implements" keyword
interface Switchable {
    void turnOn();
    void turnOff();
}

interface Dimmable {
    void setDimLevel(int percent);
}

// Example 2:
interface LoginMethod{
    void login();
    void logout();    
}

class GoogleLogin implements LoginMethod{
    @Override
    public void login(){
        System.out.println("Logged in via Google Account");
    }

    @Override
    public void logout(){
        System.out.println("Logged out from Google Account");
    }
}

class OTPLogin implements LoginMethod{
    @Override
    public void login(){
        System.out.println("Logged in via OTP");
    }
    
    @Override
    public void logout(){
        System.out.println("Logged out from OTP Login");
    }
}

class EmailLogin implements LoginMethod{
    @Override
    public void login(){
        System.out.println("Logged in via Email");
    }
    
    @Override
    public void logout(){
        System.out.println("Logged out from Email Login");
    }
}

class SmartLight implements Switchable, Dimmable{
    String location;
    boolean isOn = false;
    int dimLevel = 100;

    SmartLight(String location) {
        this.location = location;
    }

    @Override
    public void turnOn(){
        isOn = true;
        System.out.println(location + " light turned ON");
    }

    @Override
    public void turnOff(){
        isOn = false;
        System.out.println(location + " light turned OFF");
    }

    @Override
    public void setDimLevel(int percent){
        if(percent >=0 && percent <= 100){
            dimLevel = percent;
            System.out.println(location + " light dimmed to " + percent + "%");
        } else {
            System.out.println("Invalid dim level! Use 0-100.");
        }
    }
}

class SmartFan implements Switchable {
    String location;

    SmartFan(String location) {
        this.location = location;
    }

    @Override
    public void turnOn(){
        System.out.println(location + " fan turned ON");
    }
    
    @Override
    public void turnOff(){
        System.out.println(location + " fan turned OFF");
    }
}

public class Interfaces {
    public static void main(String[] args){
        SmartLight bedroomLight = new SmartLight("Bedroom");
        SmartFan livingFan = new SmartFan("Living Room");

        bedroomLight.turnOn();
        bedroomLight.setDimLevel(60);
        livingFan.turnOn();
        bedroomLight.turnOff();
        livingFan.turnOff();

        LoginMethod google = new GoogleLogin();
        LoginMethod otp = new OTPLogin();
        LoginMethod email = new EmailLogin();

        google.login();
        google.logout();
        otp.login();
        otp.logout();
        email.login();
        email.logout();
    }
}