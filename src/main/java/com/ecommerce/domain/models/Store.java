package com.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "tb_store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String domain;

    @OneToMany(mappedBy = "store")
    private Set<Address> addresses;

    @OneToMany(mappedBy = "store")
    private Set<Category> categories;

    @OneToMany(mappedBy = "store")
    private Set<User> users;

    @OneToMany(mappedBy = "store")
    private Set<Order> orders;

    @OneToMany(mappedBy = "store")
    private Set<Product> products;

}
