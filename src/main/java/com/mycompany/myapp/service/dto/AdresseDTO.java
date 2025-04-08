package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Adresse} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdresseDTO implements Serializable {

    private Long id;

    @NotNull
    private String codePaysISO;

    @NotNull
    private String codePostal;

    @NotNull
    private LocalDate dateDerniereModification;

    @NotNull
    private String libelleCommune;

    @NotNull
    private String ligne1;

    @NotNull
    private String ligne2;

    @NotNull
    private String ligne3;

    @NotNull
    private String ligne4;

    @NotNull
    private String ligne5;

    @NotNull
    private String ligne6;

    @NotNull
    private String ligne7;

    @NotNull
    private String nombreCourriersPND;

    @NotNull
    private String codeUsageAdresse;

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

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public LocalDate getDateDerniereModification() {
        return dateDerniereModification;
    }

    public void setDateDerniereModification(LocalDate dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
    }

    public String getLibelleCommune() {
        return libelleCommune;
    }

    public void setLibelleCommune(String libelleCommune) {
        this.libelleCommune = libelleCommune;
    }

    public String getLigne1() {
        return ligne1;
    }

    public void setLigne1(String ligne1) {
        this.ligne1 = ligne1;
    }

    public String getLigne2() {
        return ligne2;
    }

    public void setLigne2(String ligne2) {
        this.ligne2 = ligne2;
    }

    public String getLigne3() {
        return ligne3;
    }

    public void setLigne3(String ligne3) {
        this.ligne3 = ligne3;
    }

    public String getLigne4() {
        return ligne4;
    }

    public void setLigne4(String ligne4) {
        this.ligne4 = ligne4;
    }

    public String getLigne5() {
        return ligne5;
    }

    public void setLigne5(String ligne5) {
        this.ligne5 = ligne5;
    }

    public String getLigne6() {
        return ligne6;
    }

    public void setLigne6(String ligne6) {
        this.ligne6 = ligne6;
    }

    public String getLigne7() {
        return ligne7;
    }

    public void setLigne7(String ligne7) {
        this.ligne7 = ligne7;
    }

    public String getNombreCourriersPND() {
        return nombreCourriersPND;
    }

    public void setNombreCourriersPND(String nombreCourriersPND) {
        this.nombreCourriersPND = nombreCourriersPND;
    }

    public String getCodeUsageAdresse() {
        return codeUsageAdresse;
    }

    public void setCodeUsageAdresse(String codeUsageAdresse) {
        this.codeUsageAdresse = codeUsageAdresse;
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
        if (!(o instanceof AdresseDTO)) {
            return false;
        }

        AdresseDTO adresseDTO = (AdresseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adresseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdresseDTO{" +
            "id=" + getId() +
            ", codePaysISO='" + getCodePaysISO() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", dateDerniereModification='" + getDateDerniereModification() + "'" +
            ", libelleCommune='" + getLibelleCommune() + "'" +
            ", ligne1='" + getLigne1() + "'" +
            ", ligne2='" + getLigne2() + "'" +
            ", ligne3='" + getLigne3() + "'" +
            ", ligne4='" + getLigne4() + "'" +
            ", ligne5='" + getLigne5() + "'" +
            ", ligne6='" + getLigne6() + "'" +
            ", ligne7='" + getLigne7() + "'" +
            ", nombreCourriersPND='" + getNombreCourriersPND() + "'" +
            ", codeUsageAdresse='" + getCodeUsageAdresse() + "'" +
            ", pmEntreprise=" + getPmEntreprise() +
            ", pmEtablissement=" + getPmEtablissement() +
            "}";
    }
}
