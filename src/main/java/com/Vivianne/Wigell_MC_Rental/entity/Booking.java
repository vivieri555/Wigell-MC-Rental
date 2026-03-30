package com.Vivianne.Wigell_MC_Rental.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column()
    private LocalDateTime startDate;

    @Column()
    private LocalDateTime endDate;

    //presenteras i SEK och GBP, lokal placeholder?
    @Column()
    private Long price;

    @OneToOne(
            cascade = CascadeType.REFRESH,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "bike_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_booking_bike")
    )
    private Bike bike;

    @OneToOne(
            cascade = CascadeType.REFRESH,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "customer_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_booking_customer")
    )
    private Customer customer;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "booking_available", joinColumns = @JoinColumn(name = "available_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "available")
    private Available available;

    protected Booking() {}

    public Booking(LocalDateTime startDate, LocalDateTime endDate, Long price, Bike bike, Customer customer, Available available) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.bike = bike;
        this.customer = customer;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Available getAvailable() {
        return available;
    }
    public void setAvailable(Available available) {
        this.available = available;
    }
}
