package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Adresse.
 */
@Entity
@Table(name = "adresse")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Adresse implements Serializable {

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
    @Column(name = "code_postal", nullable = false)
    private String codePostal;

    @NotNull
    @Column(name = "date_derniere_modification", nullable = false)
    private LocalDate dateDerniereModification;

    @NotNull
    @Column(name = "libelle_commune", nullable = false)
    private String libelleCommune;

    @NotNull
    @Column(name = "ligne_1", nullable = false)
    private String ligne1;

    @NotNull
    @Column(name = "ligne_2", nullable = false)
    private String ligne2;

    @NotNull
    @Column(name = "ligne_3", nullable = false)
    private String ligne3;

    @NotNull
    @Column(name = "ligne_4", nullable = false)
    private String ligne4;

    @NotNull
    @Column(name = "ligne_5", nullable = false)
    private String ligne5;

    @NotNull
    @Column(name = "ligne_6", nullable = false)
    private String ligne6;

    @NotNull
    @Column(name = "ligne_7", nullable = false)
    private String ligne7;

    @NotNull
    @Column(name = "nombre_courriers_pnd", nullable = false)
    private String nombreCourriersPND;

    @NotNull
    @Column(name = "code_usage_adresse", nullable = false)
    private String codeUsageAdresse;

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

    public Adresse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePaysISO() {
        return this.codePaysISO;
    }

    public Adresse codePaysISO(String codePaysISO) {
        this.setCodePaysISO(codePaysISO);
        return this;
    }

    public void setCodePaysISO(String codePaysISO) {
        this.codePaysISO = codePaysISO;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public Adresse codePostal(String codePostal) {
        this.setCodePostal(codePostal);
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public LocalDate getDateDerniereModification() {
        return this.dateDerniereModification;
    }

    public Adresse dateDerniereModification(LocalDate dateDerniereModification) {
        this.setDateDerniereModification(dateDerniereModification);
        return this;
    }

    public void setDateDerniereModification(LocalDate dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
    }

    public String getLibelleCommune() {
        return this.libelleCommune;
    }

    public Adresse libelleCommune(String libelleCommune) {
        this.setLibelleCommune(libelleCommune);
        return this;
    }

    public void setLibelleCommune(String libelleCommune) {
        this.libelleCommune = libelleCommune;
    }

    public String getLigne1() {
        return this.ligne1;
    }

    public Adresse ligne1(String ligne1) {
        this.setLigne1(ligne1);
        return this;
    }

    public void setLigne1(String ligne1) {
        this.ligne1 = ligne1;
    }

    public String getLigne2() {
        return this.ligne2;
    }

    public Adresse ligne2(String ligne2) {
        this.setLigne2(ligne2);
        return this;
    }

    public void setLigne2(String ligne2) {
        this.ligne2 = ligne2;
    }

    public String getLigne3() {
        return this.ligne3;
    }

    public Adresse ligne3(String ligne3) {
        this.setLigne3(ligne3);
        return this;
    }

    public void setLigne3(String ligne3) {
        this.ligne3 = ligne3;
    }

    public String getLigne4() {
        return this.ligne4;
    }

    public Adresse ligne4(String ligne4) {
        this.setLigne4(ligne4);
        return this;
    }

    public void setLigne4(String ligne4) {
        this.ligne4 = ligne4;
    }

    public String getLigne5() {
        return this.ligne5;
    }

    public Adresse ligne5(String ligne5) {
        this.setLigne5(ligne5);
        return this;
    }

    public void setLigne5(String ligne5) {
        this.ligne5 = ligne5;
    }

    public String getLigne6() {
        return this.ligne6;
    }

    public Adresse ligne6(String ligne6) {
        this.setLigne6(ligne6);
        return this;
    }

    public void setLigne6(String ligne6) {
        this.ligne6 = ligne6;
    }

    public String getLigne7() {
        return this.ligne7;
    }

    public Adresse ligne7(String ligne7) {
        this.setLigne7(ligne7);
        return this;
    }

    public void setLigne7(String ligne7) {
        this.ligne7 = ligne7;
    }

    public String getNombreCourriersPND() {
        return this.nombreCourriersPND;
    }

    public Adresse nombreCourriersPND(String nombreCourriersPND) {
        this.setNombreCourriersPND(nombreCourriersPND);
        return this;
    }

    public void setNombreCourriersPND(String nombreCourriersPND) {
        this.nombreCourriersPND = nombreCourriersPND;
    }

    public String getCodeUsageAdresse() {
        return this.codeUsageAdresse;
    }

    public Adresse codeUsageAdresse(String codeUsageAdresse) {
        this.setCodeUsageAdresse(codeUsageAdresse);
        return this;
    }

    public void setCodeUsageAdresse(String codeUsageAdresse) {
        this.codeUsageAdresse = codeUsageAdresse;
    }

    public PmEntreprise getPmEntreprise() {
        return this.pmEntreprise;
    }

    public void setPmEntreprise(PmEntreprise pmEntreprise) {
        this.pmEntreprise = pmEntreprise;
    }

    public Adresse pmEntreprise(PmEntreprise pmEntreprise) {
        this.setPmEntreprise(pmEntreprise);
        return this;
    }

    public PmEtablissement getPmEtablissement() {
        return this.pmEtablissement;
    }

    public void setPmEtablissement(PmEtablissement pmEtablissement) {
        this.pmEtablissement = pmEtablissement;
    }

    public Adresse pmEtablissement(PmEtablissement pmEtablissement) {
        this.setPmEtablissement(pmEtablissement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresse)) {
            return false;
        }
        return getId() != null && getId().equals(((Adresse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adresse{" +
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
            "}";
    }
}
