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
//    @Column(nullable = false)
    private boolean enabled = true;
//    @Column
    private int phoneNumber;
//    @Column

    private String bio;
    @Column
    private String fileName;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] docFile;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

//    @ManyToMany(fetch = FetchType.EAGER)
    // dit is wel de bedoeling. Bij bv een get van user geeft SB dan ook de collectie rides mee
        @ManyToMany
    //was gewoon many-to-many!
///
//    @ManyToMany
//    @JoinTable(name = "users_rides",
//        joinColumns = @JoinColumn(name = "user_id"),
//        inverseJoinColumns = @JoinColumn(name = "ride_id"),
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "ride_id"})})
//    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)

////
    private List<Ride> rides;
    // bij deze many-to-many controlleren of wel moet

    // deze cascade 3/4 erbij gezet: Let op: Het gebruik van CascadeType.REMOVE in een @OneToOne relatie kan onbedoelde gevolgen hebben voor de gegevensintegriteit.
    @OneToOne (cascade = CascadeType.REMOVE, orphanRemoval = true)
    Car car;

    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviewsWritten = new ArrayList<>();

    @OneToMany(mappedBy = "reviewedUser")
    private List<Review> reviewsReceived = new ArrayList<>();

//deze 26/5 erbij gezet! test
    public User() {
        this.rides = new ArrayList<>();
    }
///


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

//    public void setRides(List<Ride> rides) {
//        this.rides = rides;
//    }
public void setRides(List<Ride> rides) {
    if (rides == null) {
        this.rides = new ArrayList<>();
    } else {
        this.rides = rides;
    }
}
/// hierboven hoort bij test 26-05

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

    // fileName en Docfile  op 15/2 toegevoegd voor profielfoto. Nog geen DTO
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