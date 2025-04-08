package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DemandeXRM.
 */
@Entity
@Table(name = "demande_xrm")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeXRM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "adresses", "emails", "telephones", "demandeXRM" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private PmEntreprise pmEntreprise;

    @JsonIgnoreProperties(value = { "demandeXRM" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private MiseEnGestion miseEnGestion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "demandeXRM")
    @JsonIgnoreProperties(value = { "adresses", "emails", "telephones", "demandeXRM" }, allowSetters = true)
    private Set<PmEtablissement> pmEtablissements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeXRM id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PmEntreprise getPmEntreprise() {
        return this.pmEntreprise;
    }

    public void setPmEntreprise(PmEntreprise pmEntreprise) {
        this.pmEntreprise = pmEntreprise;
    }

    public DemandeXRM pmEntreprise(PmEntreprise pmEntreprise) {
        this.setPmEntreprise(pmEntreprise);
        return this;
    }

    public MiseEnGestion getMiseEnGestion() {
        return this.miseEnGestion;
    }

    public void setMiseEnGestion(MiseEnGestion miseEnGestion) {
        this.miseEnGestion = miseEnGestion;
    }

    public DemandeXRM miseEnGestion(MiseEnGestion miseEnGestion) {
        this.setMiseEnGestion(miseEnGestion);
        return this;
    }

    public Set<PmEtablissement> getPmEtablissements() {
        return this.pmEtablissements;
    }

    public void setPmEtablissements(Set<PmEtablissement> pmEtablissements) {
        if (this.pmEtablissements != null) {
            this.pmEtablissements.forEach(i -> i.setDemandeXRM(null));
        }
        if (pmEtablissements != null) {
            pmEtablissements.forEach(i -> i.setDemandeXRM(this));
        }
        this.pmEtablissements = pmEtablissements;
    }

    public DemandeXRM pmEtablissements(Set<PmEtablissement> pmEtablissements) {
        this.setPmEtablissements(pmEtablissements);
        return this;
    }

    public DemandeXRM addPmEtablissements(PmEtablissement pmEtablissement) {
        this.pmEtablissements.add(pmEtablissement);
        pmEtablissement.setDemandeXRM(this);
        return this;
    }

    public DemandeXRM removePmEtablissements(PmEtablissement pmEtablissement) {
        this.pmEtablissements.remove(pmEtablissement);
        pmEtablissement.setDemandeXRM(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeXRM)) {
            return false;
        }
        return getId() != null && getId().equals(((DemandeXRM) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeXRM{" +
            "id=" + getId() +
            "}";
    }
}
