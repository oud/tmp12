package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DemandeXRMDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.DemandeXRM}.
 */
public interface DemandeXRMService {
    /**
     * Save a demandeXRM.
     *
     * @param demandeXRMDTO the entity to save.
     * @return the persisted entity.
     */
    DemandeXRMDTO save(DemandeXRMDTO demandeXRMDTO);

    /**
     * Updates a demandeXRM.
     *
     * @param demandeXRMDTO the entity to update.
     * @return the persisted entity.
     */
    DemandeXRMDTO update(DemandeXRMDTO demandeXRMDTO);

    /**
     * Partially updates a demandeXRM.
     *
     * @param demandeXRMDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandeXRMDTO> partialUpdate(DemandeXRMDTO demandeXRMDTO);

    /**
     * Get all the demandeXRMS.
     *
     * @return the list of entities.
     */
    List<DemandeXRMDTO> findAll();

    /**
     * Get the "id" demandeXRM.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandeXRMDTO> findOne(Long id);

    /**
     * Delete the "id" demandeXRM.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
