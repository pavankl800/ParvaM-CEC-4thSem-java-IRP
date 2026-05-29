import java.util.Random;
import java.util.Scanner;

class BankAccount{
    String custName;
    String acType;
    private int acNum;
    private double balance;
    
    Scanner sc = new Scanner(System.in);

    // setter method
    void createBankAccount(String customer, String typeOfAccount){
        custName = customer;
        acType = typeOfAccount;
        // Generate Random Account number
        Random random = new Random();
        // 7 digits random number
        int accountNum = 1000000 + random.nextInt(9000000);
        acNum = accountNum;
        balance = 0;
        
        System.out.println("Welcome to SBI, Your Account has been created, your bank account details are as follows:");
        System.out.println("-----------------------");
        System.out.println("Customer Name: " + custName);
        System.out.println("Account Type: " + acType);
        System.out.println("Account Number: " + accountNum);
        System.out.println("Account Balance: " + balance);
        System.out.println("-----------------------");
    }

    // DRY principle - Don't Repeat Yourself
    // Same code can be used multiple times 
    int getAccountNumber(){
        System.out.println("Enter your A/C Number:");
        int accNum = sc.nextInt();
        return accNum;
    }
    
    void deposit(){
        System.out.println("Deposit Money");
        System.out.println("---------------");
        int enteredAccNum = getAccountNumber();
        if(acNum == enteredAccNum){
            System.out.println("Enter the amount to be Deposited: ");
            double money = sc.nextDouble();
            balance += money;
            // balance = old balance + deposit amount
            System.out.println("Rs. " + money + "/- Amount Deposited Successfully to the A/C No.: " + acNum);
        } else {
            System.out.println("Account not found!");
        }
    }
    
    void withdraw(){
        System.out.println("Withdraw Money");
        System.out.println("---------------");
        int enteredAccNum = getAccountNumber();
        if(acNum == enteredAccNum){
            System.out.println("Enter the amount to be Withdrawn: ");
            double money = sc.nextDouble();
            balance -= money;
            // balance = old balance - withdraw amount
            System.out.println("Rs. " + money + "/- Amount Withdrawn Successfully from the A/C No.: " + acNum);
        } else {
            System.out.println("Account not found!");   
        }
    }
    
    void checkBalance() {
        System.out.println("Check Balance");
        System.out.println("---------------");
        int enteredAccNum = getAccountNumber();
        if(acNum == enteredAccNum){
            System.out.println("Account Balance: Rs." + balance + "/-");
        } else {
            System.out.println("Account not found!");   
        }
    }
}

public class BankSimulation {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount();
        account1.createBankAccount("Akshay Rao", "Savings A/c");
        account1.deposit();
        account1.deposit();
        account1.checkBalance();
        account1.withdraw();
        account1.withdraw();
        account1.checkBalance();
    }    
}