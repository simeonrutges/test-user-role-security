package com.example.les16.service;

import com.example.les16.dto.UserDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.model.User;
import com.example.les16.repository.CarRepository;
import com.example.les16.repository.RideRepository;
import com.example.les16.repository.UserRoleRideRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private UserRoleRideRepository userRoleRideRepository;

    @Autowired
    private RideRepository rideRepository;

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

//    public String createUser(@RequestBody UserDto userDto) {
//        User newUser = new User();
//
////        newUser.setId(userDto.id);
//        newUser.setUsername(userDto.username);
////        newUser.setFirstname(userDto.firstname);
////        newUser.setLastname(userDto.lastname);
////        newUser.setEmail(userDto.email);
////        newUser.setBio(userDto.bio);
////        newUser.setPhoneNumber(userDto.phoneNumber);
////        newUser.setEnabled(userDto.enabled);
//        newUser.setPassword(userDto.password);
//
////        List<Role> userRoleRides = new ArrayList<>();
//        List<UserRoleRide> userRoleRides = new ArrayList<>();
//        for (String rolename : userDto.roles) {
//            Optional<UserRoleRide> or = userRoleRideRepository.findById(rolename);
//
//            userRoleRides.add(or.get());
//        }
////        newUser.setRoles(userRoleRides);
//        newUser.setRoleUsers(userRoleRides);
//
//
////        moet ik hier nog de Cars bijzetten? bv:
////        List<Ride> userRides = new ArrayList<>();
////        for (Ride ride : userDto.rides) {
////            Optional<Ride> or = rideRepository.findById(ride.getId());
////
////            userRides.add(or.get());
////            //or.get gaat ervan uit dat er inderdaad een ride is. Hoe Moet ik dit veranderen!!!
////        }
////        newUser.setRides(userRides);
//
//        userRepository.save(newUser);
//
//        return "Done";
//    }

    public String createUser(@RequestBody UserDto userDto) {
//        User newUser = userRepository.save(toUser(userDto));
//        return newUser.getUsername();

//         dit hierboven werkte wel. regel 85t/m88 evt verwijderen

        User user = toUser(userDto);
        userRepository.save(user);

        return user.getUsername();
    }



//    public List<UserDto> getUsers() {
//        List<UserDto> collection = new ArrayList<>();
//        List<User> list = userRepository.findAll();
//        for (User user : list) {
//            collection.add(fromUser(user));
//        }
//        return collection;
//    }
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


//    public String createUser(@RequestBody UserDto userDto) {
////        User newUser = new User();
//
//        List<Role> userRoleRides = new ArrayList<>();
//        for (String rolename : userDto.getRoles()) {
//            Optional<Role> or = roleRepos.findById(rolename);
//
//            userRoleRides.add(or.get());
//        }
//        userDto.setRoles(userRoleRides);
//
//        userRepository.save(toUser(userDto));
//
//        return "Done";
//    }
//
//
//
public User toUser(UserDto userDto) {

    var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.isEnabled());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setBio(userDto.getBio());

    return user;
}
    public static UserDto fromUser(User user){
// hoe komt de rol hierbij?
        var dto = new UserDto();

        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.isEnabled());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());

        return dto;
    }



    public void assignCarToUser(String username, Long carId) {
        var optionalUser = userRepository.findById(username);
        var optionalCar = carRepository.findById(carId);

        if(optionalUser.isPresent() && optionalCar.isPresent()) {
            var user = optionalUser.get();
            var car = optionalCar.get();

            user.setCar(car);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
