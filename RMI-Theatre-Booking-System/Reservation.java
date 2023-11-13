
public class Reservation implements java.io.Serializable 
{ 
   private String customerName;
   private SeatType type;
   private int reservedSeats;
   private double totalCost;

   public  Reservation(String customerName,SeatType type,int reservedSeats,double totalCost)
   {
      this.customerName = customerName;
      this.type = type;
      this.reservedSeats = reservedSeats;
      this.totalCost = totalCost;
   }
   public String getCustomerName() {return customerName; }
   public SeatType getType() { return type; }
   public int getReservedSeats() { return reservedSeats; }
   public double getTotalCost() { return totalCost; }
   public void setReservedSeats(int newSeats) { this.reservedSeats = newSeats; }
   public void setPrice(double newPrice) { this.totalCost = newPrice; }
   public String toString() {
      String reservationDetails;
      reservationDetails = "Customer Name: " + customerName + "\n" +
              "Reserved Seats: " + reservedSeats + "\n" +
              "Seat Type: " + SeatType.toString(type) + "\n" +
              "Total Cost: " + totalCost + " euros\n" +
              "-------------------------------------------------------------------\n";
      return reservationDetails;
  }

}