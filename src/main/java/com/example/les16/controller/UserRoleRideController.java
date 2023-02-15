package com.example.les16.controller;

import com.example.les16.service.UserRoleRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urr")
public class UserRoleRideController {
    private UserRoleRideService userRoleRideService;

    @Autowired
    public UserRoleRideController(UserRoleRideService userRoleRideService) {
        this.userRoleRideService = userRoleRideService;
    }


//    @PostMapping("/{userId}/{roleId}")
//    // wat is de userId : Long?
//    public void addUserRole(@PathVariable("userId") String userId, @PathVariable("roleId") String roleId) {
//        userRoleRideService.addUserRole(userId, roleId);
//    }
}
