package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TelephoneDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Telephone}.
 */
public interface TelephoneService {
    /**
     * Save a telephone.
     *
     * @param telephoneDTO the entity to save.
     * @return the persisted entity.
     */
    TelephoneDTO save(TelephoneDTO telephoneDTO);

    /**
     * Updates a telephone.
     *
     * @param telephoneDTO the entity to update.
     * @return the persisted entity.
     */
    TelephoneDTO update(TelephoneDTO telephoneDTO);

    /**
     * Partially updates a telephone.
     *
     * @param telephoneDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TelephoneDTO> partialUpdate(TelephoneDTO telephoneDTO);

    /**
     * Get all the telephones.
     *
     * @return the list of entities.
     */
    List<TelephoneDTO> findAll();

    /**
     * Get the "id" telephone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TelephoneDTO> findOne(Long id);

    /**
     * Delete the "id" telephone.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
