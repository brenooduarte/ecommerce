package com.ecommerce.domain.models;

import com.ecommerce.domain.dto.form.AssessmentDTOForm;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tb_assessment")
@NoArgsConstructor
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comment;

    @Column
    private String score;

    public Assessment(AssessmentDTOForm assessmentDTOForm) {
        if (assessmentDTOForm.getComment() != null)
            this.comment = assessmentDTOForm.getComment();
        if (assessmentDTOForm.getScore() != null)
            this.score = assessmentDTOForm.getScore();
    }
}
