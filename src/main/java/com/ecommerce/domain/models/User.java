package com.ecommerce.domain.models;

import com.ecommerce.domain.dto.form.UserDTOForm;
import com.ecommerce.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    
    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<Order> orders;
    
    @Column(name = "address_type")
    private Long addressType;

    public User(UserDTOForm userDTOForm) {
        this.name = userDTOForm.getName();
        this.email = userDTOForm.getEmail();
        this.password = userDTOForm.getPassword();
        this.userType = userDTOForm.getUserType();
    }

    public void addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
    }
}
