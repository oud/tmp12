package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PmEtablissement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PmEtablissement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PmEtablissementRepository extends JpaRepository<PmEtablissement, Long> {}
