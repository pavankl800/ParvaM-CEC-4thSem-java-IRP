public class Operators {
    public static void main(String[] args){
        int num1 = 20;
        int num2 = 35;

        System.out.println("Arithematic Operators:");
        System.out.println("Sum of " + num1 + " and " + num2 + ": " + (num1 + num2));
        System.out.println("Difference of " + num1 + " and " + num2 + ": " + (num1 - num2));
        System.out.println("Product of " + num1 + " and " + num2 + ": " + (num1 * num2));
        System.out.println("Quotient of " + num2 + " and " + num1 + ": " + (num2 / num1));
        System.out.println("Remainder of " + num2 + " and " + num1 + ": " + (num2 % num1));
        System.out.println("Relational Operators:");
        System.out.println(num1 + " is greater than " + num2 + ": " + (num1 > num2) );
        System.out.println(num1 + " is smaller than " + num2 + ": " + (num1 < num2) );
        System.out.println(num1 + " is equal to " + num2 + ": " + (num1 == num2) );
        System.out.println(num1 + " is not equal to " + num2 + ": " + (num1 != num2) );

        System.out.println("Unary Operators:");
        System.out.println("num1 value Before Increment: " + num1);
        num1++;
        // In the printing step, the value will be incremented
        System.out.println("After Post Increment, num1 becomes:  " + num1);
        // In the same step, the value will be incremented
        ++num1;
        System.out.println("After Pre-Increment, num1 becomes:  " + num1);   

        System.out.println("Ternary Operator:");
        
        // Syntax for Ternary Operator:
        // (condition) ? "True" : "False"
        
        String result = (num1 > num2) ? "num1 is greater than num2" : "num2 is greater than num1";
        
        System.out.println(result);

        String isEven = (num1 % 2 == 0) ? "num1 is even" : "num1 is odd";

        System.out.println(isEven);
    }
}