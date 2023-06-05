package com.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Data
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cep_address")
    private String cep;

    @Column(name = "street_address")
    private String street;

    @Column(name = "number_address")
    private String number;

    @Column(name = "additional_address")
    private String additional;

    @Column(name = "neighborhood_address")
    private String neighborhood;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "city_address_id")
//    private City city;

    @ManyToOne
    @JoinColumn(name = "user_address_id")
    private User user;

}
