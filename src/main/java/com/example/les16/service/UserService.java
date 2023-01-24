package com.example.les16.service;

import com.example.les16.dto.UserDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.model.Ride;
import com.example.les16.model.Role;
import com.example.les16.model.User;
import com.example.les16.repository.CarRepository;
import com.example.les16.repository.RideRepository;
import com.example.les16.repository.RoleRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private RoleRepository roleRepos;

    @Autowired
    private RideRepository rideRepository;

    public String createUser(UserDto userDto) {
        User newUser = toUser(userDto);

//        User newUser = new User();

//        newUser.setUsername(userDto.username);
//        newUser.setFirstname(userDto.firstname);
//        newUser.setLastname(userDto.lastname);
//        newUser.setEmail(userDto.email);
//        newUser.setBio(userDto.bio);
//        newUser.setPhoneNumber(userDto.phoneNumber);
//        newUser.setEnabled(userDto.enabled);
//        newUser.setPassword(userDto.password);
//
//        List<Role> userRoles = new ArrayList<>();
//        for (String rolename : userDto.roles) {
//            Optional<Role> or = roleRepos.findById(rolename);
//
//            userRoles.add(or.get());
//        }
//        newUser.setRoles(userRoles);
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

        userRepository.save(newUser);

        return "Done";
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
//
//
/////////////
    public static UserDto fromUser(User user){

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.firstname = user.getFirstname();
        dto.lastname = user.getLastname();
        dto.phoneNumber = user.getPhoneNumber();
        dto.email = user.getEmail();
        dto.bio = user.getBio();

        return dto;
    }
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

        List<Role> userRoles = new ArrayList<>();
        for (String rolename : userDto.roles) {
            Optional<Role> or = roleRepos.findById(rolename);

            userRoles.add(or.get());
        }
        user.setRoles(userRoles);

        userRepository.save(user);
// moet deze laatste save erbij?
        return user;
    }


    public void assignCarToUser(String username, Long carId) {
        var optionalUser = userRepository.findByUsername(username);
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
