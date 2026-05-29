import java.util.Random;
// Heirarchical Inheritance
// One Parent(Payment) & Multi Child(UPI, Card & RTGS)
class Payment{
    String user;
    // Constructor
    Payment(String user){
        this.user = user;
    }

    void payAmount(double amount){
        System.out.println(user + " has done the payment of Rs." + amount + "/-");
    }
}
// UPI Is-A Payment => Payment -> UPI
class UPI extends Payment{
    String upiId;
    
    // Constructor
    UPI(String user, String upiId){
        super(user);
        this.upiId = upiId;
    }
    
    @Override
    void payAmount(double amount){
        System.out.println("[UPI]" + user + " has done the payment of Rs." + amount + "/- via UPI ID - " + upiId);
    }
}

// Card Is-A Payment (Payment -> Card)
class Card extends Payment{
    String cardNumber;
    
    Card(String user, String cardNumber){
        super(user);
        this.cardNumber = cardNumber;
    }
    
    @Override
    void payAmount(double amount){
        System.out.println("[Card]" + user + " has done the payment of Rs." + amount + "/- via Card which ends with last 3 digits - " + cardNumber.substring(cardNumber.length() - 4));
    }
}

class RTGS extends Payment{
    Random r = new Random();
    
    String acNum;
    
    RTGS(String user, String acNum){
        super(user);
        this.acNum = acNum;
    }
    
    @Override
    void payAmount(double amount){
        System.out.println("[RTGS]" + user + " has done the payment of Rs." + amount + "/- via RTGS and the Payment Reference Number - " + r.nextInt(100000, 999999));
    }
}

public class PaymentUsecase {
    public static void main(String[] args){
        Payment p1 = new UPI("Akshay Rao", "akshayarao1551@upi");
        Payment p2 = new Card("Ajay Rao", "425454121314");
        Payment p3 = new RTGS("Shiv Shankar", "84521452114");
        
        p1.payAmount(1350.00);
        p2.payAmount(2550.00);
        p3.payAmount(30420.00);

        System.out.println("Is Payment 1 made via UPI? " + (p1 instanceof UPI));
        System.out.println("Is Payment 1 made via Card? " + (p1 instanceof Card));
        System.out.println("Is Payment 1 made via RTGS? " + (p1 instanceof RTGS));
    }
}