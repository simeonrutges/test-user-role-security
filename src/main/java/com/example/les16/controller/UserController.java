package com.example.les16.controller;

import com.example.les16.dto.UserDto;
import com.example.les16.model.User;
import com.example.les16.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
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
    public ResponseEntity<String> klant(@Valid @RequestBody UserDto dto, BindingResult br) {

        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {

            //@Valid met BindingResults nog opgeven. Zie les Dto-service 2.06

            //met password encoder encoden
            dto.setPassword(passwordEncoder.encode(dto.password));

            String newUsername = userService.createUser(dto);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(newUsername).toUri();

            return ResponseEntity.created(location).body(newUsername);
        }
    }


    //vanaf hier nieuw. Nog even wachten tot dto in service werkt
    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> userDtos = userService.getAllUsers();

        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {

        UserDto optionalUser = userService.getUserByUsername(username);


        return ResponseEntity.ok().body(optionalUser);

    }
    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username) {
        //Deze werkt niet. Wel nodig?

        userService.deleteUser(username);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable String username, @RequestBody UserDto newUser) {

        UserDto dto = userService.updateUser(username, newUser);

        return ResponseEntity.ok().body(dto);
    }









    // tot hier!


    @PostMapping("/{username}/{rideId}")
    public ResponseEntity<Object> addRideToUser(@PathVariable String username, @PathVariable Long rideId){
        userService.addRideToUser(username, rideId);
        return ResponseEntity.ok().build();
        //object kan ook void zijn
    }


    @PutMapping("/{username}/{carId}")
    public void assignCarToUser(@PathVariable ("username") String username,@PathVariable ("carId") Long carId) {
        userService.assignCarToUser(username, carId);
        //@Valid moet bij de  regel voor @Requetsbody!!
        // dit was overal ID. zie televisions
    }
//    @GetMapping("/{role}")
//    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
//        List<User> users = userService.getUsersByRole(role);
//        return ResponseEntity.ok(users);
//    }
    // bovenstaande is het probleem. Heeft met de security te maken?

}
