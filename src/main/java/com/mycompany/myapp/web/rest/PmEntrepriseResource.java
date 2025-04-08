package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PmEntrepriseRepository;
import com.mycompany.myapp.service.PmEntrepriseService;
import com.mycompany.myapp.service.dto.PmEntrepriseDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PmEntreprise}.
 */
@RestController
@RequestMapping("/api/pm-entreprises")
public class PmEntrepriseResource {

    private static final Logger LOG = LoggerFactory.getLogger(PmEntrepriseResource.class);

    private static final String ENTITY_NAME = "pmEntreprise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PmEntrepriseService pmEntrepriseService;

    private final PmEntrepriseRepository pmEntrepriseRepository;

    public PmEntrepriseResource(PmEntrepriseService pmEntrepriseService, PmEntrepriseRepository pmEntrepriseRepository) {
        this.pmEntrepriseService = pmEntrepriseService;
        this.pmEntrepriseRepository = pmEntrepriseRepository;
    }

    /**
     * {@code POST  /pm-entreprises} : Create a new pmEntreprise.
     *
     * @param pmEntrepriseDTO the pmEntrepriseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pmEntrepriseDTO, or with status {@code 400 (Bad Request)} if the pmEntreprise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PmEntrepriseDTO> createPmEntreprise(@Valid @RequestBody PmEntrepriseDTO pmEntrepriseDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PmEntreprise : {}", pmEntrepriseDTO);
        if (pmEntrepriseDTO.getId() != null) {
            throw new BadRequestAlertException("A new pmEntreprise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pmEntrepriseDTO = pmEntrepriseService.save(pmEntrepriseDTO);
        return ResponseEntity.created(new URI("/api/pm-entreprises/" + pmEntrepriseDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pmEntrepriseDTO.getId().toString()))
            .body(pmEntrepriseDTO);
    }

    /**
     * {@code PUT  /pm-entreprises/:id} : Updates an existing pmEntreprise.
     *
     * @param id the id of the pmEntrepriseDTO to save.
     * @param pmEntrepriseDTO the pmEntrepriseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pmEntrepriseDTO,
     * or with status {@code 400 (Bad Request)} if the pmEntrepriseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pmEntrepriseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PmEntrepriseDTO> updatePmEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PmEntrepriseDTO pmEntrepriseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PmEntreprise : {}, {}", id, pmEntrepriseDTO);
        if (pmEntrepriseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pmEntrepriseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pmEntrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pmEntrepriseDTO = pmEntrepriseService.update(pmEntrepriseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pmEntrepriseDTO.getId().toString()))
            .body(pmEntrepriseDTO);
    }

    /**
     * {@code PATCH  /pm-entreprises/:id} : Partial updates given fields of an existing pmEntreprise, field will ignore if it is null
     *
     * @param id the id of the pmEntrepriseDTO to save.
     * @param pmEntrepriseDTO the pmEntrepriseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pmEntrepriseDTO,
     * or with status {@code 400 (Bad Request)} if the pmEntrepriseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pmEntrepriseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pmEntrepriseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PmEntrepriseDTO> partialUpdatePmEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PmEntrepriseDTO pmEntrepriseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PmEntreprise partially : {}, {}", id, pmEntrepriseDTO);
        if (pmEntrepriseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pmEntrepriseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pmEntrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PmEntrepriseDTO> result = pmEntrepriseService.partialUpdate(pmEntrepriseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pmEntrepriseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pm-entreprises} : get all the pmEntreprises.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pmEntreprises in body.
     */
    @GetMapping("")
    public List<PmEntrepriseDTO> getAllPmEntreprises(@RequestParam(name = "filter", required = false) String filter) {
        if ("demandexrm-is-null".equals(filter)) {
            LOG.debug("REST request to get all PmEntreprises where demandeXRM is null");
            return pmEntrepriseService.findAllWhereDemandeXRMIsNull();
        }
        LOG.debug("REST request to get all PmEntreprises");
        return pmEntrepriseService.findAll();
    }

    /**
     * {@code GET  /pm-entreprises/:id} : get the "id" pmEntreprise.
     *
     * @param id the id of the pmEntrepriseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pmEntrepriseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PmEntrepriseDTO> getPmEntreprise(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PmEntreprise : {}", id);
        Optional<PmEntrepriseDTO> pmEntrepriseDTO = pmEntrepriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pmEntrepriseDTO);
    }

    /**
     * {@code DELETE  /pm-entreprises/:id} : delete the "id" pmEntreprise.
     *
     * @param id the id of the pmEntrepriseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePmEntreprise(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PmEntreprise : {}", id);
        pmEntrepriseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
