package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Email.
 */
@Entity
@Table(name = "email")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "adresse_email", nullable = false)
    private String adresseEmail;

    @NotNull
    @Column(name = "code_statut", nullable = false)
    private String codeStatut;

    @NotNull
    @Column(name = "date_statut", nullable = false)
    private LocalDate dateStatut;

    @NotNull
    @Column(name = "code_usage_email", nullable = false)
    private String codeUsageEmail;

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

    public Email id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresseEmail() {
        return this.adresseEmail;
    }

    public Email adresseEmail(String adresseEmail) {
        this.setAdresseEmail(adresseEmail);
        return this;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public String getCodeStatut() {
        return this.codeStatut;
    }

    public Email codeStatut(String codeStatut) {
        this.setCodeStatut(codeStatut);
        return this;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public LocalDate getDateStatut() {
        return this.dateStatut;
    }

    public Email dateStatut(LocalDate dateStatut) {
        this.setDateStatut(dateStatut);
        return this;
    }

    public void setDateStatut(LocalDate dateStatut) {
        this.dateStatut = dateStatut;
    }

    public String getCodeUsageEmail() {
        return this.codeUsageEmail;
    }

    public Email codeUsageEmail(String codeUsageEmail) {
        this.setCodeUsageEmail(codeUsageEmail);
        return this;
    }

    public void setCodeUsageEmail(String codeUsageEmail) {
        this.codeUsageEmail = codeUsageEmail;
    }

    public PmEntreprise getPmEntreprise() {
        return this.pmEntreprise;
    }

    public void setPmEntreprise(PmEntreprise pmEntreprise) {
        this.pmEntreprise = pmEntreprise;
    }

    public Email pmEntreprise(PmEntreprise pmEntreprise) {
        this.setPmEntreprise(pmEntreprise);
        return this;
    }

    public PmEtablissement getPmEtablissement() {
        return this.pmEtablissement;
    }

    public void setPmEtablissement(PmEtablissement pmEtablissement) {
        this.pmEtablissement = pmEtablissement;
    }

    public Email pmEtablissement(PmEtablissement pmEtablissement) {
        this.setPmEtablissement(pmEtablissement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        return getId() != null && getId().equals(((Email) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Email{" +
            "id=" + getId() +
            ", adresseEmail='" + getAdresseEmail() + "'" +
            ", codeStatut='" + getCodeStatut() + "'" +
            ", dateStatut='" + getDateStatut() + "'" +
            ", codeUsageEmail='" + getCodeUsageEmail() + "'" +
            "}";
    }
}
