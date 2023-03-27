package com.example.les16.controller;

import com.example.les16.dto.AuthDto;
import com.example.les16.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager man, JwtService service) {
        this.authManager = man;
        this.jwtService = service;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> signIn(@RequestBody AuthDto authDto) {
            UsernamePasswordAuthenticationToken up =
                    new UsernamePasswordAuthenticationToken(authDto.username, authDto.password);


            try {
                Authentication auth = authManager.authenticate(up);

                UserDetails ud = (UserDetails) auth.getPrincipal();
                String token = jwtService.generateToken(ud);


                // Alleen deze aanpassingen gedaan met Sam. Voor het terugdraaien alleen de body weer in de header laten retouneren.
                return ResponseEntity.ok()
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .body(token);
//                        .body("Token generated");
            }
            catch (AuthenticationException ex) {
                return new ResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
            }
    }
}
