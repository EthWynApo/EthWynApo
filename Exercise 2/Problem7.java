package com.mycompany.exec2;

import java.util.Scanner;

class Guest {
    private String guestId;
    private String fullName;

    public Guest(String guestId, String fullName) {
        this.guestId = guestId;
        this.fullName = fullName;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getFullName() {
        return fullName;
    }
}

class Room {
    private int roomNumber;
    private String roomType;
    private double nightlyRate;
    private boolean available;

    public Room(int roomNumber, String roomType, double nightlyRate) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.nightlyRate = nightlyRate;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getNightlyRate() {
        return nightlyRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean reserve() {
        if (available) {
            available = false;
            return true;
        }
        return false;
    }

    public void release() {
        available = true;
    }
}

class Reservation {
    private String reservationId;
    private Guest guest;
    private Room room;
    private int nights;

    public Reservation(String reservationId, Guest guest, Room room, int nights) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.room = room;
        this.nights = nights;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalCost() {
        return room.getNightlyRate() * nights;
    }
}

public class Exec2 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("HOTEL RESERVATION MANAGER");

        System.out.print("Enter number of rooms: ");
        int roomCount = input.nextInt();
        input.nextLine();

        Room[] rooms = new Room[roomCount];

        for (int i = 0; i < roomCount; i++) {

            System.out.println();
            System.out.println("Room " + (i + 1));

            System.out.print("Enter room number: ");
            int roomNumber = input.nextInt();
            input.nextLine();

            System.out.print("Enter room type: ");
            String roomType = input.nextLine();

            System.out.print("Enter nightly rate: ");
            double nightlyRate = input.nextDouble();
            input.nextLine();

            rooms[i] = new Room(roomNumber, roomType, nightlyRate);
        }

        System.out.println();

        System.out.print("Enter number of guests: ");
        int guestCount = input.nextInt();
        input.nextLine();

        Guest[] guests = new Guest[guestCount];

        for (int i = 0; i < guestCount; i++) {

            System.out.println();
            System.out.println("Guest " + (i + 1));

            System.out.print("Enter guest ID: ");
            String guestId = input.nextLine();

            System.out.print("Enter full name: ");
            String fullName = input.nextLine();

            guests[i] = new Guest(guestId, fullName);
        }

        System.out.println();

        System.out.print("Enter number of reservation requests: ");
        int requestCount = input.nextInt();
        input.nextLine();

        Reservation[] reservations = new Reservation[requestCount];

        int reservationCount = 0;
        double totalRevenue = 0;

        for (int i = 0; i < requestCount; i++) {

            System.out.println();
            System.out.println("Reservation Request " + (i + 1));

            System.out.print("Enter reservation ID: ");
            String reservationId = input.nextLine();

            System.out.print("Enter guest ID: ");
            String guestId = input.nextLine();

            System.out.print("Enter room number: ");
            int roomNumber = input.nextInt();

            System.out.print("Enter number of nights: ");
            int nights = input.nextInt();
            input.nextLine();

            Guest selectedGuest = null;
            Room selectedRoom = null;

            for (int j = 0; j < guests.length; j++) {
                if (guests[j].getGuestId().equals(guestId)) {
                    selectedGuest = guests[j];
                    break;
                }
            }

            for (int j = 0; j < rooms.length; j++) {
                if (rooms[j].getRoomNumber() == roomNumber) {
                    selectedRoom = rooms[j];
                    break;
                }
            }

            if (selectedGuest == null) {
                System.out.println("Reservation rejected: Guest not found.");
            }
            else if (selectedRoom == null) {
                System.out.println("Reservation rejected: Room not found.");
            }
            else if (nights < 1) {
                System.out.println("Reservation rejected: Nights must be at least 1.");
            }
            else if (!selectedRoom.isAvailable()) {
                System.out.println("Reservation rejected: Room is unavailable.");
            }
            else {

                selectedRoom.reserve();

                Reservation reservation = new Reservation(
                    reservationId,
                    selectedGuest,
                    selectedRoom,
                    nights
                );

                reservations[reservationCount] = reservation;
                reservationCount++;

                double cost = reservation.getTotalCost();
                totalRevenue = totalRevenue + cost;

                System.out.println("Reservation accepted.");
                System.out.println("Total cost: PHP " + cost);
            }
        }

        System.out.println();
        System.out.println("SUCCESSFUL RESERVATIONS");

        if (reservationCount == 0) {
            System.out.println("No successful reservations.");
        }

        for (int i = 0; i < reservationCount; i++) {

            Reservation reservation = reservations[i];

            System.out.println();
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Guest: " + reservation.getGuest().getFullName());
            System.out.println("Room: " + reservation.getRoom().getRoomNumber());
            System.out.println("Room Type: " + reservation.getRoom().getRoomType());
            System.out.println("Nights: " + reservation.getNights());
            System.out.println("Total Cost: PHP " + reservation.getTotalCost());
        }

        System.out.println();
        System.out.println("Total Expected Revenue: PHP " + totalRevenue);

        System.out.println();
        System.out.println("AVAILABLE ROOMS");

        boolean foundRoom = false;

        for (int i = 0; i < rooms.length; i++) {

            if (rooms[i].isAvailable()) {

                System.out.println(
                    "Room " + rooms[i].getRoomNumber()
                    + " - " + rooms[i].getRoomType()
                    + " - PHP " + rooms[i].getNightlyRate()
                );

                foundRoom = true;
            }
        }

        if (!foundRoom) {
            System.out.println("No rooms available.");
        }

        input.close();
    }
}
