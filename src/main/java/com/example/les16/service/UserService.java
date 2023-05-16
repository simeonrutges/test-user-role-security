package com.example.les16.service;

import com.example.les16.dto.RideDto;
import com.example.les16.dto.UserDto;
import com.example.les16.exceptions.ExtensionNotSupportedException;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.exceptions.UserNotFoundException;
import com.example.les16.model.Ride;
import com.example.les16.model.User;
import com.example.les16.repository.CarRepository;
import com.example.les16.repository.RideRepository;
import com.example.les16.repository.RoleRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.http.HttpHeaders;
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
    private final CarRepository carRepository;
    private final CarService carService;
    private final RoleRepository roleRepos;
//    private final RideService rideService;
    private final DtoMapperService dtoMapperService;
    private final RideRepository rideRepository;


    public UserService(UserRepository userRepository, CarRepository carRepository, CarService carService, RoleRepository roleRepos, DtoMapperService dtoMapperService,  RideRepository rideRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.carService = carService;
        this.roleRepos = roleRepos;
//        this.rideService = rideService;
        this.dtoMapperService = dtoMapperService;
        this.rideRepository = rideRepository;
    }

    public String createUser(UserDto userDto) {

//        User newUser = transferToUser(userDto);
        User newUser = dtoMapperService.transferToUser(userDto);
        userRepository.save(newUser);

        return "Done";
    }

    //    public String createUser(@RequestBody UserDto userDto) {
////        User newUser = new User();
//
//        List<Role> userRoles = new ArrayList<>();
//        for (String rolename : userDto.getRoles()) {
//            Optional<Role> or = roleRepos.findById(rolename);
//
//            userRoles.add(or.get());
//        }
//        userDto.setRoles(userRoles);
//
//        userRepository.save(toUser(userDto));
//
//        return "Done";
//    }


    public List<UserDto> getAllUsers() {
        List<User> wallBracketList = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<>();
        for (User wb : wallBracketList) {
            dtos.add(dtoMapperService.userToDto(wb));
        }
        return dtos;
    }

//
//    public UserDto getUser(String username) {
//        UserDto dto = new UserDto();
//        Optional<User> user = userRepository.findById(username);
//        if (user.isPresent()){
//            dto = fromUser(user.get());
//        }else {
//            throw new UsernameNotFoundException(username);
//        }
//        return dto;
//    }


    //////////////////////////////////////// hieronder evt terug!!


//    public static UserDto transferToDto(User user) {
//
//        var dto = new UserDto();
//
//        dto.username = user.getUsername();
//        dto.password = user.getPassword();
//        dto.enabled = user.isEnabled();
//        dto.firstname = user.getFirstname();
//        dto.lastname = user.getLastname();
//        dto.phoneNumber = user.getPhoneNumber();
//        dto.email = user.getEmail();
//        dto.bio = user.getBio();
//
//
//// als het niet meer werkt regel 101 en 116-121 weghalen. En uncommenten: r104 t/m 111!!!!
//        dto.setRoles(getRoleNames((List<Role>) user.getRoles()));
//
//////////////////
////         nieuwe regel om roles in te stellen
////        dto.roles = user.getRoles().stream()
////                .map(Role::getRolename)
////                .toArray(String[]::new);
//        ////////////////
//
//// test 13/4
////        dto.setRides(user.getRides().stream()
////                .map(rideService::transferToDto) // gebruik de rideService om de methode aan te roepen
////                .collect(Collectors.toList()));
//
//
//
//// 23/4 erbij
//        dto.fileName = user.getFileName();
//        dto.docFile = user.getDocFile();
//
//        return dto;
//    }

//    private static String[] getRoleNames(List<Role> roles) {
//        return roles.stream()
//                .map(Role::getRolename)
//                .toArray(String[]::new);
//    }


//    public User transferToUser(UserDto userDto) {
//
//        var user = new User();
////deze vandaag weggehaalt vanwege PUT
//        user.setUsername(userDto.getUsername());
//        user.setPassword(userDto.getPassword());
//        user.setEnabled(userDto.isEnabled());
//        user.setFirstname(userDto.getFirstname());
//        user.setLastname(userDto.getLastname());
//        user.setPhoneNumber(userDto.getPhoneNumber());
//        user.setEmail(userDto.getEmail());
//        user.setBio(userDto.getBio());
//
//        // 23/4 erbij
//        user.setFileName(userDto.getFileName());
//        user.setDocFile(user.getDocFile());
//
//        List<Role> userRoles = new ArrayList<>();
//        for (String rolename : userDto.roles) {
//            Optional<Role> or = roleRepos.findById(rolename);
//
//            userRoles.add(or.get());
//        }
//        user.setRoles(userRoles);
//
//        //test 13/4
////        List<Ride> rides = userDto.getRides().stream()
////                .map(rideService::transferToRide)
////                .collect(Collectors.toList());
////        user.setRides(rides);
//        //
//
//        userRepository.save(user);
//// moet deze laatste save erbij?
//        return user;
//    }


    /////////////////////////  hierboven evt terug!

