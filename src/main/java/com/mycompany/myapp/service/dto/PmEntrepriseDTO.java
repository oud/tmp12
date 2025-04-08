package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PmEntreprise} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PmEntrepriseDTO implements Serializable {

    private Long id;

    @NotNull
    private String idEntrepriseRPG;

    @NotNull
    private String codePartenaireDistributeur;

    @NotNull
    private String numeroSiretSiege;

    @NotNull
    private String codeEtat;

    @NotNull
    private String libelleEtat;

    @NotNull
    private String codeCategoriePersonne;

    @NotNull
    private String libelleCategoriePersonne;

    @NotNull
    private String codeType;

    @NotNull
    private LocalDate dateCreationJuridique;

    @NotNull
    private LocalDate dateEtat;

    @NotNull
    private LocalDate dateFermetureJuridique;

    @NotNull
    private String codeTrancheEffectif;

    @NotNull
    private String libelleTrancheEffectif;

    @NotNull
    private LocalDate dateCessationActivite;

    @NotNull
    private LocalDate dateEffectifOfficiel;

    @NotNull
    private String effectifOfficiel;

    @NotNull
    private String codeMotifCessationActivite;

    @NotNull
    private String siren;

    @NotNull
    private String codeFormeJuridique;

    @NotNull
    private String raisonSociale;

    @NotNull
    private String codeCategorieJuridique;

    @NotNull
    private String codeIDCC;

    @NotNull
    private String codeAPE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdEntrepriseRPG() {
        return idEntrepriseRPG;
    }

    public void setIdEntrepriseRPG(String idEntrepriseRPG) {
        this.idEntrepriseRPG = idEntrepriseRPG;
    }

    public String getCodePartenaireDistributeur() {
        return codePartenaireDistributeur;
    }

    public void setCodePartenaireDistributeur(String codePartenaireDistributeur) {
        this.codePartenaireDistributeur = codePartenaireDistributeur;
    }

    public String getNumeroSiretSiege() {
        return numeroSiretSiege;
    }

    public void setNumeroSiretSiege(String numeroSiretSiege) {
        this.numeroSiretSiege = numeroSiretSiege;
    }

    public String getCodeEtat() {
        return codeEtat;
    }

    public void setCodeEtat(String codeEtat) {
        this.codeEtat = codeEtat;
    }

    public String getLibelleEtat() {
        return libelleEtat;
    }

    public void setLibelleEtat(String libelleEtat) {
        this.libelleEtat = libelleEtat;
    }

    public String getCodeCategoriePersonne() {
        return codeCategoriePersonne;
    }

    public void setCodeCategoriePersonne(String codeCategoriePersonne) {
        this.codeCategoriePersonne = codeCategoriePersonne;
    }

    public String getLibelleCategoriePersonne() {
        return libelleCategoriePersonne;
    }

    public void setLibelleCategoriePersonne(String libelleCategoriePersonne) {
        this.libelleCategoriePersonne = libelleCategoriePersonne;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public LocalDate getDateCreationJuridique() {
        return dateCreationJuridique;
    }

    public void setDateCreationJuridique(LocalDate dateCreationJuridique) {
        this.dateCreationJuridique = dateCreationJuridique;
    }

    public LocalDate getDateEtat() {
        return dateEtat;
    }

    public void setDateEtat(LocalDate dateEtat) {
        this.dateEtat = dateEtat;
    }

    public LocalDate getDateFermetureJuridique() {
        return dateFermetureJuridique;
    }

    public void setDateFermetureJuridique(LocalDate dateFermetureJuridique) {
        this.dateFermetureJuridique = dateFermetureJuridique;
    }

    public String getCodeTrancheEffectif() {
        return codeTrancheEffectif;
    }

    public void setCodeTrancheEffectif(String codeTrancheEffectif) {
        this.codeTrancheEffectif = codeTrancheEffectif;
    }

    public String getLibelleTrancheEffectif() {
        return libelleTrancheEffectif;
    }

    public void setLibelleTrancheEffectif(String libelleTrancheEffectif) {
        this.libelleTrancheEffectif = libelleTrancheEffectif;
    }

    public LocalDate getDateCessationActivite() {
        return dateCessationActivite;
    }

    public void setDateCessationActivite(LocalDate dateCessationActivite) {
        this.dateCessationActivite = dateCessationActivite;
    }

    public LocalDate getDateEffectifOfficiel() {
        return dateEffectifOfficiel;
    }

    public void setDateEffectifOfficiel(LocalDate dateEffectifOfficiel) {
        this.dateEffectifOfficiel = dateEffectifOfficiel;
    }

    public String getEffectifOfficiel() {
        return effectifOfficiel;
    }

    public void setEffectifOfficiel(String effectifOfficiel) {
        this.effectifOfficiel = effectifOfficiel;
    }

    public String getCodeMotifCessationActivite() {
        return codeMotifCessationActivite;
    }

    public void setCodeMotifCessationActivite(String codeMotifCessationActivite) {
        this.codeMotifCessationActivite = codeMotifCessationActivite;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getCodeFormeJuridique() {
        return codeFormeJuridique;
    }

    public void setCodeFormeJuridique(String codeFormeJuridique) {
        this.codeFormeJuridique = codeFormeJuridique;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getCodeCategorieJuridique() {
        return codeCategorieJuridique;
    }

    public void setCodeCategorieJuridique(String codeCategorieJuridique) {
        this.codeCategorieJuridique = codeCategorieJuridique;
    }

    public String getCodeIDCC() {
        return codeIDCC;
    }

    public void setCodeIDCC(String codeIDCC) {
        this.codeIDCC = codeIDCC;
    }

    public String getCodeAPE() {
        return codeAPE;
    }

    public void setCodeAPE(String codeAPE) {
        this.codeAPE = codeAPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PmEntrepriseDTO)) {
            return false;
        }

        PmEntrepriseDTO pmEntrepriseDTO = (PmEntrepriseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pmEntrepriseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PmEntrepriseDTO{" +
            "id=" + getId() +
            ", idEntrepriseRPG='" + getIdEntrepriseRPG() + "'" +
            ", codePartenaireDistributeur='" + getCodePartenaireDistributeur() + "'" +
            ", numeroSiretSiege='" + getNumeroSiretSiege() + "'" +
            ", codeEtat='" + getCodeEtat() + "'" +
            ", libelleEtat='" + getLibelleEtat() + "'" +
            ", codeCategoriePersonne='" + getCodeCategoriePersonne() + "'" +
            ", libelleCategoriePersonne='" + getLibelleCategoriePersonne() + "'" +
            ", codeType='" + getCodeType() + "'" +
            ", dateCreationJuridique='" + getDateCreationJuridique() + "'" +
            ", dateEtat='" + getDateEtat() + "'" +
            ", dateFermetureJuridique='" + getDateFermetureJuridique() + "'" +
            ", codeTrancheEffectif='" + getCodeTrancheEffectif() + "'" +
            ", libelleTrancheEffectif='" + getLibelleTrancheEffectif() + "'" +
            ", dateCessationActivite='" + getDateCessationActivite() + "'" +
            ", dateEffectifOfficiel='" + getDateEffectifOfficiel() + "'" +
            ", effectifOfficiel='" + getEffectifOfficiel() + "'" +
            ", codeMotifCessationActivite='" + getCodeMotifCessationActivite() + "'" +
            ", siren='" + getSiren() + "'" +
            ", codeFormeJuridique='" + getCodeFormeJuridique() + "'" +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", codeCategorieJuridique='" + getCodeCategorieJuridique() + "'" +
            ", codeIDCC='" + getCodeIDCC() + "'" +
            ", codeAPE='" + getCodeAPE() + "'" +
            "}";
    }
}
