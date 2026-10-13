package interactivedronesystem;

import java.util.Scanner;

class InvalidCargoException extends Exception {
    public InvalidCargoException(String message) {
        super(message);
    }
}

public class InteractiveDroneSystem {
    private static final double[] cargoWeights = {12.5, 8.0, 15.2, 10.4};

    public static void inspectCargoSlot(int slotIndex)
            throws InvalidCargoException {
        if (slotIndex < 0 || slotIndex >= cargoWeights.length) {
            throw new InvalidCargoException(
                    "Cargo slot " + slotIndex
                    + " is invalid. Choose a slot from 0 to 3.");
        }

        System.out.println("Cargo weight in slot " + slotIndex
                + ": " + cargoWeights[slotIndex] + " kg");
    }

    public static double calculateEfficiency(
            double totalEnergyUsed, double distanceTraveled) {
        if (distanceTraveled == 0.0) {
            throw new ArithmeticException(
                    "Distance cannot be 0.0 when calculating efficiency.");
        }

        return totalEnergyUsed / distanceTraveled;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Smart Drone Control Console ===");
            System.out.println("1. Inspect Cargo Slot");
            System.out.println("2. Calculate Flight Efficiency");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter cargo slot index (0-3): ");
                        int slotIndex = Integer.parseInt(scanner.nextLine());
                        inspectCargoSlot(slotIndex);
                        break;

                    case 2:
                        System.out.print("Enter total energy used (Wh): ");
                        double totalEnergyUsed =
                                Double.parseDouble(scanner.nextLine());

                        System.out.print("Enter distance traveled (km): ");
                        double distanceTraveled =
                                Double.parseDouble(scanner.nextLine());

                        double efficiency = calculateEfficiency(
                                totalEnergyUsed, distanceTraveled);

                        System.out.println("Energy consumption: "
                                + efficiency + " Wh/km");
                        break;

                    case 3:
                        System.out.println("Exiting drone control console.");
                        running = false;
                        break;

                    default:
                        System.out.println(
                                "Invalid option. Choose 1, 2, or 3.");
                }

            } catch (InvalidCargoException e) {
                System.out.println("Cargo loading error: " + e.getMessage());

            } catch (ArithmeticException e) {
                System.out.println(
                        "Efficiency calculation error: " + e.getMessage());

            } catch (NumberFormatException e) {
                System.out.println(
                        "Input error: Enter a valid number.");
            }
        }

        scanner.close();
    }
}
