package nl.novi.automate.service;

import nl.novi.automate.dto.RideDto;
import nl.novi.automate.dto.UserDto;
import nl.novi.automate.exceptions.ExtensionNotSupportedException;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.exceptions.UserNotFoundException;
import nl.novi.automate.model.Ride;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.CarRepository;
import nl.novi.automate.repository.RideRepository;
import nl.novi.automate.repository.RoleRepository;
import nl.novi.automate.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DtoMapperService dtoMapperService;
    private final RideRepository rideRepository;


    public UserService(UserRepository userRepository, DtoMapperService dtoMapperService,  RideRepository rideRepository) {
        this.userRepository = userRepository;
        this.dtoMapperService = dtoMapperService;
        this.rideRepository = rideRepository;
    }

    public String createUser(UserDto userDto) {

        User newUser = dtoMapperService.dtoToUser(userDto);
        userRepository.save(newUser);

        return "Done";
    }

    public List<UserDto> getAllUsers() {
        List<User> wallBracketList = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<>();
        for (User wb : wallBracketList) {
            dtos.add(dtoMapperService.userToDto(wb));
        }
        return dtos;
    }

    public void addRideToUser(String username, Long id) {
        var optionalRide = rideRepository.findById(id);
        var optionalUser = userRepository.findByUsername(username);

        if (optionalRide.isPresent() && optionalUser.isPresent()) {
            var ride = optionalRide.get();
            var user = optionalUser.get();

            user.getRides().add(ride);
            ride.getUsers().add(user);

            userRepository.save(user);
            rideRepository.save(ride);
        } else {
            throw new RecordNotFoundException();
        }
    }

    public UserDto getUserByUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            User user = userRepository.findByUsername(username).get();
            UserDto dto = dtoMapperService.userToDto(user);


            return dtoMapperService.userToDto(user);
        } else {
            throw new RecordNotFoundException("geen user gevonden");
        }
    }


    public UserDto updateUser(String username, UserDto userDto) {
        if (userRepository.findByUsername(username).isPresent()) {

            User user = userRepository.findByUsername(username).get();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setEnabled(userDto.isEnabled());
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setEmail(userDto.getEmail());
            user.setBio(userDto.getBio());

            user.setFileName(userDto.getFileName());
            user.setDocFile(userDto.getDocFile());

            userRepository.save(user);

            return dtoMapperService.userToDto(user);

        } else {

            throw new RecordNotFoundException("geen user gevonden");

        }
    }

    private boolean isJpgOrJpeg(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg");
    }

    public User uploadFileDocument(String username, MultipartFile file) throws IOException, ExtensionNotSupportedException {
        if (isJpgOrJpeg(file)) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            user.setFileName(name);
            user.setDocFile(file.getBytes());

            userRepository.saveAndFlush(user);

            return user;
        } else {
            throw new ExtensionNotSupportedException("Only .jpg or .jpeg files are allowed");
        }
    }

    public Optional<User> findImage(String fileName) {
        return Optional.ofNullable(userRepository.findByFileName(fileName));
    }

    public ResponseEntity<byte[]> singleFileDownload(String fileName, HttpServletRequest request) {
        User userFile = userRepository.findByFileName(fileName);

        if (userFile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String mimeType = request.getServletContext().getMimeType(userFile.getFileName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + userFile.getFileName())
                .body(userFile.getDocFile());
    }

    public void deleteProfileImage(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFileName(null);
            user.setDocFile(null);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

public List<RideDto> findRidesForUser(String username) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (!optionalUser.isPresent()) {
        throw new UserNotFoundException("User not found with username: " + username);
    }
    User user = optionalUser.get();
    List<Ride> rides = rideRepository.findRidesForUser(user);
    return rides.stream().map(dtoMapperService::rideToDto).collect(Collectors.toList());
}


    public void createSystemUserIfNotExists() {
        String systemUsername = "System";
        User systemUser = userRepository.findByUsernameIgnoreCase(systemUsername);

        if (systemUser == null) {
            systemUser = new User();
            systemUser.setUsername(systemUsername);
            systemUser.setFirstname("App");
            systemUser.setLastname("System");
            systemUser.setEmail("system@app.com");
            systemUser.setEnabled(true);

            userRepository.save(systemUser);
        }
    }
}



