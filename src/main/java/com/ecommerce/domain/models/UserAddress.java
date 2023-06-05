package com.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tb_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tb_address")
    private Address address;

    private String addressType;

}
