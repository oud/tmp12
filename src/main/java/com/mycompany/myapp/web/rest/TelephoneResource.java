package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TelephoneRepository;
import com.mycompany.myapp.service.TelephoneService;
import com.mycompany.myapp.service.dto.TelephoneDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Telephone}.
 */
@RestController
@RequestMapping("/api/telephones")
public class TelephoneResource {

    private static final Logger LOG = LoggerFactory.getLogger(TelephoneResource.class);

    private static final String ENTITY_NAME = "telephone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelephoneService telephoneService;

    private final TelephoneRepository telephoneRepository;

    public TelephoneResource(TelephoneService telephoneService, TelephoneRepository telephoneRepository) {
        this.telephoneService = telephoneService;
        this.telephoneRepository = telephoneRepository;
    }

    /**
     * {@code POST  /telephones} : Create a new telephone.
     *
     * @param telephoneDTO the telephoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telephoneDTO, or with status {@code 400 (Bad Request)} if the telephone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TelephoneDTO> createTelephone(@Valid @RequestBody TelephoneDTO telephoneDTO) throws URISyntaxException {
        LOG.debug("REST request to save Telephone : {}", telephoneDTO);
        if (telephoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new telephone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        telephoneDTO = telephoneService.save(telephoneDTO);
        return ResponseEntity.created(new URI("/api/telephones/" + telephoneDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, telephoneDTO.getId().toString()))
            .body(telephoneDTO);
    }

    /**
     * {@code PUT  /telephones/:id} : Updates an existing telephone.
     *
     * @param id the id of the telephoneDTO to save.
     * @param telephoneDTO the telephoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telephoneDTO,
     * or with status {@code 400 (Bad Request)} if the telephoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telephoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TelephoneDTO> updateTelephone(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TelephoneDTO telephoneDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Telephone : {}, {}", id, telephoneDTO);
        if (telephoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telephoneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telephoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        telephoneDTO = telephoneService.update(telephoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telephoneDTO.getId().toString()))
            .body(telephoneDTO);
    }

    /**
     * {@code PATCH  /telephones/:id} : Partial updates given fields of an existing telephone, field will ignore if it is null
     *
     * @param id the id of the telephoneDTO to save.
     * @param telephoneDTO the telephoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telephoneDTO,
     * or with status {@code 400 (Bad Request)} if the telephoneDTO is not valid,
     * or with status {@code 404 (Not Found)} if the telephoneDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the telephoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TelephoneDTO> partialUpdateTelephone(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TelephoneDTO telephoneDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Telephone partially : {}, {}", id, telephoneDTO);
        if (telephoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telephoneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telephoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TelephoneDTO> result = telephoneService.partialUpdate(telephoneDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telephoneDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /telephones} : get all the telephones.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telephones in body.
     */
    @GetMapping("")
    public List<TelephoneDTO> getAllTelephones() {
        LOG.debug("REST request to get all Telephones");
        return telephoneService.findAll();
    }

    /**
     * {@code GET  /telephones/:id} : get the "id" telephone.
     *
     * @param id the id of the telephoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telephoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TelephoneDTO> getTelephone(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Telephone : {}", id);
        Optional<TelephoneDTO> telephoneDTO = telephoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telephoneDTO);
    }

    /**
     * {@code DELETE  /telephones/:id} : delete the "id" telephone.
     *
     * @param id the id of the telephoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelephone(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Telephone : {}", id);
        telephoneService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
