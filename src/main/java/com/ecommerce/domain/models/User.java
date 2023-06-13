package com.ecommerce.domain.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_user")
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

    @OneToMany
    @JoinColumn(name = "assessment_id")
    private List<Assessment> assessments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> address;

    public void addAddress(UserAddress userAddress) {
        this.address.add(userAddress);
    }

    public void addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
    }
}
