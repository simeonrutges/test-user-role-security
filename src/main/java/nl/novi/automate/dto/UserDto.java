package nl.novi.automate.dto;

import javax.validation.constraints.*;

public class UserDto {

    @NotBlank
    public String username;
    @NotBlank
    @Size(min = 4, max = 30)
    public String password;
    @NotBlank
    public String firstname;
    @NotBlank
    public String lastname;
    @Email
    @NotNull
    public String email;
//    @Column
    public String bio;
//    @Column(nullable = false)
    public boolean enabled = true;
//    @Column
//    @Size (min = 10, max = 10)
    public int phoneNumber;

    public String fileName;

    //Mark zegt dat de docfile niet nodig is hier
    public byte[] docFile;

//    @NotEmpty
    public String[] roles;

    public CarDto car;
// Deze hoeft hier niet door de gebruiker te worden meegegeven toch?



//    public List<RideDto> rides;

    // Deze hoeft hier niet door de gebruiker te worden meegegeven toch?


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }


    //////

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }


    //
//    public Collection<Ride> getRides() {
//        return rides;
//    }
//
//    public void setRides(Collection<Ride> rides) {
//        this.rides = rides;
//    }
//    //    public void setRoles(List<Role> userRoles) {
////    }


//    public List<RideDto> getRides() {
//        return rides;
//    }
//
//    public void setRides(List<RideDto> rides) {
//        this.rides = rides;
//    }

}
