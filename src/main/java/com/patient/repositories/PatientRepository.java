package com.patient.repositories;

import com.patient.models.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Integer> {
    boolean existsByHealthInsuranceCardId(String HealthInsuranceCard);
}
