package com.ecommerce.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.domain.models.Product;
import com.ecommerce.infraestructure.query.ProductRepositoryQueries;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueries {

    Product findByName(String name);

}
