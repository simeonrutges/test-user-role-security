package nl.novi.automate.dto;

import javax.validation.constraints.*;
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
//    @FutureOrPresent
@FutureOrPresent(message = "De vertrekdatum moet in de toekomst liggen")
    public LocalDate departureDate;
    @FutureOrPresent
    public LocalDateTime departureDateTime;
    @NotNull
    @DecimalMin(value = "3.0", inclusive = true)
    public double pricePerPerson;
    @NotNull
//    @Min(1)
//    @Max(5)
//@Min en @Max validatie annotaties kunnen niet worden toegepast op het primitieve int type. Dit komt omdat int geen null waarde kan hebben, en @Min en @Max worden genegeerd als de waarde null is.
//    Om dit te corrigeren, moet je int vervangen door Integer
    public int pax;
    public double totalRitPrice;
    @NotNull
//    @Min(1)
//    @Max(5)
    public int availableSpots;
    public boolean automaticAcceptance;
    @Future
    public LocalTime eta;

    public String driverUsername;

    public List<UserDto> users;

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

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
