package com.example.les16.dto;

import com.example.les16.model.UserRoleRide;

import java.time.LocalTime;
import java.util.Collection;

public class RideDto {
    public Long id;
    public String pickUpLocation;
    public String destination;
    public String route;

    public String addRideInfo;

    public LocalTime departureTime;

    public double pricePerPerson;

    public double totalRitPrice;

    public int availableSpots;

    public boolean automaticAcceptance;

    public Collection<UserRoleRide> userRoleRides;
    // in model Ride heb ik User niet. Moet dit?


    public RideDto() {
    }

    public RideDto(Long id, String pickUpLocation, String destination, String route, String addRideInfo, LocalTime departureTime, double pricePerPerson, double totalRitPrice, int availableSpots, boolean automaticAcceptance) {
        this.id = id;
        this.pickUpLocation = pickUpLocation;
        this.destination = destination;
        this.route = route;
        this.addRideInfo = addRideInfo;
        this.departureTime = departureTime;
        this.pricePerPerson = pricePerPerson;
        this.totalRitPrice = totalRitPrice;
        this.availableSpots = availableSpots;
        this.automaticAcceptance = automaticAcceptance;
    }

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

    public Collection<UserRoleRide> getRoleUsers() {
        return userRoleRides;
    }

    public void setRoleUsers(Collection<UserRoleRide> userRoleRides) {
        this.userRoleRides = userRoleRides;
    }
}
