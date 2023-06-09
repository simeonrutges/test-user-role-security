package nl.novi.automate.model;

import nl.novi.automate.timeValidation.ValidETA;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@ValidETA
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pickUpLocation;
    private String destination;
    private String route;
    private String addRideInfo;
    private LocalTime departureTime;
    private LocalDate departureDate;
    private LocalDateTime departureDateTime;
    private double pricePerPerson;
    private int pax;
    private double totalRitPrice;
    private int availableSpots;
    private boolean automaticAcceptance;
    private LocalTime eta;

    private String driverUsername;
    @Column(columnDefinition = "TEXT")
    private String reservedSpotsByUser = "{}";



    @ManyToMany(mappedBy = "rides")
//    @ManyToMany(mappedBy = "rides", cascade = CascadeType.REMOVE) // dit is de enigste die werkt! Maar haalt ook gelijk de user weg...
    private List<User> users;
    //    @ManyToMany(fetch = FetchType.EAGER)
//    dit wil ik er eigenlijk ook bij hebben. Bij het opvragen van een rit wil ik ook de users zien


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getAddRideInfo() {
        return addRideInfo;
    }

    public void setAddRideInfo(String addRideInfo) {
        this.addRideInfo = addRideInfo;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public int getPax() {
        return pax;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }

    public double getTotalRitPrice() {
        return totalRitPrice;
    }

    public void setTotalRitPrice(double totalRitPrice) {
        this.totalRitPrice = totalRitPrice;
    }

//    de logica voor TotalRitPrice hiervoor kan ook in de entiteit! les 17
//public double calculateTotalRitPrice() {
//    return this.quantity * this.unitprice;
//}

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public boolean isAutomaticAcceptance() {
        return automaticAcceptance;
    }

    public void setAutomaticAcceptance(boolean automaticAcceptance) {
        this.automaticAcceptance = automaticAcceptance;
    }

    public LocalTime getEta() {
        return eta;
    }

    public void setEta(LocalTime eta) {
        this.eta = eta;
    }

    public String getDriverUsername() {
        return driverUsername;
    }

    public void setDriverUsername(String driverUsername) {
        this.driverUsername = driverUsername;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getReservedSpotsByUser() {
        return this.reservedSpotsByUser;
    }

    public void setReservedSpotsByUser(String reservedSpotsByUser) {
        this.reservedSpotsByUser = reservedSpotsByUser;
    }




}
