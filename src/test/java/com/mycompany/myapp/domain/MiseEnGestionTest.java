package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DemandeXRMTestSamples.*;
import static com.mycompany.myapp.domain.MiseEnGestionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MiseEnGestionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MiseEnGestion.class);
        MiseEnGestion miseEnGestion1 = getMiseEnGestionSample1();
        MiseEnGestion miseEnGestion2 = new MiseEnGestion();
        assertThat(miseEnGestion1).isNotEqualTo(miseEnGestion2);

        miseEnGestion2.setId(miseEnGestion1.getId());
        assertThat(miseEnGestion1).isEqualTo(miseEnGestion2);

        miseEnGestion2 = getMiseEnGestionSample2();
        assertThat(miseEnGestion1).isNotEqualTo(miseEnGestion2);
    }

    @Test
    void demandeXRMTest() {
        MiseEnGestion miseEnGestion = getMiseEnGestionRandomSampleGenerator();
        DemandeXRM demandeXRMBack = getDemandeXRMRandomSampleGenerator();

        miseEnGestion.setDemandeXRM(demandeXRMBack);
        assertThat(miseEnGestion.getDemandeXRM()).isEqualTo(demandeXRMBack);
        assertThat(demandeXRMBack.getMiseEnGestion()).isEqualTo(miseEnGestion);

        miseEnGestion.demandeXRM(null);
        assertThat(miseEnGestion.getDemandeXRM()).isNull();
        assertThat(demandeXRMBack.getMiseEnGestion()).isNull();
    }
}
