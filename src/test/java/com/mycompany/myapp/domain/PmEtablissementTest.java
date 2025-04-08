package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AdresseTestSamples.*;
import static com.mycompany.myapp.domain.DemandeXRMTestSamples.*;
import static com.mycompany.myapp.domain.EmailTestSamples.*;
import static com.mycompany.myapp.domain.PmEtablissementTestSamples.*;
import static com.mycompany.myapp.domain.TelephoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PmEtablissementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PmEtablissement.class);
        PmEtablissement pmEtablissement1 = getPmEtablissementSample1();
        PmEtablissement pmEtablissement2 = new PmEtablissement();
        assertThat(pmEtablissement1).isNotEqualTo(pmEtablissement2);

        pmEtablissement2.setId(pmEtablissement1.getId());
        assertThat(pmEtablissement1).isEqualTo(pmEtablissement2);

        pmEtablissement2 = getPmEtablissementSample2();
        assertThat(pmEtablissement1).isNotEqualTo(pmEtablissement2);
    }

    @Test
    void adressesTest() {
        PmEtablissement pmEtablissement = getPmEtablissementRandomSampleGenerator();
        Adresse adresseBack = getAdresseRandomSampleGenerator();

        pmEtablissement.addAdresses(adresseBack);
        assertThat(pmEtablissement.getAdresses()).containsOnly(adresseBack);
        assertThat(adresseBack.getPmEtablissement()).isEqualTo(pmEtablissement);

        pmEtablissement.removeAdresses(adresseBack);
        assertThat(pmEtablissement.getAdresses()).doesNotContain(adresseBack);
        assertThat(adresseBack.getPmEtablissement()).isNull();

        pmEtablissement.adresses(new HashSet<>(Set.of(adresseBack)));
        assertThat(pmEtablissement.getAdresses()).containsOnly(adresseBack);
        assertThat(adresseBack.getPmEtablissement()).isEqualTo(pmEtablissement);

        pmEtablissement.setAdresses(new HashSet<>());
        assertThat(pmEtablissement.getAdresses()).doesNotContain(adresseBack);
        assertThat(adresseBack.getPmEtablissement()).isNull();
    }

    @Test
    void emailsTest() {
        PmEtablissement pmEtablissement = getPmEtablissementRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        pmEtablissement.addEmails(emailBack);
        assertThat(pmEtablissement.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getPmEtablissement()).isEqualTo(pmEtablissement);

        pmEtablissement.removeEmails(emailBack);
        assertThat(pmEtablissement.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getPmEtablissement()).isNull();

        pmEtablissement.emails(new HashSet<>(Set.of(emailBack)));
        assertThat(pmEtablissement.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getPmEtablissement()).isEqualTo(pmEtablissement);

        pmEtablissement.setEmails(new HashSet<>());
        assertThat(pmEtablissement.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getPmEtablissement()).isNull();
    }

    @Test
    void telephonesTest() {
        PmEtablissement pmEtablissement = getPmEtablissementRandomSampleGenerator();
        Telephone telephoneBack = getTelephoneRandomSampleGenerator();

        pmEtablissement.addTelephones(telephoneBack);
        assertThat(pmEtablissement.getTelephones()).containsOnly(telephoneBack);
        assertThat(telephoneBack.getPmEtablissement()).isEqualTo(pmEtablissement);

        pmEtablissement.removeTelephones(telephoneBack);
        assertThat(pmEtablissement.getTelephones()).doesNotContain(telephoneBack);
        assertThat(telephoneBack.getPmEtablissement()).isNull();

        pmEtablissement.telephones(new HashSet<>(Set.of(telephoneBack)));
        assertThat(pmEtablissement.getTelephones()).containsOnly(telephoneBack);
        assertThat(telephoneBack.getPmEtablissement()).isEqualTo(pmEtablissement);

        pmEtablissement.setTelephones(new HashSet<>());
        assertThat(pmEtablissement.getTelephones()).doesNotContain(telephoneBack);
        assertThat(telephoneBack.getPmEtablissement()).isNull();
    }

    @Test
    void demandeXRMTest() {
        PmEtablissement pmEtablissement = getPmEtablissementRandomSampleGenerator();
        DemandeXRM demandeXRMBack = getDemandeXRMRandomSampleGenerator();

        pmEtablissement.setDemandeXRM(demandeXRMBack);
        assertThat(pmEtablissement.getDemandeXRM()).isEqualTo(demandeXRMBack);

        pmEtablissement.demandeXRM(null);
        assertThat(pmEtablissement.getDemandeXRM()).isNull();
    }
}
