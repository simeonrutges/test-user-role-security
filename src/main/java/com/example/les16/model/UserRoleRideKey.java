package com.example.les16.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserRoleRideKey implements Serializable {
    @Column(name = "user_id")
    private String userId;
//deze onder en boven waren Llong!
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "ride_id")
    private Long rideId;

    public UserRoleRideKey() {}

    public UserRoleRideKey(String userId, String roleId, Long rideId) {
        this.userId = userId;
        this.roleId = roleId;
        this.rideId = rideId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}
