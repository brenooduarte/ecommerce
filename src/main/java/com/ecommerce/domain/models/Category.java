package com.ecommerce.domain.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String image;

    @OneToMany
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private List<Product> products;

    public Category(String image, String name) {
        this.image = image;
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Category() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
