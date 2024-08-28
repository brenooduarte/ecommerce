package com.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.Data;

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
    private List<Product> products;

    public Category(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public Category() {}

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
