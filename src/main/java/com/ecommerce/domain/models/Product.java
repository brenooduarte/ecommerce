package com.ecommerce.domain.models;

import com.ecommerce.domain.dto.form.ProductDTOForm;
import com.ecommerce.utils.GlobalConstants;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "tb_product")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Column
    private String image;

    @Column
    private boolean highlight;

    @Column
    private boolean promotion;

    @Column(name = "promotion_price")
    private BigDecimal promotionPrice;

    @Column
    private boolean status;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Assessment> assessments;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @OneToMany(mappedBy = "product")
    private List<ProductOrder> orders;

    public Product(ProductDTOForm productDTOForm){
        this.name = productDTOForm.getName();
        this.price = productDTOForm.getPrice();
        this.description = productDTOForm.getDescription();
        this.image = productDTOForm.getImage();
        this.highlight = productDTOForm.isHighlight();
        this.promotion = productDTOForm.isPromotion();
        this.promotionPrice = productDTOForm.getPricePromotion();
    }

    public void addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
    }

    @PrePersist
    public void prePersist() {
    	setStatus(GlobalConstants.ACTIVE);
    }

}