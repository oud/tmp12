package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.PmEntrepriseTestSamples.*;
import static com.mycompany.myapp.domain.PmEtablissementTestSamples.*;
import static com.mycompany.myapp.domain.TelephoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelephoneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telephone.class);
        Telephone telephone1 = getTelephoneSample1();
        Telephone telephone2 = new Telephone();
        assertThat(telephone1).isNotEqualTo(telephone2);

        telephone2.setId(telephone1.getId());
        assertThat(telephone1).isEqualTo(telephone2);

        telephone2 = getTelephoneSample2();
        assertThat(telephone1).isNotEqualTo(telephone2);
    }

    @Test
    void pmEntrepriseTest() {
        Telephone telephone = getTelephoneRandomSampleGenerator();
        PmEntreprise pmEntrepriseBack = getPmEntrepriseRandomSampleGenerator();

        telephone.setPmEntreprise(pmEntrepriseBack);
        assertThat(telephone.getPmEntreprise()).isEqualTo(pmEntrepriseBack);

        telephone.pmEntreprise(null);
        assertThat(telephone.getPmEntreprise()).isNull();
    }

    @Test
    void pmEtablissementTest() {
        Telephone telephone = getTelephoneRandomSampleGenerator();
        PmEtablissement pmEtablissementBack = getPmEtablissementRandomSampleGenerator();

        telephone.setPmEtablissement(pmEtablissementBack);
        assertThat(telephone.getPmEtablissement()).isEqualTo(pmEtablissementBack);

        telephone.pmEtablissement(null);
        assertThat(telephone.getPmEtablissement()).isNull();
    }
}
