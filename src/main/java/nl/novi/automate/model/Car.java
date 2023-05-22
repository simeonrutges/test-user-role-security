package nl.novi.automate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Car {
    //is push test-user-role-sec branch gelukt?
    // dit staat alleen in de foto upload
    // komt dit er dan ook bij?
    //laatste test
    @Id
    @GeneratedValue
    private Long id;
    private String licensePlate;
    private String model;
    private String brand;

    // cascade erbij gezet 3/4. als user delete wordt dan car ook: , cascade = CascadeType.ALL
    @OneToOne(mappedBy = "car")
            @JsonIgnore
    User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String name) {
        this.model = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
