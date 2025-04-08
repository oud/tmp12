package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeXRMDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeXRMDTO.class);
        DemandeXRMDTO demandeXRMDTO1 = new DemandeXRMDTO();
        demandeXRMDTO1.setId(1L);
        DemandeXRMDTO demandeXRMDTO2 = new DemandeXRMDTO();
        assertThat(demandeXRMDTO1).isNotEqualTo(demandeXRMDTO2);
        demandeXRMDTO2.setId(demandeXRMDTO1.getId());
        assertThat(demandeXRMDTO1).isEqualTo(demandeXRMDTO2);
        demandeXRMDTO2.setId(2L);
        assertThat(demandeXRMDTO1).isNotEqualTo(demandeXRMDTO2);
        demandeXRMDTO1.setId(null);
        assertThat(demandeXRMDTO1).isNotEqualTo(demandeXRMDTO2);
    }
}
