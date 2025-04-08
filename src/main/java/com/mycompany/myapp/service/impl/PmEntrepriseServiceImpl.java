package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PmEntreprise;
import com.mycompany.myapp.repository.PmEntrepriseRepository;
import com.mycompany.myapp.service.PmEntrepriseService;
import com.mycompany.myapp.service.dto.PmEntrepriseDTO;
import com.mycompany.myapp.service.mapper.PmEntrepriseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.PmEntreprise}.
 */
@Service
@Transactional
public class PmEntrepriseServiceImpl implements PmEntrepriseService {

    private static final Logger LOG = LoggerFactory.getLogger(PmEntrepriseServiceImpl.class);

    private final PmEntrepriseRepository pmEntrepriseRepository;

    private final PmEntrepriseMapper pmEntrepriseMapper;

    public PmEntrepriseServiceImpl(PmEntrepriseRepository pmEntrepriseRepository, PmEntrepriseMapper pmEntrepriseMapper) {
        this.pmEntrepriseRepository = pmEntrepriseRepository;
        this.pmEntrepriseMapper = pmEntrepriseMapper;
    }

    @Override
    public PmEntrepriseDTO save(PmEntrepriseDTO pmEntrepriseDTO) {
        LOG.debug("Request to save PmEntreprise : {}", pmEntrepriseDTO);
        PmEntreprise pmEntreprise = pmEntrepriseMapper.toEntity(pmEntrepriseDTO);
        pmEntreprise = pmEntrepriseRepository.save(pmEntreprise);
        return pmEntrepriseMapper.toDto(pmEntreprise);
    }

    @Override
    public PmEntrepriseDTO update(PmEntrepriseDTO pmEntrepriseDTO) {
        LOG.debug("Request to update PmEntreprise : {}", pmEntrepriseDTO);
        PmEntreprise pmEntreprise = pmEntrepriseMapper.toEntity(pmEntrepriseDTO);
        pmEntreprise = pmEntrepriseRepository.save(pmEntreprise);
        return pmEntrepriseMapper.toDto(pmEntreprise);
    }

    @Override
    public Optional<PmEntrepriseDTO> partialUpdate(PmEntrepriseDTO pmEntrepriseDTO) {
        LOG.debug("Request to partially update PmEntreprise : {}", pmEntrepriseDTO);

        return pmEntrepriseRepository
            .findById(pmEntrepriseDTO.getId())
            .map(existingPmEntreprise -> {
                pmEntrepriseMapper.partialUpdate(existingPmEntreprise, pmEntrepriseDTO);

                return existingPmEntreprise;
            })
            .map(pmEntrepriseRepository::save)
            .map(pmEntrepriseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PmEntrepriseDTO> findAll() {
        LOG.debug("Request to get all PmEntreprises");
        return pmEntrepriseRepository.findAll().stream().map(pmEntrepriseMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the pmEntreprises where DemandeXRM is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PmEntrepriseDTO> findAllWhereDemandeXRMIsNull() {
        LOG.debug("Request to get all pmEntreprises where DemandeXRM is null");
        return StreamSupport.stream(pmEntrepriseRepository.findAll().spliterator(), false)
            .filter(pmEntreprise -> pmEntreprise.getDemandeXRM() == null)
            .map(pmEntrepriseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PmEntrepriseDTO> findOne(Long id) {
        LOG.debug("Request to get PmEntreprise : {}", id);
        return pmEntrepriseRepository.findById(id).map(pmEntrepriseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PmEntreprise : {}", id);
        pmEntrepriseRepository.deleteById(id);
    }
}
