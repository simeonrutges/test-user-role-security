package com.example.les16.controller;

import com.example.les16.dto.RoleDto;
import com.example.les16.dto.UserDto;
import com.example.les16.service.RoleService;
import com.example.les16.service.UserRoleRideService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class RoleController {

    private final RoleService roleService;

    private final UserRoleRideService userRoleRideService;

    public RoleController(RoleService roleService, UserRoleRideService userRoleRideService) {
        this.roleService = roleService;
        this.userRoleRideService = userRoleRideService;

    }

    @PostMapping("/roles")
    public String createRole(@RequestBody RoleDto dto) {
        String dto1 = roleService.createRole(dto);
        return dto1;
    }
//    @GetMapping("/roles/users/{roleId}")
//    public Collection<UserDto> getUsersByRoleId(@PathVariable("roleId") String roleId){
//        return userRoleRideService.getUserRoleByRoleId(roleId);
//    }

}
