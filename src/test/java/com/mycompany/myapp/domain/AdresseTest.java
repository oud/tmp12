package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AdresseTestSamples.*;
import static com.mycompany.myapp.domain.PmEntrepriseTestSamples.*;
import static com.mycompany.myapp.domain.PmEtablissementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdresseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresse.class);
        Adresse adresse1 = getAdresseSample1();
        Adresse adresse2 = new Adresse();
        assertThat(adresse1).isNotEqualTo(adresse2);

        adresse2.setId(adresse1.getId());
        assertThat(adresse1).isEqualTo(adresse2);

        adresse2 = getAdresseSample2();
        assertThat(adresse1).isNotEqualTo(adresse2);
    }

    @Test
    void pmEntrepriseTest() {
        Adresse adresse = getAdresseRandomSampleGenerator();
        PmEntreprise pmEntrepriseBack = getPmEntrepriseRandomSampleGenerator();

        adresse.setPmEntreprise(pmEntrepriseBack);
        assertThat(adresse.getPmEntreprise()).isEqualTo(pmEntrepriseBack);

        adresse.pmEntreprise(null);
        assertThat(adresse.getPmEntreprise()).isNull();
    }

    @Test
    void pmEtablissementTest() {
        Adresse adresse = getAdresseRandomSampleGenerator();
        PmEtablissement pmEtablissementBack = getPmEtablissementRandomSampleGenerator();

        adresse.setPmEtablissement(pmEtablissementBack);
        assertThat(adresse.getPmEtablissement()).isEqualTo(pmEtablissementBack);

        adresse.pmEtablissement(null);
        assertThat(adresse.getPmEtablissement()).isNull();
    }
}
