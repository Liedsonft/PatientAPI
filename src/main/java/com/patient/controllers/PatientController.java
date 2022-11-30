package com.patient.controllers;

import com.patient.dtos.PatientDto;
import com.patient.models.PatientModel;
import com.patient.services.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;


@RestController
@CrossOrigin (origins = "*", maxAge = 3600)
@RequestMapping("/patient")
public class PatientController {

    final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Object> savePatient (@RequestBody @Valid PatientDto patientDto) {

        if (patientService.existsByHealthInsuranceCardId(patientDto.getHealthInsuranceCardId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: HelathInsuranceCardId já está em uso!");
        }

        var patientModel = new PatientModel();
        BeanUtils.copyProperties(patientDto, patientModel);
        patientModel.setCreateAt(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(patientModel));

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<Page<PatientModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findAll(pageable));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public  ResponseEntity<Object> getOnePatient(@PathVariable(value = "id") Integer id) {
        Optional<PatientModel> patientModelOptional = patientService.findById(id);
        if (!patientModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(patientModelOptional.get());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> deletePatient (@PathVariable(value = "id") Integer id) {
        Optional<PatientModel> patientModelOptional = patientService.findById(id);
        if (!patientModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
        patientService.delete(patientModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Dados do paciente apagados com sucesso!");
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public  ResponseEntity<Object> updatePatient (@PathVariable(value = "id") Integer id,
                                                  @RequestBody @Valid PatientDto patientDto) {
        Optional<PatientModel> patientModelOptional = patientService.findById(id);
        if (!patientModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
        var patientModel = patientModelOptional.get();
        patientModel.setName(patientDto.getName());
        patientModel.setAdress(patientDto.getAdress());
        patientModel.setHealthInsuranceCardId(patientDto.getHealthInsuranceCardId());
        return ResponseEntity.status(HttpStatus.OK).body(patientService.save(patientModel));
    }
}