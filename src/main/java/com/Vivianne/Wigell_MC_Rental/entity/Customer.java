package com.Vivianne.Wigell_MC_Rental.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer",
uniqueConstraints = {
       @UniqueConstraint(
        name = "uk_customer_kc_user_id", columnNames = {"keycloak_user_id"}) },
        indexes = {
        @Index(name = "idx_customer_kc_user_id", columnList = "keycloak_user_id")
        } )
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column()
    private String phone;

    @ManyToMany
    @JoinTable(
            name = "customer_addresses",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @Valid
    private List<Address> addresses = new ArrayList<>();

    @Column(unique = true)
    private String username;

    @Column(unique = true, length = 36)
    private String keycloakUserId;

    protected Customer() {}

    public Customer(String firstName, String lastName, String phone, List<Address> addresses,
                    String username, String keycloakUserId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.addresses = addresses;
        this.username = username;
        this.keycloakUserId = keycloakUserId;
    }
    public Customer(String firstName, String lastName, String phone,
                    List<Address> addresses, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.addresses = addresses;
        this.username = username;
    }
    public Long getId() {
        return id;
    }
    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public List<Address> getAddress() {
        return addresses;
    }
    public void setAddress(List<Address> addresses) {
        this.addresses = addresses;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getKeycloakUserId() { return keycloakUserId; }
    public void setKeycloakUserId(String keycloakUserId) {
        this.keycloakUserId = keycloakUserId;
    }
}
