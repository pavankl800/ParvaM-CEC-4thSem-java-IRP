// Polymorphism - Same Name Many Forms
// Method Overloading (In compile time JVM will decide to call the method)
// We can have same name for many methods by varying the return type and its parameters
// Example 1:
class Calculate{
    int findSum(int a, int b){
        int sum = a + b;
        return sum;
    }
    
    int findSum(int a, int b, int c){
        int sum = a + b + c;
        return sum;
    }
    
    float findSum(float a, float b, float c){
        float sum = a + b + c;
        return sum;
    }
    
    String findSum(String a, String b){
        return a + " + " + b + " : " + (a + b);
    }
}

// Example 2:
class Print{
    void printIt(int number){
        System.out.println("Printing the number: " + number);
    }
    
    void printIt(float decimalNumber){
        System.out.println("Printing the decimal number: " + decimalNumber);
    }

    void printIt(String message){
        System.out.println("Printing the message: " + message);
    }
    
    void printIt(int numberOfPages, int numberOfCopies){
        System.out.println("Printing " + numberOfCopies + " copies of document with " + numberOfPages + " pages.");
    }
}

public class Calculator {
    public static void main(String[] args){
        Calculate cal = new Calculate();
        int sumOf2Num =cal.findSum(20, 30);
        int sumOf3Num = cal.findSum(20, 30, 40);
        float sumOfFloat = cal.findSum(20.5f, 22.3f, 21.7f);
        String combinedWord = cal.findSum("Akshay ", "Rao");

        System.out.println("Sum of 2 Numbers: " + sumOf2Num);
        System.out.println("Sum of 3 Numbers: " + sumOf3Num);
        System.out.println("Sum of 3 Float Numbers: " + sumOfFloat);
        System.out.println("Concatenation of 2 Words: " + combinedWord);

        Print p = new Print();
        p.printIt(108);
        p.printIt(3.142f);
        p.printIt("We are implementing Method Overloading!");
        p.printIt(2,25);
    }   
}