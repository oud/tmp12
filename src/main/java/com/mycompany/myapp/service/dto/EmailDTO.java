package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Email} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailDTO implements Serializable {

    private Long id;

    @NotNull
    private String adresseEmail;

    @NotNull
    private String codeStatut;

    @NotNull
    private LocalDate dateStatut;

    @NotNull
    private String codeUsageEmail;

    private PmEntrepriseDTO pmEntreprise;

    private PmEtablissementDTO pmEtablissement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public String getCodeStatut() {
        return codeStatut;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public LocalDate getDateStatut() {
        return dateStatut;
    }

    public void setDateStatut(LocalDate dateStatut) {
        this.dateStatut = dateStatut;
    }

    public String getCodeUsageEmail() {
        return codeUsageEmail;
    }

    public void setCodeUsageEmail(String codeUsageEmail) {
        this.codeUsageEmail = codeUsageEmail;
    }

    public PmEntrepriseDTO getPmEntreprise() {
        return pmEntreprise;
    }

    public void setPmEntreprise(PmEntrepriseDTO pmEntreprise) {
        this.pmEntreprise = pmEntreprise;
    }

    public PmEtablissementDTO getPmEtablissement() {
        return pmEtablissement;
    }

    public void setPmEtablissement(PmEtablissementDTO pmEtablissement) {
        this.pmEtablissement = pmEtablissement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailDTO)) {
            return false;
        }

        EmailDTO emailDTO = (EmailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailDTO{" +
            "id=" + getId() +
            ", adresseEmail='" + getAdresseEmail() + "'" +
            ", codeStatut='" + getCodeStatut() + "'" +
            ", dateStatut='" + getDateStatut() + "'" +
            ", codeUsageEmail='" + getCodeUsageEmail() + "'" +
            ", pmEntreprise=" + getPmEntreprise() +
            ", pmEtablissement=" + getPmEtablissement() +
            "}";
    }
}
