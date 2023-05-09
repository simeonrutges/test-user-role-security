package com.example.les16.controller;
import com.example.les16.FileUploadResponse.FileUploadResponse;
import com.example.les16.dto.NotificationDto;
import com.example.les16.dto.RideDto;
import com.example.les16.dto.UserDto;
import com.example.les16.exceptions.ExtensionNotSupportedException;
import com.example.les16.model.Ride;
import com.example.les16.model.User;
import com.example.les16.security.MyUserDetails;
import com.example.les16.security.MyUserDetailsService;
import com.example.les16.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import com.example.les16.security.JwtService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
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


    // hieronder toegevoegd voor het uploaden van de profielfoto:
//////////////////////
//    @PostMapping("/single/uploadDb")
//    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) throws IOException {
//
//        // next line makes url. example "http://localhost:8080/download/naam.jpg"
//        User fileDocument = userService.uploadFileDocument(username, file);
//        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();
//
//        String contentType = file.getContentType();
//
//        return new FileUploadResponse(fileDocument.getFileName(), url, contentType );
//    }
    ////////////////////

    @PostMapping("/single/uploadDb")
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
        try {
            User fileDocument = userService.uploadFileDocument(username, file);
            // users erbij gezet hieronder
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("users/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();
            String contentType = file.getContentType();
            return ResponseEntity.ok(new FileUploadResponse(fileDocument.getFileName(), url, contentType));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload file: " + e.getMessage());
        } catch (ExtensionNotSupportedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not upload file: " + e.getMessage());
        }
    }
    ///werkt
////////////////////

    //    get for single download
//    @GetMapping("/downloadFromDB/{fileName}/{username}")
//    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, @PathVariable String username, HttpServletRequest request) {
//// dit hierboven erbij gezet:  @RequestParam("username") String username. Zie ook UserService
//        return userService.singleFileDownload(fileName, username, request);
//    }
/////////////
    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {
        //Marc heeft in HW klas staan <Resource>

        return userService.singleFileDownload(fileName, request);
    }

    ////////////

//    //    post for multiple uploads to database
//    @PostMapping("/multiple/upload/db")
//    List<FileUploadResponse> multipleUpload(@RequestParam("files") MultipartFile [] files) {
//
//        if(files.length > 7) {
//            throw new RuntimeException("to many files selected");
//        }
//
//        return userService.createMultipleUpload(files);
//
//    }

//    @GetMapping("/zipDownload/db")
//    public void zipDownload(@RequestParam("fileName") String[] files, HttpServletResponse response) throws IOException {
//
//        userService.getZipDownload(files, response);
//
//    }

//    @GetMapping("/getAll/db")
//    public Collection<User> getAllFromDB(){
//        return userService.getALlFromDB();
//    }

    @DeleteMapping("/deleteProfileImage/{username}")
    public ResponseEntity<?> deleteProfileImage(@PathVariable("username") String username) {
        try {
            userService.deleteProfileImage(username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete profile image: " + e.getMessage());
        }
    }

    @GetMapping("/{username}/rides")
    public ResponseEntity<List<RideDto>> getRidesForUser(@PathVariable("username") String username) {
        List<RideDto> rideDtos = userService.findRidesForUser(username);
        return ResponseEntity.ok(rideDtos);
    }


}
