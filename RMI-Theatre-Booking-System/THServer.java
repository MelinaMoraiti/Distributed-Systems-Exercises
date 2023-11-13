import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class THServer {

   public THServer() 
   {
      System.out.println("RMI server started");
	    try { //special exception handler for registry creation
	        LocateRegistry.createRegistry(7500);
	        System.out.println("java RMI registry created.");
	    } catch (RemoteException e) {//do nothing, error means registry already exists
	      	System.out.println("java RMI registry already exists.");
		  }
      try {
       THInterface th = new THImpl();
       Naming.rebind("rmi://localhost:7500/THInterfaceService", th);
      } catch (Exception e) {
       System.out.println("Trouble: " + e);
      }
   }

   public static void main(String args[]) 
   {
      new THServer();
   }
}


