package com.ecommerce.domain.repository;

import com.ecommerce.domain.models.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}