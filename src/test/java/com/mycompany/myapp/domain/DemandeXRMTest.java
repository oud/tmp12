package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DemandeXRMTestSamples.*;
import static com.mycompany.myapp.domain.MiseEnGestionTestSamples.*;
import static com.mycompany.myapp.domain.PmEntrepriseTestSamples.*;
import static com.mycompany.myapp.domain.PmEtablissementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DemandeXRMTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeXRM.class);
        DemandeXRM demandeXRM1 = getDemandeXRMSample1();
        DemandeXRM demandeXRM2 = new DemandeXRM();
        assertThat(demandeXRM1).isNotEqualTo(demandeXRM2);

        demandeXRM2.setId(demandeXRM1.getId());
        assertThat(demandeXRM1).isEqualTo(demandeXRM2);

        demandeXRM2 = getDemandeXRMSample2();
        assertThat(demandeXRM1).isNotEqualTo(demandeXRM2);
    }

    @Test
    void pmEntrepriseTest() {
        DemandeXRM demandeXRM = getDemandeXRMRandomSampleGenerator();
        PmEntreprise pmEntrepriseBack = getPmEntrepriseRandomSampleGenerator();

        demandeXRM.setPmEntreprise(pmEntrepriseBack);
        assertThat(demandeXRM.getPmEntreprise()).isEqualTo(pmEntrepriseBack);

        demandeXRM.pmEntreprise(null);
        assertThat(demandeXRM.getPmEntreprise()).isNull();
    }

    @Test
    void miseEnGestionTest() {
        DemandeXRM demandeXRM = getDemandeXRMRandomSampleGenerator();
        MiseEnGestion miseEnGestionBack = getMiseEnGestionRandomSampleGenerator();

        demandeXRM.setMiseEnGestion(miseEnGestionBack);
        assertThat(demandeXRM.getMiseEnGestion()).isEqualTo(miseEnGestionBack);

        demandeXRM.miseEnGestion(null);
        assertThat(demandeXRM.getMiseEnGestion()).isNull();
    }

    @Test
    void pmEtablissementsTest() {
        DemandeXRM demandeXRM = getDemandeXRMRandomSampleGenerator();
        PmEtablissement pmEtablissementBack = getPmEtablissementRandomSampleGenerator();

        demandeXRM.addPmEtablissements(pmEtablissementBack);
        assertThat(demandeXRM.getPmEtablissements()).containsOnly(pmEtablissementBack);
        assertThat(pmEtablissementBack.getDemandeXRM()).isEqualTo(demandeXRM);

        demandeXRM.removePmEtablissements(pmEtablissementBack);
        assertThat(demandeXRM.getPmEtablissements()).doesNotContain(pmEtablissementBack);
        assertThat(pmEtablissementBack.getDemandeXRM()).isNull();

        demandeXRM.pmEtablissements(new HashSet<>(Set.of(pmEtablissementBack)));
        assertThat(demandeXRM.getPmEtablissements()).containsOnly(pmEtablissementBack);
        assertThat(pmEtablissementBack.getDemandeXRM()).isEqualTo(demandeXRM);

        demandeXRM.setPmEtablissements(new HashSet<>());
        assertThat(demandeXRM.getPmEtablissements()).doesNotContain(pmEtablissementBack);
        assertThat(pmEtablissementBack.getDemandeXRM()).isNull();
    }
}
