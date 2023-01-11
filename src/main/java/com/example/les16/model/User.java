package com.example.les16.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="users")
public class User {
    @Id
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    //    @Column(nullable = false)
    private boolean enabled = true;
    //    @Column
    private int phoneNumber;
    //    @Column
    private String bio;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Collection<Role> roles;
    // Dit is de target kant van de relatie. Er staat niks in de database
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    Collection<UserRoleRide> userRoleRides;

    @OneToOne
    Car car;

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Collection<UserRoleRide> getUserRoleRides() {
        return userRoleRides;
    }

    public void setUserRoleRides(Collection<UserRoleRide> userRoleRides) {
        this.userRoleRides = userRoleRides;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
