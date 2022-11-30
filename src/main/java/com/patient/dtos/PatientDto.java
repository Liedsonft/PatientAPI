

package com.patient.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PatientDto {

    @NotBlank
    @Size(min = 7)
    private String name;

    @NotBlank
    @Size(min = 9)
    private String healthInsuranceCardId;

    @NotBlank
    private String adress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHealthInsuranceCardId() {
        return healthInsuranceCardId;
    }

    public void setHealthInsuranceCardId(String healthInsuranceCardId) {
        this.healthInsuranceCardId = healthInsuranceCardId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}