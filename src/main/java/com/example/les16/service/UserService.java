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

        User newUser = transferToUser(userDto);
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
            dtos.add(transferToDto(wb));
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



    public static UserDto transferToDto (User user){

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
    public User transferToUser(UserDto userDto) {

        var user = new User();
//deze vandaag weggehaalt vanwege PUT
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

    public void addRideToUser(String username, Long id) {
        var optionalRide = rideRepository.findById(id);
        var optionalUser = userRepository.findByUsername(username);

        if(optionalRide.isPresent() && optionalUser.isPresent()) {
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
        if (userRepository.findByUsername(username).isPresent()){
            User user = userRepository.findByUsername(username).get();
            UserDto dto =transferToDto(user);
//            if(ride.getPassengers() != null){
//                dto.setPassengers(passengerService.transferToDto(ride.getPassengers().get()));
//            }
//            if(ride.getRemoteController() != null){
//                dto.setRemoteControllerDto(remoteControllerService.transferToDto(ride.getRemoteController()));
//            }

            return transferToDto(user);
        }
        else {
            throw new RecordNotFoundException("geen user gevonden");
        }
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public UserDto updateUser(String username, UserDto newUser) {
        if (userRepository.findByUsername(username).isPresent()){

            User user = userRepository.findByUsername(username).get();

            User user1 = transferToUser(newUser);
            user1.setUsername(user.getUsername());

            userRepository.save(user1);

            return transferToDto(user1);

        } else {

            throw new  RecordNotFoundException("geen user gevonden");

        }
    }
//    public List<User> getUsersByRole(String role) {
//        return userRepository.findByRoles(role);
//    }
}
