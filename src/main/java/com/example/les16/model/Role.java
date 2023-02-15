package com.example.les16.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    private String rolename;

//    @ManyToMany(mappedBy = "roles")
//    private Collection<User> users;

    // Dit is de target kant van de relatie. Er staat niks in de database
    @OneToMany(mappedBy = "user")
    @JsonIgnore
//    List<UserRoleRide> userRoleRides;
List<UserRoleRide> userRoleRides;
    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public List<UserRoleRide> getUserRoleRides() {
        return userRoleRides;
    }

    public void setUserRoleRides(List<UserRoleRide> userRoleRides) {
        this.userRoleRides = userRoleRides;
    }
}