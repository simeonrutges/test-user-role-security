package com.example.les16.dto;

import com.example.les16.model.Ride;
import com.example.les16.model.Role;

import javax.persistence.Column;
import java.util.Collection;
import java.util.List;

public class UserDto {

    public String username;
    public String password;
    public String firstname;
    public String lastname;
    public String email;
//    @Column
    public String bio;
//    @Column(nullable = false)
    public boolean enabled = true;
//    @Column
    public int phoneNumber;
    public String[] roles;
    public CarDto carDto;

    public Collection<Ride> rides;
    // kan  dit een collection zijn??


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public CarDto getCarDto() {
        return carDto;
    }

    public void setCarDto(CarDto carDto) {
        this.carDto = carDto;
    }

    public Collection<Ride> getRides() {
        return rides;
    }

    public void setRides(Collection<Ride> rides) {
        this.rides = rides;
    }
    //    public void setRoles(List<Role> userRoles) {
//    }

}
