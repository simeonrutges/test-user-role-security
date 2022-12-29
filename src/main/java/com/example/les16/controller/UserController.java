package com.example.les16.controller;

import com.example.les16.dto.UserDto;
import com.example.les16.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
@Autowired
private UserService userService;
    private final PasswordEncoder passwordEncoder;
    // passwordEncoder staat ook in de uitwerking hier

    public UserController(PasswordEncoder encoder) {
        this.passwordEncoder = encoder;
    }
    @PostMapping("")
    public ResponseEntity<String> klant(@RequestBody UserDto dto) {;

        //met password encoder encoden
        dto.setPassword(passwordEncoder.encode(dto.password));

        String newUsername = userService.createUser(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).body(newUsername);
    }

    //vanaf hier nieuw. Nog even wachten tot dto in service werkt
//    @GetMapping(value = "")
//    public ResponseEntity<List<UserDto>> getUsers() {
//
//        List<UserDto> userDtos = userService.getUsers();
//
//        return ResponseEntity.ok().body(userDtos);
//    }
//
//    @GetMapping(value = "/{username}")
//    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
//
//        UserDto optionalUser = userService.getUser(username);
//
//
//        return ResponseEntity.ok().body(optionalUser);
//
//    }

}
