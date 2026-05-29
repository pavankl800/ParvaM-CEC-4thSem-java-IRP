import java.util.Scanner;

public class SimpleInterestCalculator {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String choice;
        
        do {  
            System.out.println("Do you want to calculate the Simple Interest? (Reply with Yes or No"); 
            choice = sc.next();
    if(choice.equalsIgnoreCase("Yes")){
                System.out.println("Enter your Principal Amount: ");
                double principal = sc.nextDouble(); 
                System.out.println("Enter your Rate of Interest: ");
                float rate = sc.nextFloat(); 
                System.out.println("Enter your Duration: ");
                int duration = sc.nextInt();
                
                double simpleInterest = (principal * rate * duration) / 100;
                System.out.println("Simple Interest Calculator");
                System.out.println("----------------------");
                System.out.println("Principal Amount: " + principal);
                System.out.println("Rate of Interest (%): " + rate);
                System.out.println("Duration (in Yrs): " + duration);
                System.out.println("Simple Interest: " + simpleInterest);
                System.out.println("Total Amount: " + (principal + simpleInterest));
            } else {
                System.out.println("Simple Interest not Calculated! Try again later.");
            }
        } while(choice.equalsIgnoreCase("Yes"));

        sc.close();
    }
}