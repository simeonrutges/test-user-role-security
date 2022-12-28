package com.example.les16.controller;

import com.example.les16.dto.UserDto;
import com.example.les16.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UserController {
@Autowired
private UserService userService;
    private final PasswordEncoder passwordEncoder;
    // passwordEncoder staat ook in de uitwerking hier

    public UserController(PasswordEncoder encoder) {
        this.passwordEncoder = encoder;
    }
    @PostMapping("/users")
    public ResponseEntity<String> klant(@RequestBody UserDto dto) {;

        //met password encoder encoden
        dto.setPassword(passwordEncoder.encode(dto.password));

        String newUsername = userService.createUser(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).body(newUsername);
    }
}
