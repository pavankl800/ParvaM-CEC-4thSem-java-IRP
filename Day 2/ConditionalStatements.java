public class ConditionalStatements {
    public static void main(String[] args) {
        int num = 24;

        // If Condition
        if (num % 2 == 0) {
            System.out.println(num + " is an even number.");
        }

        // If-Else Condition
        if (num % 2 == 0) {
            System.out.println(num + " is an even number.");
        } else {
            System.out.println(num + " is an odd number.");
        }

        // If-Else Ladder
        if (num % 6 == 0) {
            System.out.println(num + " is divisible by both 2 & 3.");
        } else if (num % 2 == 0) {
            System.out.println(num + " is only divisible by 2.");
        } else if (num % 3 == 0) {
            System.out.println(num + " is only divisible by 3.");
        } else {  
            System.out.println(num + " is not divisible by either 2 or 3.");
        }

        int num1 = 17;
        int num2 = 33;
        // Nested If-Else Statement
        if (num > num1) {
            if (num > num2){
                System.out.println(num + " is greatest");
            } else {
                System.out.println(num2 + " is greatest");
            }
        } else {  
            if (num1 > num2){
                System.out.println(num1 + " is greatest");
            } else {
                System.out.println(num2 + " is greatest");
            }
        }
    }
}