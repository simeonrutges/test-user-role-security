package com.example.les16.model;

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
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

//    @ManyToMany(fetch = FetchType.EAGER)
    // dit is wel de bedoeling. Bij bv een get van user geeft SB dan ook de collectie rides mee
    @ManyToMany
    private Collection<Ride> rides;
    // bij deze many-to-many controlleren of wel moet
    @OneToOne
    Car car;

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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Collection<Ride> getRides() {
        return rides;
    }

    public void setRides(Collection<Ride> rides) {
        this.rides = rides;
    }
}
