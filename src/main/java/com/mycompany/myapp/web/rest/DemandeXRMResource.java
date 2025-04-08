package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DemandeXRMRepository;
import com.mycompany.myapp.service.DemandeXRMService;
import com.mycompany.myapp.service.dto.DemandeXRMDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DemandeXRM}.
 */
@RestController
@RequestMapping("/api/demande-xrms")
public class DemandeXRMResource {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeXRMResource.class);

    private static final String ENTITY_NAME = "demandeXRM";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeXRMService demandeXRMService;

    private final DemandeXRMRepository demandeXRMRepository;

    public DemandeXRMResource(DemandeXRMService demandeXRMService, DemandeXRMRepository demandeXRMRepository) {
        this.demandeXRMService = demandeXRMService;
        this.demandeXRMRepository = demandeXRMRepository;
    }

    /**
     * {@code POST  /demande-xrms} : Create a new demandeXRM.
     *
     * @param demandeXRMDTO the demandeXRMDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeXRMDTO, or with status {@code 400 (Bad Request)} if the demandeXRM has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DemandeXRMDTO> createDemandeXRM(@RequestBody DemandeXRMDTO demandeXRMDTO) throws URISyntaxException {
        LOG.debug("REST request to save DemandeXRM : {}", demandeXRMDTO);
        if (demandeXRMDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandeXRM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        demandeXRMDTO = demandeXRMService.save(demandeXRMDTO);
        return ResponseEntity.created(new URI("/api/demande-xrms/" + demandeXRMDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, demandeXRMDTO.getId().toString()))
            .body(demandeXRMDTO);
    }

    /**
     * {@code PUT  /demande-xrms/:id} : Updates an existing demandeXRM.
     *
     * @param id the id of the demandeXRMDTO to save.
     * @param demandeXRMDTO the demandeXRMDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeXRMDTO,
     * or with status {@code 400 (Bad Request)} if the demandeXRMDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeXRMDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DemandeXRMDTO> updateDemandeXRM(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeXRMDTO demandeXRMDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DemandeXRM : {}, {}", id, demandeXRMDTO);
        if (demandeXRMDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeXRMDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeXRMRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        demandeXRMDTO = demandeXRMService.update(demandeXRMDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeXRMDTO.getId().toString()))
            .body(demandeXRMDTO);
    }

    /**
     * {@code PATCH  /demande-xrms/:id} : Partial updates given fields of an existing demandeXRM, field will ignore if it is null
     *
     * @param id the id of the demandeXRMDTO to save.
     * @param demandeXRMDTO the demandeXRMDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeXRMDTO,
     * or with status {@code 400 (Bad Request)} if the demandeXRMDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandeXRMDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeXRMDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeXRMDTO> partialUpdateDemandeXRM(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeXRMDTO demandeXRMDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DemandeXRM partially : {}, {}", id, demandeXRMDTO);
        if (demandeXRMDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeXRMDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeXRMRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeXRMDTO> result = demandeXRMService.partialUpdate(demandeXRMDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeXRMDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-xrms} : get all the demandeXRMS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeXRMS in body.
     */
    @GetMapping("")
    public List<DemandeXRMDTO> getAllDemandeXRMS() {
        LOG.debug("REST request to get all DemandeXRMS");
        return demandeXRMService.findAll();
    }

    /**
     * {@code GET  /demande-xrms/:id} : get the "id" demandeXRM.
     *
     * @param id the id of the demandeXRMDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeXRMDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemandeXRMDTO> getDemandeXRM(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DemandeXRM : {}", id);
        Optional<DemandeXRMDTO> demandeXRMDTO = demandeXRMService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeXRMDTO);
    }

    /**
     * {@code DELETE  /demande-xrms/:id} : delete the "id" demandeXRM.
     *
     * @param id the id of the demandeXRMDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemandeXRM(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DemandeXRM : {}", id);
        demandeXRMService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
