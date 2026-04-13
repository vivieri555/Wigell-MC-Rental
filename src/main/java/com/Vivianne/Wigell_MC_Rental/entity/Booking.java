package com.Vivianne.Wigell_MC_Rental.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import javax.print.attribute.HashPrintServiceAttributeSet;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private BigDecimal priceSEK;

    @Column()
    private BigDecimal totalPriceGBP;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_booking_customer")
    )
    @Valid
    private Customer customer;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "booking_available", joinColumns = @JoinColumn(name = "available_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "available")
    private Set<Available> available = new HashSet<>();

    @ManyToOne(optional = false,
    fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id",
    nullable = false,
    foreignKey = @ForeignKey(name = "fk_booking_bike"))
    @Valid
    private Bike bike;

    protected Booking() {}

    public Booking(LocalDateTime startDate, LocalDateTime endDate, BigDecimal priceSEK, BigDecimal totalPriceGBP, Bike bike,
                   Customer customer, Set<Available> available) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceSEK = priceSEK;
        this.totalPriceGBP = totalPriceGBP;
        this.bike = bike;
        this.customer = customer;
        this.available = available;
    }
    public Booking(LocalDateTime startDate, LocalDateTime endDate, Bike bike, Customer customer, Set<Available> available) {
        this.startDate = startDate;
        this.endDate = endDate;
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

    public BigDecimal getPriceSEK() {
        return priceSEK;
    }

    public void setPriceSEK(BigDecimal priceSEK) {
        this.priceSEK = priceSEK;
    }

    public BigDecimal getTotalPriceGBP() { return totalPriceGBP;}

    public void setTotalPriceGBP(BigDecimal totalPriceGBP) { this.totalPriceGBP = totalPriceGBP; }

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
    public Set<Available> getAvailable() {
        return available;
    }
    public void setAvailable(Set<Available> available) {
        this.available = available;
    }
}
