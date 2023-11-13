import java.rmi.Remote;
import java.rmi.RemoteException;
public interface THCallback extends Remote {
    public void notifySeatCancellation(int cancelledSeats) throws RemoteException;
    
}