import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class MP3 {

    static class Ticket {
        private int ticketNumber;
        private String customerName;
        private String request;

        public Ticket(int ticketNumber, String customerName, String request) {
            this.ticketNumber = ticketNumber;
            this.customerName = customerName;
            this.request = request;
        }

        public int getTicketNumber() {
            return ticketNumber;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getRequest() {
            return request;
        }

        @Override
        public String toString() {            return "Ticket " + ticketNumber + " - " + customerName
                    + " - " + request;
        }
    }

    static Scanner scanner = new Scanner(System.in);

    static Queue<Ticket> ticketQueue = new ArrayDeque<>();
    static int nextTicketNumber = 1;

    public static int getMenuChoice() {
        while (true) {
            System.out.print("Choice: ");

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void createTicket() {
        System.out.print("Customer name: ");
        String customerName = scanner.nextLine().trim();

        System.out.print("Request: ");
        String request = scanner.nextLine().trim();

        if (customerName.isEmpty() || request.isEmpty()) {
            System.out.println("Customer name and request cannot be empty.");
            return;
        }

        Ticket newTicket = new Ticket(nextTicketNumber, customerName, request);
        ticketQueue.offer(newTicket);
        nextTicketNumber++;

        System.out.println(newTicket + " created.");
    }

    public static void serveNextTicket() {
        if (ticketQueue.isEmpty()) {
            System.out.println("No pending tickets.");
        } else {
            Ticket servedTicket = ticketQueue.poll();
            System.out.println("Served: " + servedTicket);
        }
    }

    public static void previewNextTicket() {
        if (ticketQueue.isEmpty()) {
            System.out.println("No pending tickets.");
        } else {
            Ticket nextTicket = ticketQueue.peek();
            System.out.println("Next ticket: " + nextTicket);
        }
    }

    public static void showAllTickets() {
        if (ticketQueue.isEmpty()) {
            System.out.println("No pending tickets.");
        } else {
            System.out.println("Pending tickets:");

            for (Ticket ticket : ticketQueue) {
                System.out.println(ticket);
            }
        }
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println();
            System.out.println("Customer Service Queue");
            System.out.println("1. New ticket");
            System.out.println("2. Serve next");
            System.out.println("3. Preview next");
            System.out.println("4. Show queue");
            System.out.println("0. Exit");

            choice = getMenuChoice();

            switch (choice) {
                case 1:
                    createTicket();
                    break;
                case 2:
                    serveNextTicket();
                    break;
                case 3:
                    previewNextTicket();
                    break;
                case 4:
                    showAllTickets();
                    break;
                case 0:
                    System.out.println("Program ended.");
                    break;
                default:
                    System.out.println("Invalid menu option.");
            }
        } while (choice != 0);
    }
}
