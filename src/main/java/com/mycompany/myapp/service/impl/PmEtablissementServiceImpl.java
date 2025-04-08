package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PmEtablissement;
import com.mycompany.myapp.repository.PmEtablissementRepository;
import com.mycompany.myapp.service.PmEtablissementService;
import com.mycompany.myapp.service.dto.PmEtablissementDTO;
import com.mycompany.myapp.service.mapper.PmEtablissementMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.PmEtablissement}.
 */
@Service
@Transactional
public class PmEtablissementServiceImpl implements PmEtablissementService {

    private static final Logger LOG = LoggerFactory.getLogger(PmEtablissementServiceImpl.class);

    private final PmEtablissementRepository pmEtablissementRepository;

    private final PmEtablissementMapper pmEtablissementMapper;

    public PmEtablissementServiceImpl(PmEtablissementRepository pmEtablissementRepository, PmEtablissementMapper pmEtablissementMapper) {
        this.pmEtablissementRepository = pmEtablissementRepository;
        this.pmEtablissementMapper = pmEtablissementMapper;
    }

    @Override
    public PmEtablissementDTO save(PmEtablissementDTO pmEtablissementDTO) {
        LOG.debug("Request to save PmEtablissement : {}", pmEtablissementDTO);
        PmEtablissement pmEtablissement = pmEtablissementMapper.toEntity(pmEtablissementDTO);
        pmEtablissement = pmEtablissementRepository.save(pmEtablissement);
        return pmEtablissementMapper.toDto(pmEtablissement);
    }

    @Override
    public PmEtablissementDTO update(PmEtablissementDTO pmEtablissementDTO) {
        LOG.debug("Request to update PmEtablissement : {}", pmEtablissementDTO);
        PmEtablissement pmEtablissement = pmEtablissementMapper.toEntity(pmEtablissementDTO);
        pmEtablissement = pmEtablissementRepository.save(pmEtablissement);
        return pmEtablissementMapper.toDto(pmEtablissement);
    }

    @Override
    public Optional<PmEtablissementDTO> partialUpdate(PmEtablissementDTO pmEtablissementDTO) {
        LOG.debug("Request to partially update PmEtablissement : {}", pmEtablissementDTO);

        return pmEtablissementRepository
            .findById(pmEtablissementDTO.getId())
            .map(existingPmEtablissement -> {
                pmEtablissementMapper.partialUpdate(existingPmEtablissement, pmEtablissementDTO);

                return existingPmEtablissement;
            })
            .map(pmEtablissementRepository::save)
            .map(pmEtablissementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PmEtablissementDTO> findAll() {
        LOG.debug("Request to get all PmEtablissements");
        return pmEtablissementRepository
            .findAll()
            .stream()
            .map(pmEtablissementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PmEtablissementDTO> findOne(Long id) {
        LOG.debug("Request to get PmEtablissement : {}", id);
        return pmEtablissementRepository.findById(id).map(pmEtablissementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PmEtablissement : {}", id);
        pmEtablissementRepository.deleteById(id);
    }
}
