package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.DemandeXRM;
import com.mycompany.myapp.repository.DemandeXRMRepository;
import com.mycompany.myapp.service.DemandeXRMService;
import com.mycompany.myapp.service.dto.DemandeXRMDTO;
import com.mycompany.myapp.service.mapper.DemandeXRMMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.DemandeXRM}.
 */
@Service
@Transactional
public class DemandeXRMServiceImpl implements DemandeXRMService {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeXRMServiceImpl.class);

    private final DemandeXRMRepository demandeXRMRepository;

    private final DemandeXRMMapper demandeXRMMapper;

    public DemandeXRMServiceImpl(DemandeXRMRepository demandeXRMRepository, DemandeXRMMapper demandeXRMMapper) {
        this.demandeXRMRepository = demandeXRMRepository;
        this.demandeXRMMapper = demandeXRMMapper;
    }

    @Override
    public DemandeXRMDTO save(DemandeXRMDTO demandeXRMDTO) {
        LOG.debug("Request to save DemandeXRM : {}", demandeXRMDTO);
        DemandeXRM demandeXRM = demandeXRMMapper.toEntity(demandeXRMDTO);
        demandeXRM = demandeXRMRepository.save(demandeXRM);
        return demandeXRMMapper.toDto(demandeXRM);
    }

    @Override
    public DemandeXRMDTO update(DemandeXRMDTO demandeXRMDTO) {
        LOG.debug("Request to update DemandeXRM : {}", demandeXRMDTO);
        DemandeXRM demandeXRM = demandeXRMMapper.toEntity(demandeXRMDTO);
        demandeXRM = demandeXRMRepository.save(demandeXRM);
        return demandeXRMMapper.toDto(demandeXRM);
    }

    @Override
    public Optional<DemandeXRMDTO> partialUpdate(DemandeXRMDTO demandeXRMDTO) {
        LOG.debug("Request to partially update DemandeXRM : {}", demandeXRMDTO);

        return demandeXRMRepository
            .findById(demandeXRMDTO.getId())
            .map(existingDemandeXRM -> {
                demandeXRMMapper.partialUpdate(existingDemandeXRM, demandeXRMDTO);

                return existingDemandeXRM;
            })
            .map(demandeXRMRepository::save)
            .map(demandeXRMMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DemandeXRMDTO> findAll() {
        LOG.debug("Request to get all DemandeXRMS");
        return demandeXRMRepository.findAll().stream().map(demandeXRMMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeXRMDTO> findOne(Long id) {
        LOG.debug("Request to get DemandeXRM : {}", id);
        return demandeXRMRepository.findById(id).map(demandeXRMMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DemandeXRM : {}", id);
        demandeXRMRepository.deleteById(id);
    }
}
