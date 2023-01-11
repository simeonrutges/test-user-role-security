package com.example.les16.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Collection;

@Entity
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
    private double pricePerPerson;
    private double totalRitPrice;
    private int availableSpots;
    private boolean automaticAcceptance;


    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    Collection<UserRoleRide> userRoleRides;

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

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public double getTotalRitPrice() {
        return totalRitPrice;
    }

    public void setTotalRitPrice(double totalRitPrice) {
        this.totalRitPrice = totalRitPrice;
    }

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

    public Collection<UserRoleRide> getUserRoleRides() {
        return userRoleRides;
    }

    public void setUserRoleRides(Collection<UserRoleRide> userRoleRides) {
        this.userRoleRides = userRoleRides;
    }
}
