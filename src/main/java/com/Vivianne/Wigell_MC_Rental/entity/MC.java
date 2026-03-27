package com.Vivianne.Wigell_MC_Rental.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MC")
public class MC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column()
    private String brand;

    @Column()
    private String model;

    @Column()
    private String gearbox;

    @Column()
    private String year;

    protected MC() {}

    public MC(String brand, String model, String gearbox, String year) {
        this.brand = brand;
        this.model = model;
        this.gearbox = gearbox;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
