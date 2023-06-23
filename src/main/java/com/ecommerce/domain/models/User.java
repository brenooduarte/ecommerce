package com.ecommerce.domain.models;

import java.util.List;

import com.ecommerce.domain.enums.UserType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tb_user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "user_type")
    private UserType userType;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Assessment> assessments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> address;
    
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
    
    @Column(name = "address_type")
    private Long addressType;

    public void addAddress(UserAddress userAddress) {
        this.address.add(userAddress);
    }

    public void addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
    }
}
