// Sharing of Properties b/w different classes
// Derived Class will inherit the properties of Base Class
// Base Class -> Derived Class
// Parent Class -> Child Class

// Example 1:
// Base Class

import javax.print.attribute.standard.MediaSize.NA;

class A {
    void printA() {
        System.out.println("Printing from A Class");
    }
}

// Derived Class
class B extends A {
    void printB() {
        System.out.println("Printing from B Class and it can also call printA() method.");
    }
}

// Example 2:
// Animal -> Tiger
class Animal {
    String type;
    String habitate;

    void addInfo(String animalType, String place) {
        type = animalType;
        habitate = place;
    }

    void showInfo() {
        System.out.println("This is " + type + " animal, and it lives in " + habitate);
    }
}

class Tiger extends Animal {
    String name;
    int age;

    void addData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void printData() {
        System.out.println("The name of this tiger is " + name + " and it is " + age + " years old.");
    }
}

public class Inheritance {
    public static void main(String[] args) {
        A a1 = new A();
        a1.printA();

        B b1 = new B();
        // b is an object of Class B but it can also access the methods of Class A as it
        // is derived from that parent class
        b1.printA();
        b1.printB();

        // Animal Object
        Tiger tiger1 = new Tiger();
        // addInfo() & showInfo() are the methods of Animal class and currently accessed by Tiger class object - tiger1
        tiger1.addInfo("Wild", "Forest");
        tiger1.addData("Rocky", 15);
        tiger1.printData();
        tiger1.showInfo();
    }
}