// deze hieronder weer terugzetten:
    public void assignCarToUser(String username, Long carId) {
        var optionalUser = userRepository.findByUsername(username);
        var optionalCar = carRepository.findById(carId);

        if (optionalUser.isPresent() && optionalCar.isPresent()) {
            var user = optionalUser.get();
            var car = optionalCar.get();

            user.setCar(car);
            userRepository.save(user);

        } else {
            throw new RecordNotFoundException();
        }
    }

//    @Transactional
//    public void assignCarToUser(String username, Long carId) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//        Car car = carRepository.findById(carId)
//                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
//        user.setCar(car);
//        userRepository.save(user);
//    }


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

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
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

//            23/4 erbij
            user.setFileName(userDto.getFileName());
            user.setDocFile(userDto.getDocFile());
//



            userRepository.save(user);

            return dtoMapperService.userToDto(user);

        } else {

            throw new RecordNotFoundException("geen user gevonden");

        }
    }


//    public List<User> getUsersByRole(String role) {
//        return userRepository.findByRoles(role);
//    }


    //hieronder met profielfoto toegevoegd

    private boolean isJpgOrJpeg(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg");
    }

    public User uploadFileDocument(String username, MultipartFile file) throws IOException, ExtensionNotSupportedException {
        ///// dit er als laatste bijgeprobeerd: middag
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
    // werkt!
        //////
// deze werkt sowieso:
//        if (isJpgOrJpeg(file)) {
//            String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//            User fileDocument = new User();
//            fileDocument.setUsername(username); // instellen van de gebruikersnaam als ID. ids for this class must be manually assigned before calling save() Hier zit het probleem!
//            fileDocument.setFileName(name);
//            fileDocument.setDocFile(file.getBytes());
//
//            userRepository.saveAndFlush(fileDocument);
//
//            return fileDocument;
//        } else {
//            throw new ExtensionNotSupportedException("Only .jpg or .jpeg files are allowed");
//        }
//    }

        ///////

    public ResponseEntity<byte[]> singleFileDownload(String fileName, HttpServletRequest request) {
//        String username, stond hierboven nog bij
        User userFile = userRepository.findByFileName(fileName);

//        this mediaType decides witch type you accept if you only accept 1 type
//        MediaType contentType = MediaType.IMAGE_JPEG;
//        this is going to accept multiple types

        String mimeType = request.getServletContext().getMimeType(userFile.getFileName());

//        for download attachment use next line
//        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
//        for showing image in browser
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + userFile.getFileName()).body(userFile.getDocFile());

    }
//    public void getZipDownload(String[] files, HttpServletResponse response) throws IOException {
//        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
//            Arrays.stream(files).forEach(file -> {
//                try {
//                    createZipEntry(file, zos);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            zos.finish();
//
//            response.setStatus(200);
//            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=zipfile");
//        }
//    }
//
//    public Resource downLoadFileDatabase(String fileName) {
//
//        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(fileName).toUriString();
//
//        Resource resource;
//
//        try {
//            resource = new UrlResource(url);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Issue in reading the file", e);
//        }
//
//        if(resource.exists()&& resource.isReadable()) {
//            return resource;
//        } else {
//            throw new RuntimeException("the file doesn't exist or not readable");
//        }
//    }
//
//    public void createZipEntry(String file, ZipOutputStream zos) throws IOException {
//
//        Resource resource = downLoadFileDatabase(file);
//        ZipEntry zipEntry = new ZipEntry(Objects.requireNonNull(resource.getFilename()));
//        try {
//            zipEntry.setSize(resource.contentLength());
//            zos.putNextEntry(zipEntry);
//
//            StreamUtils.copy(resource.getInputStream(), zos);
//
//            zos.closeEntry();
//        } catch (IOException e) {
//            System.out.println("some exception while zipping");
//        }
//    }

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

//    public List<RideDto> findRidesForUser(String username) {
//        Optional<User> optionalUser = userRepository.findByUsername(username);
//        if (!optionalUser.isPresent()) {
//            throw new UserNotFoundException("User not found with username: " + username);
//        }
//        User user = optionalUser.get();
//        List<Ride> rides = rideRepository.findRidesForUser(user);
//        return rides.stream().map(rideService::transferToDto).collect(Collectors.toList());
//    }
public List<RideDto> findRidesForUser(String username) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (!optionalUser.isPresent()) {
        throw new UserNotFoundException("User not found with username: " + username);
    }
    User user = optionalUser.get();
    List<Ride> rides = rideRepository.findRidesForUser(user);
//    return rides.stream().map(dtoMapperService::rideToRideDto).collect(Collectors.toList());
    return rides.stream().map(dtoMapperService::userToDto).collect(Collectors.toList());
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



