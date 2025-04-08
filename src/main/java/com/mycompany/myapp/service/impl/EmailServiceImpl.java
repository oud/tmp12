package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Email;
import com.mycompany.myapp.repository.EmailRepository;
import com.mycompany.myapp.service.EmailService;
import com.mycompany.myapp.service.dto.EmailDTO;
import com.mycompany.myapp.service.mapper.EmailMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Email}.
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailRepository emailRepository;

    private final EmailMapper emailMapper;

    public EmailServiceImpl(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    @Override
    public EmailDTO save(EmailDTO emailDTO) {
        LOG.debug("Request to save Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    @Override
    public EmailDTO update(EmailDTO emailDTO) {
        LOG.debug("Request to update Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    @Override
    public Optional<EmailDTO> partialUpdate(EmailDTO emailDTO) {
        LOG.debug("Request to partially update Email : {}", emailDTO);

        return emailRepository
            .findById(emailDTO.getId())
            .map(existingEmail -> {
                emailMapper.partialUpdate(existingEmail, emailDTO);

                return existingEmail;
            })
            .map(emailRepository::save)
            .map(emailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailDTO> findAll() {
        LOG.debug("Request to get all Emails");
        return emailRepository.findAll().stream().map(emailMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmailDTO> findOne(Long id) {
        LOG.debug("Request to get Email : {}", id);
        return emailRepository.findById(id).map(emailMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Email : {}", id);
        emailRepository.deleteById(id);
    }
}
