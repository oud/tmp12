package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PmEtablissementRepository;
import com.mycompany.myapp.service.PmEtablissementService;
import com.mycompany.myapp.service.dto.PmEtablissementDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PmEtablissement}.
 */
@RestController
@RequestMapping("/api/pm-etablissements")
public class PmEtablissementResource {

    private static final Logger LOG = LoggerFactory.getLogger(PmEtablissementResource.class);

    private static final String ENTITY_NAME = "pmEtablissement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PmEtablissementService pmEtablissementService;

    private final PmEtablissementRepository pmEtablissementRepository;

    public PmEtablissementResource(PmEtablissementService pmEtablissementService, PmEtablissementRepository pmEtablissementRepository) {
        this.pmEtablissementService = pmEtablissementService;
        this.pmEtablissementRepository = pmEtablissementRepository;
    }

    /**
     * {@code POST  /pm-etablissements} : Create a new pmEtablissement.
     *
     * @param pmEtablissementDTO the pmEtablissementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pmEtablissementDTO, or with status {@code 400 (Bad Request)} if the pmEtablissement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PmEtablissementDTO> createPmEtablissement(@Valid @RequestBody PmEtablissementDTO pmEtablissementDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PmEtablissement : {}", pmEtablissementDTO);
        if (pmEtablissementDTO.getId() != null) {
            throw new BadRequestAlertException("A new pmEtablissement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pmEtablissementDTO = pmEtablissementService.save(pmEtablissementDTO);
        return ResponseEntity.created(new URI("/api/pm-etablissements/" + pmEtablissementDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pmEtablissementDTO.getId().toString()))
            .body(pmEtablissementDTO);
    }

    /**
     * {@code PUT  /pm-etablissements/:id} : Updates an existing pmEtablissement.
     *
     * @param id the id of the pmEtablissementDTO to save.
     * @param pmEtablissementDTO the pmEtablissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pmEtablissementDTO,
     * or with status {@code 400 (Bad Request)} if the pmEtablissementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pmEtablissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PmEtablissementDTO> updatePmEtablissement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PmEtablissementDTO pmEtablissementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PmEtablissement : {}, {}", id, pmEtablissementDTO);
        if (pmEtablissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pmEtablissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pmEtablissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pmEtablissementDTO = pmEtablissementService.update(pmEtablissementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pmEtablissementDTO.getId().toString()))
            .body(pmEtablissementDTO);
    }

    /**
     * {@code PATCH  /pm-etablissements/:id} : Partial updates given fields of an existing pmEtablissement, field will ignore if it is null
     *
     * @param id the id of the pmEtablissementDTO to save.
     * @param pmEtablissementDTO the pmEtablissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pmEtablissementDTO,
     * or with status {@code 400 (Bad Request)} if the pmEtablissementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pmEtablissementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pmEtablissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PmEtablissementDTO> partialUpdatePmEtablissement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PmEtablissementDTO pmEtablissementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PmEtablissement partially : {}, {}", id, pmEtablissementDTO);
        if (pmEtablissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pmEtablissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pmEtablissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PmEtablissementDTO> result = pmEtablissementService.partialUpdate(pmEtablissementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pmEtablissementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pm-etablissements} : get all the pmEtablissements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pmEtablissements in body.
     */
    @GetMapping("")
    public List<PmEtablissementDTO> getAllPmEtablissements() {
        LOG.debug("REST request to get all PmEtablissements");
        return pmEtablissementService.findAll();
    }

    /**
     * {@code GET  /pm-etablissements/:id} : get the "id" pmEtablissement.
     *
     * @param id the id of the pmEtablissementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pmEtablissementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PmEtablissementDTO> getPmEtablissement(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PmEtablissement : {}", id);
        Optional<PmEtablissementDTO> pmEtablissementDTO = pmEtablissementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pmEtablissementDTO);
    }

    /**
     * {@code DELETE  /pm-etablissements/:id} : delete the "id" pmEtablissement.
     *
     * @param id the id of the pmEtablissementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePmEtablissement(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PmEtablissement : {}", id);
        pmEtablissementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
