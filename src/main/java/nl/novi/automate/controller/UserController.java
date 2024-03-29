package nl.novi.automate.controller;
import nl.novi.automate.FileUploadResponse.FileUploadResponse;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.dto.UserDto;
import nl.novi.automate.exceptions.ExtensionNotSupportedException;
import nl.novi.automate.model.User;
import nl.novi.automate.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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


    @PutMapping("/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable String username, @RequestBody UserDto newUser) {

        UserDto dto = userService.updateUser(username, newUser);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/{username}/{rideId}")
    public ResponseEntity<Object> addRideToUser(@PathVariable String username, @PathVariable Long rideId){
        userService.addRideToUser(username, rideId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/single/uploadDb")
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
        try {
            User fileDocument = userService.uploadFileDocument(username, file);
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("users/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();
            String contentType = file.getContentType();
            return ResponseEntity.ok(new FileUploadResponse(fileDocument.getFileName(), url, contentType));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload file: " + e.getMessage());
        } catch (ExtensionNotSupportedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not upload file: " + e.getMessage());
        }
    }

    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        Optional<User> userWithImage = userService.findImage(fileName);

        if (!userWithImage.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return userService.singleFileDownload(fileName, request);
    }

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
