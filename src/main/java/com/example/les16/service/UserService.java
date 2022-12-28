package com.example.les16.service;

import com.example.les16.dto.UserDto;
import com.example.les16.model.Role;
import com.example.les16.model.User;
import com.example.les16.repository.RoleRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepos;

    public String createUser(@RequestBody UserDto userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.username);

        newUser.setVoornaam(userDto.voornaam);
        newUser.setAchternaam(userDto.achternaam);
        newUser.setEmail(userDto.email);

        newUser.setPassword(userDto.password);

        List<Role> userRoles = new ArrayList<>();
        for (String rolename : userDto.roles) {
            Optional<Role> or = roleRepos.findById(rolename);

            userRoles.add(or.get());
        }
        newUser.setRoles(userRoles);

        userRepository.save(newUser);

        return "Done";
    }


}
