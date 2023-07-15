package nl.novi.automate.model;

public class ReservationInfo {

    private int reservedSpots;
    private double totalPrice;

    public ReservationInfo(int reservedSpots, double totalPrice) {
        this.reservedSpots = reservedSpots;
        this.totalPrice = totalPrice;
    }

    public int getReservedSpots() {
        return reservedSpots;
    }

    public void setReservedSpots(int reservedSpots) {
        this.reservedSpots = reservedSpots;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
