// Library to get the input from the user
import java.util.Scanner;

public class DiscountCalculator {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        float discount;
        double totalAmount;
        String discountType;

        System.out.println("Enter the amount you've purchased:");
        double amount = sc.nextDouble();
        System.out.println("Are you a member of our Store? (Reply with True or False");
        boolean isMember = sc.nextBoolean();

        if(isMember){
            if(amount > 5000) {
                discountType = "Diamond Discount Offer";    
                discount = 0.35f;
                totalAmount = amount - (amount * discount);
    
                System.out.println("Purchase Amount: " + amount + "\n Discount: " + discount + "\n Discount Type: " + discountType + "\n Total Amount after Discount: " + totalAmount);
            } else if(amount >= 3000) {
                discountType = "Gold Discount Offer";    
                discount = 0.25f;
                totalAmount = amount - (amount * discount);
    
                System.out.println("Purchase Amount: " + amount + "\n Discount: " + discount + "\n Discount Type: " + discountType + "\n Total Amount after Discount: " + totalAmount);
            } else if(amount >= 1500){
                    discountType = "Silver Discount Offer";    
                discount = 0.15f;
                totalAmount = amount - (amount * discount);
    
                System.out.println("Purchase Amount: " + amount + "\n Discount: " + discount + "\n Discount Type: " + discountType + "\n Total Amount after Discount: " + totalAmount);
            } else {
                discountType = "Bronze Discount Offer";    
                discount = 0.05f;
                totalAmount = amount - (amount * discount);
                
                System.out.println("Purchase Amount: " + amount + "\n Discount: " + discount + "\n Discount Type: " + discountType + "\n Total Amount after Discount: " + totalAmount);
            }
        } else {
            discountType = "Early Bird Offer";    
            discount = 0.01f;
            totalAmount = amount - (amount * discount);
            System.out.println("Avail the Membership for more offers. Offer Closes Soon!");
            System.out.println("Purchase Amount: " + amount + "\n Discount: " + discount + "\n Discount Type: " + discountType + "\n Total Amount after Discount: " + totalAmount);
        }
    }    
}
