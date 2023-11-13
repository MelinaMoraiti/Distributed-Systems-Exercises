
public class SeatInfo implements java.io.Serializable 
{ 
   private double price;
   private  int currentSeats;
   public  SeatInfo(double price, int currentSeats)
   {
      this.price = price;
      this.currentSeats = currentSeats;
   }
   public double getPrice() { return price; }
   public int getCurrentSeats() { return currentSeats; }
   public void setCurrentSeats(int newSeats) { this.currentSeats = newSeats; }
}
