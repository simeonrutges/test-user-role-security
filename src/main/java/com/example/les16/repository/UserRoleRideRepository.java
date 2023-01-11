package com.example.les16.repository;

import com.example.les16.model.UserRoleRide;
import com.example.les16.model.UserRoleRideKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserRoleRideRepository extends JpaRepository<UserRoleRide, UserRoleRideKey> {
    Optional<UserRoleRide> findById(String rolename);
    // deze hierboven erbij gegenereerd


    // custom query om alle TelevisionWallBrackets te vinden die bij een bepaalde tv horen
//    Collection<UserRoleRide> findAllByUserId(String userId);
//    // custom query om alle TelevisionWallBrackets te vinden die bij een bepaalde wallbracket horen
//    Collection<UserRoleRide> findAllByRoleId(String roleId);

    Collection<UserRoleRide> findAllByUserUsername(String username);
    Collection<UserRoleRide> findAllByRoleRolename(String rolename);
    Collection<UserRoleRide> findAllByRideId(Long rideId);


}
