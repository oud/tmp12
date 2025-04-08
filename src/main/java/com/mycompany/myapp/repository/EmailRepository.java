package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Email;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Email entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {}
