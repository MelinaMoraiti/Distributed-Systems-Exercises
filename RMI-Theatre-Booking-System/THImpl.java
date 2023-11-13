import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum SeatType implements java.io.Serializable {
    ZONE_A, ZONE_B, ZONE_C, CENTRAL_BALCONY, SIDE_BOXES;

    public static String toString(SeatType type) {
        switch (type) {
            case ZONE_A:
                return "Zone A";
            case ZONE_B:
                return "Zone B";
            case ZONE_C:
                return "Zone C";
            case CENTRAL_BALCONY:
                return "Central Balcony";
            case SIDE_BOXES:
                return "Side Boxes";
            default:
                throw new IllegalArgumentException("Invalid seat type: " + type);
        }
    }

    public static SeatType toSeatType(String s) {
        switch (s) {
            case "Zone A":
                return ZONE_A;
            case "Zone B":
                return ZONE_B;
            case "Zone C":
                return ZONE_C;
            case "Central Balcony":
                return CENTRAL_BALCONY;
            case "Side Boxes":
                return SIDE_BOXES;
            default:
                throw new IllegalArgumentException("Invalid seat type: " + s);
        }
    }
}

public class THImpl extends UnicastRemoteObject implements THInterface {
    private Map<SeatType, SeatInfo> seatInfoMap;
    private Map<SeatType, List<THCallback>> notificationLists;
    private List<Reservation> reservations;

    public THImpl() throws RemoteException {
        super();
        initializeSeats();
        notificationLists = new HashMap<>();
        reservations = new ArrayList<>();
    }

    private void initializeSeats() {
        seatInfoMap = new HashMap<>();
        seatInfoMap.put(SeatType.ZONE_A, new SeatInfo(45, 100));
        seatInfoMap.put(SeatType.ZONE_B, new SeatInfo(35, 200));
        seatInfoMap.put(SeatType.ZONE_C, new SeatInfo(25, 400));
        seatInfoMap.put(SeatType.CENTRAL_BALCONY, new SeatInfo(30, 225));
        seatInfoMap.put(SeatType.SIDE_BOXES, new SeatInfo(20, 75));
    }

    public String listAvailableSeats(String hostname) throws RemoteException {
        return seatInfoMap.get(SeatType.ZONE_A).getCurrentSeats() + " seats in Zone A - Code: Zone A - Price: " + seatInfoMap.get(SeatType.ZONE_A).getPrice() + " euros\n" +
                seatInfoMap.get(SeatType.ZONE_B).getCurrentSeats() + " seats in Zone B - Code: Zone B - Price: " + seatInfoMap.get(SeatType.ZONE_B).getPrice() + " euros\n" +
                seatInfoMap.get(SeatType.ZONE_C).getCurrentSeats() + " seats in Zone C - Code: Zone C - Price: " + seatInfoMap.get(SeatType.ZONE_C).getPrice() + " euros\n" +
                seatInfoMap.get(SeatType.CENTRAL_BALCONY).getCurrentSeats() + " seats in Central Balcony - Code: Central Balcony - Price: " + seatInfoMap.get(SeatType.CENTRAL_BALCONY).getPrice() + " euros\n" +
                seatInfoMap.get(SeatType.SIDE_BOXES).getCurrentSeats() + " seats in Side Boxes - Code: Side Boxes - Price: " + seatInfoMap.get(SeatType.SIDE_BOXES).getPrice() + " euros\n";
    }

    public synchronized Reservation makeReservation(String hostname, SeatType type, int reservedSeats, String customerName) throws RemoteException {
        Reservation newReservation;
        if (seatInfoMap.get(type).getCurrentSeats() >= reservedSeats) {
            double cost = seatInfoMap.get(type).getPrice() * reservedSeats;
            newReservation = new Reservation(customerName, type, reservedSeats, cost);
            reservations.add(newReservation);
            seatInfoMap.get(type).setCurrentSeats(seatInfoMap.get(type).getCurrentSeats() - reservedSeats);
        } else {
            if (seatInfoMap.get(type).getCurrentSeats() > 0) {
                newReservation = new Reservation(customerName, type, seatInfoMap.get(type).getCurrentSeats(), seatInfoMap.get(type).getCurrentSeats() * seatInfoMap.get(type).getPrice());
            } else {
                newReservation = new Reservation(customerName, type, 0, 0);
            }
        }
        return newReservation;
    }

    public String showReservations(String hostname) throws RemoteException {
        StringBuilder text = new StringBuilder();
        for (Reservation reservation : reservations) 
            text.append(reservation.toString()).append("\n");
        return text.toString();
    }

    public synchronized String cancelSeats(String hostname, SeatType type, int canceledSeats, String customerName) throws RemoteException {
        String message = "Cancellation Failure: Reservation not found";
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            if (reservation.getCustomerName().equals(customerName) && reservation.getType() == type) {
                if (canceledSeats > 0) {
                    if (reservation.getReservedSeats() > canceledSeats) {
                        seatInfoMap.get(type).setCurrentSeats(seatInfoMap.get(type).getCurrentSeats() + canceledSeats);
                        notifyClients(canceledSeats, reservation.getType());
                        reservation.setReservedSeats(reservation.getReservedSeats() - canceledSeats);
                        reservation.setPrice(reservation.getReservedSeats() * seatInfoMap.get(type).getPrice());
                        message = "Remaining seats: " + reservation.getReservedSeats() + "\nUpdated Reservation:\n" + reservation.toString();
                    } else if (reservation.getReservedSeats() == canceledSeats) {
                        seatInfoMap.get(type).setCurrentSeats(seatInfoMap.get(type).getCurrentSeats() + canceledSeats);
                        notifyClients(canceledSeats, reservation.getType());
                        reservations.remove(i);
                        message = "Cancellation Successful: Reservation and all seats canceled";
                    } else {
                        message = "Cancellation Failure: Canceled seats exceed the reserved seats";
                    }
                }
            }
        }
        return message;
    }

    public synchronized void notifyClients(int noOfSeats, SeatType type) throws RemoteException {
        List<THCallback> subscribers = notificationLists.get(type);
        if (subscribers != null) {
            for (THCallback subscribedClient : subscribers) {
                try {
                    subscribedClient.notifySeatCancellation(noOfSeats);
                } catch (RemoteException e) {
                    subscribers.remove(subscribedClient);
                }
            }
        }
    }

    public void addCallback(THCallback callback, SeatType type) throws RemoteException {
        List<THCallback> subscribers = notificationLists.get(type);
        if (subscribers == null) {
            subscribers = new ArrayList<>();
            notificationLists.put(type, subscribers);
        }
        subscribers.add(callback);
    }
}
