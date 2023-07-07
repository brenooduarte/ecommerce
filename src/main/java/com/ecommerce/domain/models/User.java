package com.ecommerce.domain.models;

import com.ecommerce.domain.dto.form.UserDTOForm;
import com.ecommerce.utils.GlobalConstants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_user")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @ManyToMany
    @JoinTable(name = "tb_user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    private boolean status;

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
        this.roles = userDTOForm.getRoles();
    }

    public void addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
    }

    @PrePersist
    public void prePersist() {
        setStatus(GlobalConstants.ACTIVE);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
