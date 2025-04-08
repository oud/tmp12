package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PmEtablissement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PmEtablissementDTO implements Serializable {

    private Long id;

    @NotNull
    private String idEtablissementRPG;

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
    private String dateCreationJuridique;

    @NotNull
    private String dateEtat;

    @NotNull
    private String dateFermetureJuridique;

    @NotNull
    private String codeIDCC;

    @NotNull
    private String codeTrancheEffectif;

    @NotNull
    private String libelleTrancheEffectif;

    @NotNull
    private String dateCessationActivite;

    @NotNull
    private String dateEffectifOfficiel;

    @NotNull
    private String effectifOfficiel;

    @NotNull
    private String codeMotifCessationActivite;

    @NotNull
    private String siret;

    @NotNull
    private String codeTypeEtablissement;

    @NotNull
    private String libelleEnseigne;

    @NotNull
    private String codeNIC;

    private DemandeXRMDTO demandeXRM;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdEtablissementRPG() {
        return idEtablissementRPG;
    }

    public void setIdEtablissementRPG(String idEtablissementRPG) {
        this.idEtablissementRPG = idEtablissementRPG;
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

    public String getDateCreationJuridique() {
        return dateCreationJuridique;
    }

    public void setDateCreationJuridique(String dateCreationJuridique) {
        this.dateCreationJuridique = dateCreationJuridique;
    }

    public String getDateEtat() {
        return dateEtat;
    }

    public void setDateEtat(String dateEtat) {
        this.dateEtat = dateEtat;
    }

    public String getDateFermetureJuridique() {
        return dateFermetureJuridique;
    }

    public void setDateFermetureJuridique(String dateFermetureJuridique) {
        this.dateFermetureJuridique = dateFermetureJuridique;
    }

    public String getCodeIDCC() {
        return codeIDCC;
    }

    public void setCodeIDCC(String codeIDCC) {
        this.codeIDCC = codeIDCC;
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

    public String getDateCessationActivite() {
        return dateCessationActivite;
    }

    public void setDateCessationActivite(String dateCessationActivite) {
        this.dateCessationActivite = dateCessationActivite;
    }

    public String getDateEffectifOfficiel() {
        return dateEffectifOfficiel;
    }

    public void setDateEffectifOfficiel(String dateEffectifOfficiel) {
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

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getCodeTypeEtablissement() {
        return codeTypeEtablissement;
    }

    public void setCodeTypeEtablissement(String codeTypeEtablissement) {
        this.codeTypeEtablissement = codeTypeEtablissement;
    }

    public String getLibelleEnseigne() {
        return libelleEnseigne;
    }

    public void setLibelleEnseigne(String libelleEnseigne) {
        this.libelleEnseigne = libelleEnseigne;
    }

    public String getCodeNIC() {
        return codeNIC;
    }

    public void setCodeNIC(String codeNIC) {
        this.codeNIC = codeNIC;
    }

    public DemandeXRMDTO getDemandeXRM() {
        return demandeXRM;
    }

    public void setDemandeXRM(DemandeXRMDTO demandeXRM) {
        this.demandeXRM = demandeXRM;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PmEtablissementDTO)) {
            return false;
        }

        PmEtablissementDTO pmEtablissementDTO = (PmEtablissementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pmEtablissementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PmEtablissementDTO{" +
            "id=" + getId() +
            ", idEtablissementRPG='" + getIdEtablissementRPG() + "'" +
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
            ", codeIDCC='" + getCodeIDCC() + "'" +
            ", codeTrancheEffectif='" + getCodeTrancheEffectif() + "'" +
            ", libelleTrancheEffectif='" + getLibelleTrancheEffectif() + "'" +
            ", dateCessationActivite='" + getDateCessationActivite() + "'" +
            ", dateEffectifOfficiel='" + getDateEffectifOfficiel() + "'" +
            ", effectifOfficiel='" + getEffectifOfficiel() + "'" +
            ", codeMotifCessationActivite='" + getCodeMotifCessationActivite() + "'" +
            ", siret='" + getSiret() + "'" +
            ", codeTypeEtablissement='" + getCodeTypeEtablissement() + "'" +
            ", libelleEnseigne='" + getLibelleEnseigne() + "'" +
            ", codeNIC='" + getCodeNIC() + "'" +
            ", demandeXRM=" + getDemandeXRM() +
            "}";
    }
}
