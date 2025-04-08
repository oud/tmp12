package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EmailTestSamples.*;
import static com.mycompany.myapp.domain.PmEntrepriseTestSamples.*;
import static com.mycompany.myapp.domain.PmEtablissementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Email.class);
        Email email1 = getEmailSample1();
        Email email2 = new Email();
        assertThat(email1).isNotEqualTo(email2);

        email2.setId(email1.getId());
        assertThat(email1).isEqualTo(email2);

        email2 = getEmailSample2();
        assertThat(email1).isNotEqualTo(email2);
    }

    @Test
    void pmEntrepriseTest() {
        Email email = getEmailRandomSampleGenerator();
        PmEntreprise pmEntrepriseBack = getPmEntrepriseRandomSampleGenerator();

        email.setPmEntreprise(pmEntrepriseBack);
        assertThat(email.getPmEntreprise()).isEqualTo(pmEntrepriseBack);

        email.pmEntreprise(null);
        assertThat(email.getPmEntreprise()).isNull();
    }

    @Test
    void pmEtablissementTest() {
        Email email = getEmailRandomSampleGenerator();
        PmEtablissement pmEtablissementBack = getPmEtablissementRandomSampleGenerator();

        email.setPmEtablissement(pmEtablissementBack);
        assertThat(email.getPmEtablissement()).isEqualTo(pmEtablissementBack);

        email.pmEtablissement(null);
        assertThat(email.getPmEtablissement()).isNull();
    }
}
