package nl.novi.automate.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private boolean enabled = true;
    private int phoneNumber;
    @Column(length = 1000)
    private String bio;
    @Column
    private String fileName;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] docFile;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    @ManyToMany
    private List<Ride> rides;

    @OneToOne (cascade = CascadeType.REMOVE, orphanRemoval = true)
    Car car;

    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviewsWritten = new ArrayList<>();

    @OneToMany(mappedBy = "reviewedUser")
    private List<Review> reviewsReceived = new ArrayList<>();

    public User() {
        this.rides = new ArrayList<>();
    }


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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
    if (rides == null) {
        this.rides = new ArrayList<>();
    } else {
        this.rides = rides;
    }
}

    public List<Review> getReviewsWritten() {
        return reviewsWritten;
    }

    public void setReviewsWritten(List<Review> reviewsWritten) {
        this.reviewsWritten = reviewsWritten;
    }

    public List<Review> getReviewsReceived() {
        return reviewsReceived;
    }

    public void setReviewsReceived(List<Review> reviewsReceived) {
        this.reviewsReceived = reviewsReceived;
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
}
