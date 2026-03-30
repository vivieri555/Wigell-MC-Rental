package com.Vivianne.Wigell_MC_Rental.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column()
    private String username;

    //customers joina


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
            name = "mc_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_booking_mc")
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

    protected Booking() {}

    public Booking(String username, LocalDateTime startDate, LocalDateTime endDate, Long price, Bike bike, Customer customer) {
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.bike = bike;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Bike getMc() {
        return bike;
    }

    public void setMc(Bike bike) {
        this.bike = bike;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
