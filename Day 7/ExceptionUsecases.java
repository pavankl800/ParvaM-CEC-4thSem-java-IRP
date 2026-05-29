public class ExceptionUsecases {

    public static void main(String[] args) {

        // Usecase1: Customer Name not found
        // NullPointerException
        try {

            String customerName = null;

            System.out.println(
                    "Customer Name: Mr./Ms."
                            + customerName
                            + " and their name length - "
                            + customerName.length());

        } catch (NullPointerException e) {

            System.out.println("Customer Name is missing!");
            System.out.println("Error: " + e.getMessage() + "\n");
        }

        // Usecase2: Error in Billing
        // ArithmeticException
        try {
            int totalAmount = 4300;
            int totalItems = 0;

            int perItemPrice = totalAmount / totalItems;

            System.out.println(
                    "Price Per Item: Rs. "
                            + perItemPrice
                            + "/-");

        } catch (ArithmeticException e) {

            System.out.println(
                    "Item count is missing to find Price per item");

            System.out.println("Error: " + e.getMessage() + "\n");
        }

        // Usecase3: Order Item Missing in Delivery
        // ArrayIndexOutOfBoundException
        try {

            String[] orders = {
                    "Jaggery",
                    "Sugar",
                    "Rock Salt",
                    "Britannia Biscuit"
            };

            System.out.println("Order Items:");

            for (int i = 0; i < 5; i++) {
                System.out.println(
                        "Item " + ((int)i+1) + ": "
                                + orders[i]);
            }

        } catch (ArrayIndexOutOfBoundsException e) {

            System.out.println(
                    "Item 4 & 5 are missing in delivery!");

            System.out.println("Error: " + e.getMessage() + "\n");
        }

        // Usecase4: Mismatch in Amount Format
        // NumberFormatException
        try {
            String amount = "Rs.12,300";

            int money = Integer.parseInt(amount);

            System.out.println(
                    "Amount in Number: "
                            + money);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Amount contains Rs. prefix and comma");

            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
}