package com.example.les16.model;

import javax.persistence.*;

@Entity
public class UserRoleRide {
    // EmbeddedId zorgt dat er geen nieuwe Id wordt aangemaakt,
    // maar dat de variabelen met de @MapsId annotatie tot key worden gecombineerd.
    @EmbeddedId
    private UserRoleRideKey id;

    // Dit is de owner kan van de relatie. Er staat een foreign key in de database
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "username")
    private User user;

    // Dit is de owner kan van de relatie. Er staat een foreign key in de database
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role")
    private Role role;

    @ManyToOne
    @MapsId("rideId")
    @JoinColumn(name = "ride_id")
    private Ride ride;


    public UserRoleRideKey getId() {
        return id;
    }

    public void setId(UserRoleRideKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}
