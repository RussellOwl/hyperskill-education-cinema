package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        String[][] seatsArray = new String[rows][seats];
        createSeats(seatsArray);

        while (!exit) {
            boolean ticketIsBought = false; // needed to give a customer a new try to enter a valid data

            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int action = scanner.nextInt();
            switch (action) {
                case 1:
                    System.out.println();
                    printSeats(seatsArray);
                    System.out.println();
                    break;
                case 2:
                    while (!ticketIsBought) {
                        System.out.println("Enter a row number:");
                        int row = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int seat = scanner.nextInt();
                        //validating entered data
                        if (row - 1 < seatsArray.length && seat - 1 < seatsArray[0].length && row > 0) {
                            if ("S".equals(seatsArray[row - 1][seat - 1])) {
                                bookingSeat(seatsArray, row, seat);
                                ticketIsBought = true;
                            } else {
                                System.out.println("That ticket has already been purchased!");
                            }
                        } else {
                            System.out.println("Wrong input!");
                        }
                        System.out.println();
                    }
                    break;
                case 3:
                    double income = (double) bookedSeats(seatsArray) / (rows * seats) * 100;

                    System.out.println("Number of purchased tickets: " + bookedSeats(seatsArray));
                    System.out.printf("Percentage: %.2f%%\n", income);
                    System.out.println("Current income: $" + currentIncome(seatsArray));
                    System.out.println("Total income: $" + profit(rows, seats));
                    System.out.println();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        }
    }
// calculating total profit from selling all seats in cinema
    public static int profit(int rows, int seats) {
        int allSeats = rows * seats;
        int profit;
        if (allSeats < 60) {
            profit = allSeats * 10;
        } else {
            profit = rows / 2 * seats * 10 + (allSeats - rows / 2 * seats) * 8;
        }
        return profit;
    }
// calculating ticket prices
    public static int ticketPrice(String[][] seatsArray, int row) {
        int ticketPrice;
        if (seatsArray.length * seatsArray[0].length < 60 || row - 1 < seatsArray.length / 2) {
            ticketPrice = 10;
        } else {
            ticketPrice = 8;
        }
        return ticketPrice;
    }
// creating array with free seats
    public static void createSeats(String[][] seatsArray) {
        for (int i = 0; i < seatsArray.length; i++) {
            Arrays.fill(seatsArray[i], "S");
        }
    }
// printing cinema seats with numbering
    public static void printSeats(String[][] seatsArray) {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 0; i < seatsArray[0].length; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < seatsArray.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < seatsArray[i].length; j++) {
                System.out.print(seatsArray[i][j] + " ");
            }
            System.out.println();
        }
    }
// "booking seat" in array and printing ticket price
    public static void bookingSeat(String[][] seatsArray, int row, int seat) {
        seatsArray[row - 1][seat - 1] = "B";
        System.out.println("Ticket price: $" + ticketPrice(seatsArray, row));
    }
// calculating booked seats (sold tickets)
    public static int bookedSeats(String[][] seatsArray) {
        int booked = 0;
        for (int i = 0; i < seatsArray.length; i++) {
            for (int j = 0; j < seatsArray[i].length; j++) {
                if ("B".equals(seatsArray[i][j])) {
                    booked++;
                }
            }
        }
        return booked;
    }

    public static int currentIncome(String[][] seatsArray) {
        int currentProfit = 0;
        for (int i = 0; i < seatsArray.length; i++) {
            for (int j = 0; j < seatsArray[i].length; j++) {
                if ("B".equals(seatsArray[i][j])) {
                    currentProfit += ticketPrice(seatsArray, i + 1);
                }
            }
        }
        return currentProfit;
    }
}
