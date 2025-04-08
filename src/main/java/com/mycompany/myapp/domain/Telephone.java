package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "telephone")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Telephone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code_pays_iso", nullable = false)
    private String codePaysISO;

    @NotNull
    @Column(name = "code_type_telephone", nullable = false)
    private String codeTypeTelephone;

    @NotNull
    @Column(name = "date_derniere_modification", nullable = false)
    private LocalDate dateDerniereModification;

    @NotNull
    @Column(name = "code_indicatif_pays", nullable = false)
    private String codeIndicatifPays;

    @NotNull
    @Column(name = "numero_telephone", nullable = false)
    private String numeroTelephone;

    @NotNull
    @Column(name = "code_statut", nullable = false)
    private String codeStatut;

    @NotNull
    @Column(name = "date_statut", nullable = false)
    private String dateStatut;

    @NotNull
    @Column(name = "code_usage_telephone", nullable = false)
    private String codeUsageTelephone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "adresses", "emails", "telephones", "demandeXRM" }, allowSetters = true)
    private PmEntreprise pmEntreprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "adresses", "emails", "telephones", "demandeXRM" }, allowSetters = true)
    private PmEtablissement pmEtablissement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Telephone id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePaysISO() {
        return this.codePaysISO;
    }

    public Telephone codePaysISO(String codePaysISO) {
        this.setCodePaysISO(codePaysISO);
        return this;
    }

    public void setCodePaysISO(String codePaysISO) {
        this.codePaysISO = codePaysISO;
    }

    public String getCodeTypeTelephone() {
        return this.codeTypeTelephone;
    }

    public Telephone codeTypeTelephone(String codeTypeTelephone) {
        this.setCodeTypeTelephone(codeTypeTelephone);
        return this;
    }

    public void setCodeTypeTelephone(String codeTypeTelephone) {
        this.codeTypeTelephone = codeTypeTelephone;
    }

    public LocalDate getDateDerniereModification() {
        return this.dateDerniereModification;
    }

    public Telephone dateDerniereModification(LocalDate dateDerniereModification) {
        this.setDateDerniereModification(dateDerniereModification);
        return this;
    }

    public void setDateDerniereModification(LocalDate dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
    }

    public String getCodeIndicatifPays() {
        return this.codeIndicatifPays;
    }

    public Telephone codeIndicatifPays(String codeIndicatifPays) {
        this.setCodeIndicatifPays(codeIndicatifPays);
        return this;
    }

    public void setCodeIndicatifPays(String codeIndicatifPays) {
        this.codeIndicatifPays = codeIndicatifPays;
    }

    public String getNumeroTelephone() {
        return this.numeroTelephone;
    }

    public Telephone numeroTelephone(String numeroTelephone) {
        this.setNumeroTelephone(numeroTelephone);
        return this;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getCodeStatut() {
        return this.codeStatut;
    }

    public Telephone codeStatut(String codeStatut) {
        this.setCodeStatut(codeStatut);
        return this;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public String getDateStatut() {
        return this.dateStatut;
    }

    public Telephone dateStatut(String dateStatut) {
        this.setDateStatut(dateStatut);
        return this;
    }

    public void setDateStatut(String dateStatut) {
        this.dateStatut = dateStatut;
    }

    public String getCodeUsageTelephone() {
        return this.codeUsageTelephone;
    }

    public Telephone codeUsageTelephone(String codeUsageTelephone) {
        this.setCodeUsageTelephone(codeUsageTelephone);
        return this;
    }

    public void setCodeUsageTelephone(String codeUsageTelephone) {
        this.codeUsageTelephone = codeUsageTelephone;
    }

    public PmEntreprise getPmEntreprise() {
        return this.pmEntreprise;
    }

    public void setPmEntreprise(PmEntreprise pmEntreprise) {
        this.pmEntreprise = pmEntreprise;
    }

    public Telephone pmEntreprise(PmEntreprise pmEntreprise) {
        this.setPmEntreprise(pmEntreprise);
        return this;
    }

    public PmEtablissement getPmEtablissement() {
        return this.pmEtablissement;
    }

    public void setPmEtablissement(PmEtablissement pmEtablissement) {
        this.pmEtablissement = pmEtablissement;
    }

    public Telephone pmEtablissement(PmEtablissement pmEtablissement) {
        this.setPmEtablissement(pmEtablissement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Telephone)) {
            return false;
        }
        return getId() != null && getId().equals(((Telephone) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Telephone{" +
            "id=" + getId() +
            ", codePaysISO='" + getCodePaysISO() + "'" +
            ", codeTypeTelephone='" + getCodeTypeTelephone() + "'" +
            ", dateDerniereModification='" + getDateDerniereModification() + "'" +
            ", codeIndicatifPays='" + getCodeIndicatifPays() + "'" +
            ", numeroTelephone='" + getNumeroTelephone() + "'" +
            ", codeStatut='" + getCodeStatut() + "'" +
            ", dateStatut='" + getDateStatut() + "'" +
            ", codeUsageTelephone='" + getCodeUsageTelephone() + "'" +
            "}";
    }
}
