package com.Vivianne.Wigell_MC_Rental.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bike")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 100)
    private String gearbox;

    @Column(nullable = false, length = 15)
    private String year;

    @Column(nullable = false)
    private BigDecimal dayPrice;

    @OneToMany(mappedBy = "bike",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
@JsonIgnore
    private List<Booking> bookings = new ArrayList<>();

    protected Bike() {}

    public Bike(String brand, String model, String gearbox, String year, BigDecimal dayPrice) {
        this.brand = brand;
        this.model = model;
        this.gearbox = gearbox;
        this.year = year;
        this.dayPrice = dayPrice;
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
    public BigDecimal getDayPrice() {
        return dayPrice;
    }
    public void setDayPrice(BigDecimal dayPrice) {
        this.dayPrice = dayPrice;
    }
}
