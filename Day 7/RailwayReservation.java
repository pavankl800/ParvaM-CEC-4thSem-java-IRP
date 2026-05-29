class SeatNotAvailableException extends Exception {
    SeatNotAvailableException(String message) {
        super(message);
    }
}

class PaymentFailedException extends Exception {
    PaymentFailedException(String message) {
        super(message);
    }
}

class RailwayBooking {
    int availableSeats = 2;
    double walletBalance = 1000;

    void bookTicket(int requestSeats, double ticketAmount) throws SeatNotAvailableException, PaymentFailedException {
        System.out.println("\nRequested Seats: " + requestSeats);

        System.out.println("Ticket Amount: Rs." + ticketAmount + "/-");

        if (requestSeats > availableSeats) {
            throw new SeatNotAvailableException("Requested seats are not available!");
        }

        if (ticketAmount > walletBalance) {
            throw new PaymentFailedException("Payment Failed due to Insufficient Balance in the wallet!");
        }

        availableSeats -= requestSeats;
        walletBalance -= ticketAmount;

        System.out.println("Ticket Booked Successfully!");

        System.out.println("Remaining Seats: " + availableSeats);

        System.out.println("Current Wallet Balance: Rs." + walletBalance + "/- \n");
    }
}

public class RailwayReservation {
    public static void main(String[] args) {
        RailwayBooking booking = new RailwayBooking();
        // Success Condition
        try {

            booking.bookTicket(1, 500);

        } catch (SeatNotAvailableException e) {

            System.out.println(
                    "Booking Failed!");

            System.out.println(
                    "Reason: "
                            + e.getMessage());

        } catch (PaymentFailedException e) {

            System.out.println(
                    "Payment Failed!");

            System.out.println(
                    "Reason: "
                            + e.getMessage());
        }

        // PAYMENT FAILURE
        try {

            booking.bookTicket(1, 800);

        } catch (SeatNotAvailableException e) {

            System.out.println(
                    "Booking Failed!");

            System.out.println(
                    "Reason: "
                            + e.getMessage() + "\n");

        } catch (PaymentFailedException e) {

            System.out.println(
                    "Payment Failed!");

            System.out.println(
                    "Reason: "
                            + e.getMessage());
        }

        // SEAT FAILURE
        try {

            booking.bookTicket(3, 200);

        } catch (SeatNotAvailableException e) {

            System.out.println(
                    "Booking Failed!");

            System.out.println(
                    "Reason: "
                            + e.getMessage());

        } catch (PaymentFailedException e) {

            System.out.println(
                    "Payment Failed!");

            System.out.println(
                    "Reason: "
                            + e.getMessage());
        }

        finally {

            System.out.println(
                    "\n Railway Booking System Closed!");
        }
    }
}