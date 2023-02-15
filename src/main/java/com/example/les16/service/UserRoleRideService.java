package com.example.les16.service;

import com.example.les16.dto.RoleDto;
import com.example.les16.dto.UserDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.model.Role;
import com.example.les16.model.UserRoleRide;
import com.example.les16.model.UserRoleRideKey;
import com.example.les16.repository.RideRepository;
import com.example.les16.repository.RoleRepository;
import com.example.les16.repository.UserRoleRideRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class UserRoleRideService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRoleRideRepository userRoleRideRepository;

//    public Collection<UserDto> getUserRolesByRoleId(String roleId) {
//        Collection<UserDto> dtos = new HashSet<>();
//        Collection<UserRoleRide> userRoleRides = userRoleRideRepository.findAllByRoleId(roleId);
//        for (UserRoleRide userRoleRide : userRoleRides) {
//            User user = userRoleRide.getUser();
//            UserDto dto = new UserDto();
//
//            user.setUsername(dto.getUsername());
//            // username is de Id ???
//            user.setPassword(dto.getPassword());
//
//            dtos.add(dto);
//        }
//        return dtos;
//    }
//
//    public Collection<RoleDto> getUserRoleByUserId(String UserId) {
//        Collection<RoleDto> dtos = new HashSet<>();
//        Collection<UserRoleRide> userRoleRides = userRoleRideRepository.findAllByUserId(UserId);
//        for (UserRoleRide userRoleRide : userRoleRides) {
//            Role role = userRoleRide.getRole();
//            var dto = new RoleDto();
//
//            dto.rolename(role.getRolename());
//
//            dtos.add(dto);
//        }
//        return dtos;
//    }
//
//
//    public UserRoleRideKey addUserRole(String userId, String roleId) {
//        var userRole = new UserRoleRide();
//        if (!userRepository.existsById(userId)) {throw new RecordNotFoundException();}
//        User user = userRepository.findById(userId).orElse(null);
//        if (!roleRepository.existsById(roleId)) {throw new RecordNotFoundException();}
//        Role role = roleRepository.findById(roleId).orElse(null);
//        userRole.setUser(user);
//        userRole.setRole(role);
//        UserRoleRideKey id = new UserRoleRideKey(userId, roleId);
//        userRole.setId(id);
//        userRoleRideRepository.save(userRole);
//        return id;
//    }


    // deze 10-01 helemaal uitgecomment!!!
}
