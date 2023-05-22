package nl.novi.automate.dto;

import javax.validation.constraints.NotBlank;

public class CarDto {
    public Long id;
    @NotBlank
    public String licensePlate;
    @NotBlank
    public String model;
    @NotBlank
    public String brand;


    public CarDto() {
    }

    public CarDto(Long id, String licensePlate, String model, String brand) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.model = model;
        this.brand = brand;
    }

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

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
