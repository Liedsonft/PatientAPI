package com.patient.services;

import com.patient.models.PatientModel;
import com.patient.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    final
    PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public PatientModel save(PatientModel patientModel) {
        return patientRepository.save(patientModel);
    }

    public Page<PatientModel> findAll(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    public Optional<PatientModel> findById(Integer id) {
        return patientRepository.findById(id);
    }

    @Transactional
    public void delete(PatientModel patientModel) {
        patientRepository.delete(patientModel);
    }

    public boolean existsByHealthInsuranceCardId(String healthInsuranceCard) {
        return patientRepository.existsByHealthInsuranceCardId(healthInsuranceCard);
    }
}
