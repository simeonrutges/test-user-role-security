package com.example.les16.dto;

import com.example.les16.model.User;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public class RideDto {
    public Long id;
    @NotBlank
    public String pickUpLocation;
    @NotBlank
    public String destination;
    public String route;
    public String addRideInfo;

    public LocalTime departureTime;
    @FutureOrPresent
    public LocalDate departureDate;
    @FutureOrPresent
    public LocalDateTime departureDateTime;
    @NotNull
    public double pricePerPerson;
    @NotNull
    public int pax;
    public double totalRitPrice;
    @NotNull
    public int availableSpots;
    public boolean automaticAcceptance;
    @Future
    public LocalTime eta;

    public String driverUsername;

//    public List<UserDto> users;


// deze werkte perfect voor 13/4. Nu testen zonder
//    public RideDto() {
//    }
//
//    public RideDto(Long id, String pickUpLocation, String destination, String route, String addRideInfo, LocalTime departureTime, LocalDate departureDate, LocalDateTime departureDateTime, double pricePerPerson, int pax, double totalRitPrice, int availableSpots, boolean automaticAcceptance, LocalTime eta) {
//        this.id = id;
//        this.pickUpLocation = pickUpLocation;
//        this.destination = destination;
//        this.route = route;
//        this.addRideInfo = addRideInfo;
//        this.departureTime = departureTime;
//        this.departureDate = departureDate;
//        this.departureDateTime = departureDateTime;
//        this.pricePerPerson = pricePerPerson;
//        this.pax = pax;
//        this.totalRitPrice = totalRitPrice;
//        this.availableSpots = availableSpots;
//        this.automaticAcceptance = automaticAcceptance;
//        this.eta = eta;
//    }

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

    //    public List<UserDto> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<UserDto> users) {
//        this.users = users;
//    }
}
