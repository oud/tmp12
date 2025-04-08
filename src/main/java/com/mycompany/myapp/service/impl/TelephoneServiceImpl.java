package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Telephone;
import com.mycompany.myapp.repository.TelephoneRepository;
import com.mycompany.myapp.service.TelephoneService;
import com.mycompany.myapp.service.dto.TelephoneDTO;
import com.mycompany.myapp.service.mapper.TelephoneMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Telephone}.
 */
@Service
@Transactional
public class TelephoneServiceImpl implements TelephoneService {

    private static final Logger LOG = LoggerFactory.getLogger(TelephoneServiceImpl.class);

    private final TelephoneRepository telephoneRepository;

    private final TelephoneMapper telephoneMapper;

    public TelephoneServiceImpl(TelephoneRepository telephoneRepository, TelephoneMapper telephoneMapper) {
        this.telephoneRepository = telephoneRepository;
        this.telephoneMapper = telephoneMapper;
    }

    @Override
    public TelephoneDTO save(TelephoneDTO telephoneDTO) {
        LOG.debug("Request to save Telephone : {}", telephoneDTO);
        Telephone telephone = telephoneMapper.toEntity(telephoneDTO);
        telephone = telephoneRepository.save(telephone);
        return telephoneMapper.toDto(telephone);
    }

    @Override
    public TelephoneDTO update(TelephoneDTO telephoneDTO) {
        LOG.debug("Request to update Telephone : {}", telephoneDTO);
        Telephone telephone = telephoneMapper.toEntity(telephoneDTO);
        telephone = telephoneRepository.save(telephone);
        return telephoneMapper.toDto(telephone);
    }

    @Override
    public Optional<TelephoneDTO> partialUpdate(TelephoneDTO telephoneDTO) {
        LOG.debug("Request to partially update Telephone : {}", telephoneDTO);

        return telephoneRepository
            .findById(telephoneDTO.getId())
            .map(existingTelephone -> {
                telephoneMapper.partialUpdate(existingTelephone, telephoneDTO);

                return existingTelephone;
            })
            .map(telephoneRepository::save)
            .map(telephoneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TelephoneDTO> findAll() {
        LOG.debug("Request to get all Telephones");
        return telephoneRepository.findAll().stream().map(telephoneMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelephoneDTO> findOne(Long id) {
        LOG.debug("Request to get Telephone : {}", id);
        return telephoneRepository.findById(id).map(telephoneMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Telephone : {}", id);
        telephoneRepository.deleteById(id);
    }
}
