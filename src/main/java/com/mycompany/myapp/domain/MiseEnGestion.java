package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A MiseEnGestion.
 */
@Entity
@Table(name = "mise_en_gestion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MiseEnGestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "type_meg", nullable = false)
    private String typeMEG;

    @NotNull
    @Column(name = "code_offre", nullable = false)
    private String codeOffre;

    @NotNull
    @Column(name = "date_effet", nullable = false)
    private LocalDate dateEffet;

    @JsonIgnoreProperties(value = { "pmEntreprise", "miseEnGestion", "pmEtablissements" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "miseEnGestion")
    private DemandeXRM demandeXRM;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MiseEnGestion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeMEG() {
        return this.typeMEG;
    }

    public MiseEnGestion typeMEG(String typeMEG) {
        this.setTypeMEG(typeMEG);
        return this;
    }

    public void setTypeMEG(String typeMEG) {
        this.typeMEG = typeMEG;
    }

    public String getCodeOffre() {
        return this.codeOffre;
    }

    public MiseEnGestion codeOffre(String codeOffre) {
        this.setCodeOffre(codeOffre);
        return this;
    }

    public void setCodeOffre(String codeOffre) {
        this.codeOffre = codeOffre;
    }

    public LocalDate getDateEffet() {
        return this.dateEffet;
    }

    public MiseEnGestion dateEffet(LocalDate dateEffet) {
        this.setDateEffet(dateEffet);
        return this;
    }

    public void setDateEffet(LocalDate dateEffet) {
        this.dateEffet = dateEffet;
    }

    public DemandeXRM getDemandeXRM() {
        return this.demandeXRM;
    }

    public void setDemandeXRM(DemandeXRM demandeXRM) {
        if (this.demandeXRM != null) {
            this.demandeXRM.setMiseEnGestion(null);
        }
        if (demandeXRM != null) {
            demandeXRM.setMiseEnGestion(this);
        }
        this.demandeXRM = demandeXRM;
    }

    public MiseEnGestion demandeXRM(DemandeXRM demandeXRM) {
        this.setDemandeXRM(demandeXRM);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MiseEnGestion)) {
            return false;
        }
        return getId() != null && getId().equals(((MiseEnGestion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MiseEnGestion{" +
            "id=" + getId() +
            ", typeMEG='" + getTypeMEG() + "'" +
            ", codeOffre='" + getCodeOffre() + "'" +
            ", dateEffet='" + getDateEffet() + "'" +
            "}";
    }
}
