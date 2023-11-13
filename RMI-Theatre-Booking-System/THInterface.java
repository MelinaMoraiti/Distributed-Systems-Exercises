import java.rmi.Remote;
import java.rmi.RemoteException;
public interface THInterface extends Remote 
{ 
    public String listAvailableSeats(String hostname)  throws RemoteException; 
    public Reservation makeReservation(String hostname,SeatType type,int reservedSeats,String customerName) throws RemoteException;
    public String showReservations(String hostname) throws RemoteException;
    public String cancelSeats(String hostname,SeatType type,int canceledSeats,String customerName)throws RemoteException;
    void addCallback(THCallback callback,SeatType type) throws RemoteException;
    public void notifyClients(int noOfSeats,SeatType type) throws RemoteException;
} 

