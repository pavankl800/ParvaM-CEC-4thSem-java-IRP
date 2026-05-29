class InsufficientBalanceException extends Exception {
    InsufficientBalanceException(String message) {
        super(message);
    }
}

class ATM {
    double balance = 50000;

    // throws with another method similar to inheritance
    // Extending the properties of Exception using throws
    void withdraw(double amount) throws InsufficientBalanceException {
            System.out.println("Attempting Withdrawal: Rs." + amount + "/-");

        if(amount > balance) {
            // we will use throw to call the method with arguments
            // To trigger the exception we are using throw keyword
            // throw acts like an object of Exception
            throw new InsufficientBalanceException("Insufficient Account Balance! Cannot withdraw amount");
        }

        balance -= amount;

        System.out.println("Withdrawal Successful!");
        System.out.println("Current Balance: Rs." + balance + "/- \n");
    }
}

public class CustomException {
    public static void main(String[] args) {
        ATM atm = new ATM();

        try {
            atm.withdraw(20000);

            atm.withdraw(45000);
        } catch (InsufficientBalanceException e){
            System.out.println("Transaction Failed!");
            System.out.println("Reason: "+ e.getMessage());
        }
    }
}