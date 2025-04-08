package com.mycompany.myapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Telephone} entity.
 */
@Schema(description = "not an ignored comment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TelephoneDTO implements Serializable {

    private Long id;

    @NotNull
    private String codePaysISO;

    @NotNull
    private String codeTypeTelephone;

    @NotNull
    private LocalDate dateDerniereModification;

    @NotNull
    private String codeIndicatifPays;

    @NotNull
    private String numeroTelephone;

    @NotNull
    private String codeStatut;

    @NotNull
    private String dateStatut;

    @NotNull
    private String codeUsageTelephone;

    private PmEntrepriseDTO pmEntreprise;

    private PmEtablissementDTO pmEtablissement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePaysISO() {
        return codePaysISO;
    }

    public void setCodePaysISO(String codePaysISO) {
        this.codePaysISO = codePaysISO;
    }

    public String getCodeTypeTelephone() {
        return codeTypeTelephone;
    }

    public void setCodeTypeTelephone(String codeTypeTelephone) {
        this.codeTypeTelephone = codeTypeTelephone;
    }

    public LocalDate getDateDerniereModification() {
        return dateDerniereModification;
    }

    public void setDateDerniereModification(LocalDate dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
    }

    public String getCodeIndicatifPays() {
        return codeIndicatifPays;
    }

    public void setCodeIndicatifPays(String codeIndicatifPays) {
        this.codeIndicatifPays = codeIndicatifPays;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getCodeStatut() {
        return codeStatut;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public String getDateStatut() {
        return dateStatut;
    }

    public void setDateStatut(String dateStatut) {
        this.dateStatut = dateStatut;
    }

    public String getCodeUsageTelephone() {
        return codeUsageTelephone;
    }

    public void setCodeUsageTelephone(String codeUsageTelephone) {
        this.codeUsageTelephone = codeUsageTelephone;
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
        if (!(o instanceof TelephoneDTO)) {
            return false;
        }

        TelephoneDTO telephoneDTO = (TelephoneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, telephoneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelephoneDTO{" +
            "id=" + getId() +
            ", codePaysISO='" + getCodePaysISO() + "'" +
            ", codeTypeTelephone='" + getCodeTypeTelephone() + "'" +
            ", dateDerniereModification='" + getDateDerniereModification() + "'" +
            ", codeIndicatifPays='" + getCodeIndicatifPays() + "'" +
            ", numeroTelephone='" + getNumeroTelephone() + "'" +
            ", codeStatut='" + getCodeStatut() + "'" +
            ", dateStatut='" + getDateStatut() + "'" +
            ", codeUsageTelephone='" + getCodeUsageTelephone() + "'" +
            ", pmEntreprise=" + getPmEntreprise() +
            ", pmEtablissement=" + getPmEtablissement() +
            "}";
    }
}
