package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A PmEntreprise.
 */
@Entity
@Table(name = "pm_entreprise")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PmEntreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_entreprise_rpg", nullable = false)
    private String idEntrepriseRPG;

    @NotNull
    @Column(name = "code_partenaire_distributeur", nullable = false)
    private String codePartenaireDistributeur;

    @NotNull
    @Column(name = "numero_siret_siege", nullable = false)
    private String numeroSiretSiege;

    @NotNull
    @Column(name = "code_etat", nullable = false)
    private String codeEtat;

    @NotNull
    @Column(name = "libelle_etat", nullable = false)
    private String libelleEtat;

    @NotNull
    @Column(name = "code_categorie_personne", nullable = false)
    private String codeCategoriePersonne;

    @NotNull
    @Column(name = "libelle_categorie_personne", nullable = false)
    private String libelleCategoriePersonne;

    @NotNull
    @Column(name = "code_type", nullable = false)
    private String codeType;

    @NotNull
    @Column(name = "date_creation_juridique", nullable = false)
    private LocalDate dateCreationJuridique;

    @NotNull
    @Column(name = "date_etat", nullable = false)
    private LocalDate dateEtat;

    @NotNull
    @Column(name = "date_fermeture_juridique", nullable = false)
    private LocalDate dateFermetureJuridique;

    @NotNull
    @Column(name = "code_tranche_effectif", nullable = false)
    private String codeTrancheEffectif;

    @NotNull
    @Column(name = "libelle_tranche_effectif", nullable = false)
    private String libelleTrancheEffectif;

    @NotNull
    @Column(name = "date_cessation_activite", nullable = false)
    private LocalDate dateCessationActivite;

    @NotNull
    @Column(name = "date_effectif_officiel", nullable = false)
    private LocalDate dateEffectifOfficiel;

    @NotNull
    @Column(name = "effectif_officiel", nullable = false)
    private String effectifOfficiel;

    @NotNull
    @Column(name = "code_motif_cessation_activite", nullable = false)
    private String codeMotifCessationActivite;

    @NotNull
    @Column(name = "siren", nullable = false)
    private String siren;

    @NotNull
    @Column(name = "code_forme_juridique", nullable = false)
    private String codeFormeJuridique;

    @NotNull
    @Column(name = "raison_sociale", nullable = false)
    private String raisonSociale;

    @NotNull
    @Column(name = "code_categorie_juridique", nullable = false)
    private String codeCategorieJuridique;

    @NotNull
    @Column(name = "code_idcc", nullable = false)
    private String codeIDCC;

    @NotNull
    @Column(name = "code_ape", nullable = false)
    private String codeAPE;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pmEntreprise")
    @JsonIgnoreProperties(value = { "pmEntreprise", "pmEtablissement" }, allowSetters = true)
    private Set<Adresse> adresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pmEntreprise")
    @JsonIgnoreProperties(value = { "pmEntreprise", "pmEtablissement" }, allowSetters = true)
    private Set<Email> emails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pmEntreprise")
    @JsonIgnoreProperties(value = { "pmEntreprise", "pmEtablissement" }, allowSetters = true)
    private Set<Telephone> telephones = new HashSet<>();

    @JsonIgnoreProperties(value = { "pmEntreprise", "miseEnGestion", "pmEtablissements" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pmEntreprise")
    private DemandeXRM demandeXRM;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PmEntreprise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdEntrepriseRPG() {
        return this.idEntrepriseRPG;
    }

    public PmEntreprise idEntrepriseRPG(String idEntrepriseRPG) {
        this.setIdEntrepriseRPG(idEntrepriseRPG);
        return this;
    }

    public void setIdEntrepriseRPG(String idEntrepriseRPG) {
        this.idEntrepriseRPG = idEntrepriseRPG;
    }

    public String getCodePartenaireDistributeur() {
        return this.codePartenaireDistributeur;
    }

    public PmEntreprise codePartenaireDistributeur(String codePartenaireDistributeur) {
        this.setCodePartenaireDistributeur(codePartenaireDistributeur);
        return this;
    }

    public void setCodePartenaireDistributeur(String codePartenaireDistributeur) {
        this.codePartenaireDistributeur = codePartenaireDistributeur;
    }

    public String getNumeroSiretSiege() {
        return this.numeroSiretSiege;
    }

    public PmEntreprise numeroSiretSiege(String numeroSiretSiege) {
        this.setNumeroSiretSiege(numeroSiretSiege);
        return this;
    }

    public void setNumeroSiretSiege(String numeroSiretSiege) {
        this.numeroSiretSiege = numeroSiretSiege;
    }

    public String getCodeEtat() {
        return this.codeEtat;
    }

    public PmEntreprise codeEtat(String codeEtat) {
        this.setCodeEtat(codeEtat);
        return this;
    }

    public void setCodeEtat(String codeEtat) {
        this.codeEtat = codeEtat;
    }

    public String getLibelleEtat() {
        return this.libelleEtat;
    }

    public PmEntreprise libelleEtat(String libelleEtat) {
        this.setLibelleEtat(libelleEtat);
        return this;
    }

    public void setLibelleEtat(String libelleEtat) {
        this.libelleEtat = libelleEtat;
    }

    public String getCodeCategoriePersonne() {
        return this.codeCategoriePersonne;
    }

    public PmEntreprise codeCategoriePersonne(String codeCategoriePersonne) {
        this.setCodeCategoriePersonne(codeCategoriePersonne);
        return this;
    }

    public void setCodeCategoriePersonne(String codeCategoriePersonne) {
        this.codeCategoriePersonne = codeCategoriePersonne;
    }

    public String getLibelleCategoriePersonne() {
        return this.libelleCategoriePersonne;
    }

    public PmEntreprise libelleCategoriePersonne(String libelleCategoriePersonne) {
        this.setLibelleCategoriePersonne(libelleCategoriePersonne);
        return this;
    }

    public void setLibelleCategoriePersonne(String libelleCategoriePersonne) {
        this.libelleCategoriePersonne = libelleCategoriePersonne;
    }

    public String getCodeType() {
        return this.codeType;
    }

    public PmEntreprise codeType(String codeType) {
        this.setCodeType(codeType);
        return this;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public LocalDate getDateCreationJuridique() {
        return this.dateCreationJuridique;
    }

    public PmEntreprise dateCreationJuridique(LocalDate dateCreationJuridique) {
        this.setDateCreationJuridique(dateCreationJuridique);
        return this;
    }

    public void setDateCreationJuridique(LocalDate dateCreationJuridique) {
        this.dateCreationJuridique = dateCreationJuridique;
    }

    public LocalDate getDateEtat() {
        return this.dateEtat;
    }

    public PmEntreprise dateEtat(LocalDate dateEtat) {
        this.setDateEtat(dateEtat);
        return this;
    }

    public void setDateEtat(LocalDate dateEtat) {
        this.dateEtat = dateEtat;
    }

    public LocalDate getDateFermetureJuridique() {
        return this.dateFermetureJuridique;
    }

    public PmEntreprise dateFermetureJuridique(LocalDate dateFermetureJuridique) {
        this.setDateFermetureJuridique(dateFermetureJuridique);
        return this;
    }

    public void setDateFermetureJuridique(LocalDate dateFermetureJuridique) {
        this.dateFermetureJuridique = dateFermetureJuridique;
    }

    public String getCodeTrancheEffectif() {
        return this.codeTrancheEffectif;
    }

    public PmEntreprise codeTrancheEffectif(String codeTrancheEffectif) {
        this.setCodeTrancheEffectif(codeTrancheEffectif);
        return this;
    }

    public void setCodeTrancheEffectif(String codeTrancheEffectif) {
        this.codeTrancheEffectif = codeTrancheEffectif;
    }

    public String getLibelleTrancheEffectif() {
        return this.libelleTrancheEffectif;
    }

    public PmEntreprise libelleTrancheEffectif(String libelleTrancheEffectif) {
        this.setLibelleTrancheEffectif(libelleTrancheEffectif);
        return this;
    }

    public void setLibelleTrancheEffectif(String libelleTrancheEffectif) {
        this.libelleTrancheEffectif = libelleTrancheEffectif;
    }

    public LocalDate getDateCessationActivite() {
        return this.dateCessationActivite;
    }

    public PmEntreprise dateCessationActivite(LocalDate dateCessationActivite) {
        this.setDateCessationActivite(dateCessationActivite);
        return this;
    }

    public void setDateCessationActivite(LocalDate dateCessationActivite) {
        this.dateCessationActivite = dateCessationActivite;
    }

    public LocalDate getDateEffectifOfficiel() {
        return this.dateEffectifOfficiel;
    }

    public PmEntreprise dateEffectifOfficiel(LocalDate dateEffectifOfficiel) {
        this.setDateEffectifOfficiel(dateEffectifOfficiel);
        return this;
    }

    public void setDateEffectifOfficiel(LocalDate dateEffectifOfficiel) {
        this.dateEffectifOfficiel = dateEffectifOfficiel;
    }

    public String getEffectifOfficiel() {
        return this.effectifOfficiel;
    }

    public PmEntreprise effectifOfficiel(String effectifOfficiel) {
        this.setEffectifOfficiel(effectifOfficiel);
        return this;
    }

    public void setEffectifOfficiel(String effectifOfficiel) {
        this.effectifOfficiel = effectifOfficiel;
    }

    public String getCodeMotifCessationActivite() {
        return this.codeMotifCessationActivite;
    }

    public PmEntreprise codeMotifCessationActivite(String codeMotifCessationActivite) {
        this.setCodeMotifCessationActivite(codeMotifCessationActivite);
        return this;
    }

    public void setCodeMotifCessationActivite(String codeMotifCessationActivite) {
        this.codeMotifCessationActivite = codeMotifCessationActivite;
    }

    public String getSiren() {
        return this.siren;
    }

    public PmEntreprise siren(String siren) {
        this.setSiren(siren);
        return this;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getCodeFormeJuridique() {
        return this.codeFormeJuridique;
    }

    public PmEntreprise codeFormeJuridique(String codeFormeJuridique) {
        this.setCodeFormeJuridique(codeFormeJuridique);
        return this;
    }

    public void setCodeFormeJuridique(String codeFormeJuridique) {
        this.codeFormeJuridique = codeFormeJuridique;
    }

    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public PmEntreprise raisonSociale(String raisonSociale) {
        this.setRaisonSociale(raisonSociale);
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getCodeCategorieJuridique() {
        return this.codeCategorieJuridique;
    }

    public PmEntreprise codeCategorieJuridique(String codeCategorieJuridique) {
        this.setCodeCategorieJuridique(codeCategorieJuridique);
        return this;
    }

    public void setCodeCategorieJuridique(String codeCategorieJuridique) {
        this.codeCategorieJuridique = codeCategorieJuridique;
    }

    public String getCodeIDCC() {
        return this.codeIDCC;
    }

    public PmEntreprise codeIDCC(String codeIDCC) {
        this.setCodeIDCC(codeIDCC);
        return this;
    }

    public void setCodeIDCC(String codeIDCC) {
        this.codeIDCC = codeIDCC;
    }

    public String getCodeAPE() {
        return this.codeAPE;
    }

    public PmEntreprise codeAPE(String codeAPE) {
        this.setCodeAPE(codeAPE);
        return this;
    }

    public void setCodeAPE(String codeAPE) {
        this.codeAPE = codeAPE;
    }

    public Set<Adresse> getAdresses() {
        return this.adresses;
    }

    public void setAdresses(Set<Adresse> adresses) {
        if (this.adresses != null) {
            this.adresses.forEach(i -> i.setPmEntreprise(null));
        }
        if (adresses != null) {
            adresses.forEach(i -> i.setPmEntreprise(this));
        }
        this.adresses = adresses;
    }

    public PmEntreprise adresses(Set<Adresse> adresses) {
        this.setAdresses(adresses);
        return this;
    }

    public PmEntreprise addAdresses(Adresse adresse) {
        this.adresses.add(adresse);
        adresse.setPmEntreprise(this);
        return this;
    }

    public PmEntreprise removeAdresses(Adresse adresse) {
        this.adresses.remove(adresse);
        adresse.setPmEntreprise(null);
        return this;
    }

    public Set<Email> getEmails() {
        return this.emails;
    }

    public void setEmails(Set<Email> emails) {
        if (this.emails != null) {
            this.emails.forEach(i -> i.setPmEntreprise(null));
        }
        if (emails != null) {
            emails.forEach(i -> i.setPmEntreprise(this));
        }
        this.emails = emails;
    }

    public PmEntreprise emails(Set<Email> emails) {
        this.setEmails(emails);
        return this;
    }

    public PmEntreprise addEmails(Email email) {
        this.emails.add(email);
        email.setPmEntreprise(this);
        return this;
    }

    public PmEntreprise removeEmails(Email email) {
        this.emails.remove(email);
        email.setPmEntreprise(null);
        return this;
    }

    public Set<Telephone> getTelephones() {
        return this.telephones;
    }

    public void setTelephones(Set<Telephone> telephones) {
        if (this.telephones != null) {
            this.telephones.forEach(i -> i.setPmEntreprise(null));
        }
        if (telephones != null) {
            telephones.forEach(i -> i.setPmEntreprise(this));
        }
        this.telephones = telephones;
    }

    public PmEntreprise telephones(Set<Telephone> telephones) {
        this.setTelephones(telephones);
        return this;
    }

    public PmEntreprise addTelephones(Telephone telephone) {
        this.telephones.add(telephone);
        telephone.setPmEntreprise(this);
        return this;
    }

    public PmEntreprise removeTelephones(Telephone telephone) {
        this.telephones.remove(telephone);
        telephone.setPmEntreprise(null);
        return this;
    }

    public DemandeXRM getDemandeXRM() {
        return this.demandeXRM;
    }

    public void setDemandeXRM(DemandeXRM demandeXRM) {
        if (this.demandeXRM != null) {
            this.demandeXRM.setPmEntreprise(null);
        }
        if (demandeXRM != null) {
            demandeXRM.setPmEntreprise(this);
        }
        this.demandeXRM = demandeXRM;
    }

    public PmEntreprise demandeXRM(DemandeXRM demandeXRM) {
        this.setDemandeXRM(demandeXRM);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PmEntreprise)) {
            return false;
        }
        return getId() != null && getId().equals(((PmEntreprise) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PmEntreprise{" +
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
