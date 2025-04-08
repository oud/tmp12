package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.MiseEnGestionRepository;
import com.mycompany.myapp.service.MiseEnGestionService;
import com.mycompany.myapp.service.dto.MiseEnGestionDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MiseEnGestion}.
 */
@RestController
@RequestMapping("/api/mise-en-gestions")
public class MiseEnGestionResource {

    private static final Logger LOG = LoggerFactory.getLogger(MiseEnGestionResource.class);

    private static final String ENTITY_NAME = "miseEnGestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MiseEnGestionService miseEnGestionService;

    private final MiseEnGestionRepository miseEnGestionRepository;

    public MiseEnGestionResource(MiseEnGestionService miseEnGestionService, MiseEnGestionRepository miseEnGestionRepository) {
        this.miseEnGestionService = miseEnGestionService;
        this.miseEnGestionRepository = miseEnGestionRepository;
    }

    /**
     * {@code POST  /mise-en-gestions} : Create a new miseEnGestion.
     *
     * @param miseEnGestionDTO the miseEnGestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new miseEnGestionDTO, or with status {@code 400 (Bad Request)} if the miseEnGestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MiseEnGestionDTO> createMiseEnGestion(@Valid @RequestBody MiseEnGestionDTO miseEnGestionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save MiseEnGestion : {}", miseEnGestionDTO);
        if (miseEnGestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new miseEnGestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        miseEnGestionDTO = miseEnGestionService.save(miseEnGestionDTO);
        return ResponseEntity.created(new URI("/api/mise-en-gestions/" + miseEnGestionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, miseEnGestionDTO.getId().toString()))
            .body(miseEnGestionDTO);
    }

    /**
     * {@code PUT  /mise-en-gestions/:id} : Updates an existing miseEnGestion.
     *
     * @param id the id of the miseEnGestionDTO to save.
     * @param miseEnGestionDTO the miseEnGestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated miseEnGestionDTO,
     * or with status {@code 400 (Bad Request)} if the miseEnGestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the miseEnGestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MiseEnGestionDTO> updateMiseEnGestion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MiseEnGestionDTO miseEnGestionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MiseEnGestion : {}, {}", id, miseEnGestionDTO);
        if (miseEnGestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, miseEnGestionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!miseEnGestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        miseEnGestionDTO = miseEnGestionService.update(miseEnGestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, miseEnGestionDTO.getId().toString()))
            .body(miseEnGestionDTO);
    }

    /**
     * {@code PATCH  /mise-en-gestions/:id} : Partial updates given fields of an existing miseEnGestion, field will ignore if it is null
     *
     * @param id the id of the miseEnGestionDTO to save.
     * @param miseEnGestionDTO the miseEnGestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated miseEnGestionDTO,
     * or with status {@code 400 (Bad Request)} if the miseEnGestionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the miseEnGestionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the miseEnGestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MiseEnGestionDTO> partialUpdateMiseEnGestion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MiseEnGestionDTO miseEnGestionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MiseEnGestion partially : {}, {}", id, miseEnGestionDTO);
        if (miseEnGestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, miseEnGestionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!miseEnGestionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MiseEnGestionDTO> result = miseEnGestionService.partialUpdate(miseEnGestionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, miseEnGestionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mise-en-gestions} : get all the miseEnGestions.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of miseEnGestions in body.
     */
    @GetMapping("")
    public List<MiseEnGestionDTO> getAllMiseEnGestions(@RequestParam(name = "filter", required = false) String filter) {
        if ("demandexrm-is-null".equals(filter)) {
            LOG.debug("REST request to get all MiseEnGestions where demandeXRM is null");
            return miseEnGestionService.findAllWhereDemandeXRMIsNull();
        }
        LOG.debug("REST request to get all MiseEnGestions");
        return miseEnGestionService.findAll();
    }

    /**
     * {@code GET  /mise-en-gestions/:id} : get the "id" miseEnGestion.
     *
     * @param id the id of the miseEnGestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the miseEnGestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MiseEnGestionDTO> getMiseEnGestion(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MiseEnGestion : {}", id);
        Optional<MiseEnGestionDTO> miseEnGestionDTO = miseEnGestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(miseEnGestionDTO);
    }

    /**
     * {@code DELETE  /mise-en-gestions/:id} : delete the "id" miseEnGestion.
     *
     * @param id the id of the miseEnGestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMiseEnGestion(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MiseEnGestion : {}", id);
        miseEnGestionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
