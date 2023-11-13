import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

enum options {
    list, book, guests, cancel
}

public class THClient implements THCallback {
    public static void printUsage() {
        System.out.println("Usage to display a list of available seats: java THClient list <hostname>");
        System.out.println("Usage to book seats: java THClient book <hostname> <type> <number> <name>");
        System.out.println("Usage to show booked guests: java THClient guests <hostname>");
        System.out.println("Usage to cancel seats: java THClient cancel <hostname> <type> <number> <name>");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            System.exit(0);
        }
        String command = args[0];
        String host = args[1];
        String name;
        SeatType type;
        int number;
        THClient client = new THClient();

        try {
            THInterface th = (THInterface) Naming.lookup("rmi://" + host + ":7500/THInterfaceService");
            try {
                switch (options.valueOf(command)) {
                    case list:
                        System.out.println(th.listAvailableSeats(host));
                        break;
                    case book:
                        type = SeatType.toSeatType(args[2]);
                        number = Integer.parseInt(args[3]);
                        name = args[4];
                        Reservation reservationInfo;
                        reservationInfo = th.makeReservation(host, type, number, name);
                        int availableSeats = reservationInfo.getReservedSeats();
                        if (availableSeats == number)
                            System.out.println("Successful booking: " + number + " seats of type " + SeatType.toString(type) + " booked in the name of " + name + " with a total cost of " + reservationInfo.getTotalCost());
                        if (availableSeats == 0) {
                            System.out.println("Failed booking: No available seats of type " + SeatType.toString(type));
                            boolean wantsSubscribe = readAnswer("Would you like to subscribe for notifications if any reservation of type " + SeatType.toString(type) + " gets canceled?");
                            if (wantsSubscribe) {
                                th.addCallback(client, type);
                            }
                        } else if (availableSeats < number) {
                            System.out.println("Failed booking: Not enough available seats of type " + SeatType.toString(type));
                            System.out.println("Available Seats: " + reservationInfo.getReservedSeats());
                            boolean ans = readAnswer("Would you like to book only the available seats?");
                            if (ans) {
                                reservationInfo = th.makeReservation(host, type, availableSeats, name);
                                System.out.println("Successful booking");
                                System.out.println(reservationInfo.toString());
                            } else {
                                System.out.println("Booking canceled");
                            }
                            boolean wantsSubscribe = readAnswer("Would you like to subscribe for notifications if any reservation of type " + SeatType.toString(type) + " gets canceled?");
                            if (wantsSubscribe) {
                                th.addCallback(client, type);
                            }
                        }
                        break;
                    case guests:
                        System.out.println(th.showReservations(host));
                        break;
                    case cancel:
                        type = SeatType.toSeatType(args[2]);
                        number = Integer.parseInt(args[3]);
                        name = args[4];
                        System.out.println(th.cancelSeats(host, type, number, name));
                        break;
                    default:
                        System.out.println("Command line argument: " + args[0] + " is not an available option.");
                        printUsage();
                        System.exit(0);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Command line argument: " + args[0] + " is not an available option.");
                printUsage();
                System.exit(0);
            }
        } catch (MalformedURLException murle) {
            System.out.println();
            System.out.println("MalformedURLException");
            System.out.println(murle);
        } catch (RemoteException re) {
            System.out.println();
            System.out.println("RemoteException");
            System.out.println(re);
        } catch (NotBoundException nbe) {
            System.out.println();
            System.out.println("NotBoundException");
            System.out.println(nbe);
        } catch (java.lang.ArithmeticException ae) {
            System.out.println();
            System.out.println("java.lang.ArithmeticException");
            System.out.println(ae);
        }
    }

    public static boolean readAnswer(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        System.out.print("Enter 'yes' or 'no': ");
        String input = scanner.nextLine().trim();
        return input.equalsIgnoreCase("yes");
    }

    public void notifySeatCancellation(int cancelledSeats) {
        System.out.println(cancelledSeats + " seats have been canceled");
    }
}
