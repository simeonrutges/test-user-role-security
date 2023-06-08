package nl.novi.automate.model;

public class ReservationInfo {
    // Gekozen om geen DTO klasse hiervan te maken omdat ik niet verwacht  dat de interne representatie (de ReservationInfo klasse) zou kunnen veranderen. Ook verwacht ik geen extra logica te willen toevoegen
    // bij het omzetten van de interne naar de externe representatie.
    // Daarom wil ik geen extra complexiteit toevoegen zonder veel voordelen.
    // dezelfde reden waarom ik geen @Entity hiervan gemaakt heb.
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
