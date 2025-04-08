package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.MiseEnGestion;
import com.mycompany.myapp.repository.MiseEnGestionRepository;
import com.mycompany.myapp.service.MiseEnGestionService;
import com.mycompany.myapp.service.dto.MiseEnGestionDTO;
import com.mycompany.myapp.service.mapper.MiseEnGestionMapper;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.MiseEnGestion}.
 */
@Service
@Transactional
public class MiseEnGestionServiceImpl implements MiseEnGestionService {

    private static final Logger LOG = LoggerFactory.getLogger(MiseEnGestionServiceImpl.class);

    private final MiseEnGestionRepository miseEnGestionRepository;

    private final MiseEnGestionMapper miseEnGestionMapper;

    public MiseEnGestionServiceImpl(MiseEnGestionRepository miseEnGestionRepository, MiseEnGestionMapper miseEnGestionMapper) {
        this.miseEnGestionRepository = miseEnGestionRepository;
        this.miseEnGestionMapper = miseEnGestionMapper;
    }

    @Override
    public MiseEnGestionDTO save(MiseEnGestionDTO miseEnGestionDTO) {
        LOG.debug("Request to save MiseEnGestion : {}", miseEnGestionDTO);
        MiseEnGestion miseEnGestion = miseEnGestionMapper.toEntity(miseEnGestionDTO);
        miseEnGestion = miseEnGestionRepository.save(miseEnGestion);
        return miseEnGestionMapper.toDto(miseEnGestion);
    }

    @Override
    public MiseEnGestionDTO update(MiseEnGestionDTO miseEnGestionDTO) {
        LOG.debug("Request to update MiseEnGestion : {}", miseEnGestionDTO);
        MiseEnGestion miseEnGestion = miseEnGestionMapper.toEntity(miseEnGestionDTO);
        miseEnGestion = miseEnGestionRepository.save(miseEnGestion);
        return miseEnGestionMapper.toDto(miseEnGestion);
    }

    @Override
    public Optional<MiseEnGestionDTO> partialUpdate(MiseEnGestionDTO miseEnGestionDTO) {
        LOG.debug("Request to partially update MiseEnGestion : {}", miseEnGestionDTO);

        return miseEnGestionRepository
            .findById(miseEnGestionDTO.getId())
            .map(existingMiseEnGestion -> {
                miseEnGestionMapper.partialUpdate(existingMiseEnGestion, miseEnGestionDTO);

                return existingMiseEnGestion;
            })
            .map(miseEnGestionRepository::save)
            .map(miseEnGestionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MiseEnGestionDTO> findAll() {
        LOG.debug("Request to get all MiseEnGestions");
        return miseEnGestionRepository.findAll().stream().map(miseEnGestionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the miseEnGestions where DemandeXRM is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MiseEnGestionDTO> findAllWhereDemandeXRMIsNull() {
        LOG.debug("Request to get all miseEnGestions where DemandeXRM is null");
        return StreamSupport.stream(miseEnGestionRepository.findAll().spliterator(), false)
            .filter(miseEnGestion -> miseEnGestion.getDemandeXRM() == null)
            .map(miseEnGestionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MiseEnGestionDTO> findOne(Long id) {
        LOG.debug("Request to get MiseEnGestion : {}", id);
        return miseEnGestionRepository.findById(id).map(miseEnGestionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete MiseEnGestion : {}", id);
        miseEnGestionRepository.deleteById(id);
    }
}
