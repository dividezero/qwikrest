package com.hazlanrozaimi.qwikrest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hazlanrozaimi.qwikrest.domain.ArtefactStructure;
import com.hazlanrozaimi.qwikrest.service.ArtefactStructureService;
import com.hazlanrozaimi.qwikrest.web.rest.util.HeaderUtil;
import com.hazlanrozaimi.qwikrest.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ArtefactStructure.
 */
@RestController
@RequestMapping("/api")
public class ArtefactStructureResource {

    private final Logger log = LoggerFactory.getLogger(ArtefactStructureResource.class);

    private static final String ENTITY_NAME = "artefactStructure";
        
    private final ArtefactStructureService artefactStructureService;

    public ArtefactStructureResource(ArtefactStructureService artefactStructureService) {
        this.artefactStructureService = artefactStructureService;
    }

    /**
     * POST  /artefact-structures : Create a new artefactStructure.
     *
     * @param artefactStructure the artefactStructure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new artefactStructure, or with status 400 (Bad Request) if the artefactStructure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artefact-structures")
    @Timed
    public ResponseEntity<ArtefactStructure> createArtefactStructure(@Valid @RequestBody ArtefactStructure artefactStructure) throws URISyntaxException {
        log.debug("REST request to save ArtefactStructure : {}", artefactStructure);
        if (artefactStructure.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new artefactStructure cannot already have an ID")).body(null);
        }
        ArtefactStructure result = artefactStructureService.save(artefactStructure);
        return ResponseEntity.created(new URI("/api/artefact-structures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artefact-structures : Updates an existing artefactStructure.
     *
     * @param artefactStructure the artefactStructure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated artefactStructure,
     * or with status 400 (Bad Request) if the artefactStructure is not valid,
     * or with status 500 (Internal Server Error) if the artefactStructure couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artefact-structures")
    @Timed
    public ResponseEntity<ArtefactStructure> updateArtefactStructure(@Valid @RequestBody ArtefactStructure artefactStructure) throws URISyntaxException {
        log.debug("REST request to update ArtefactStructure : {}", artefactStructure);
        if (artefactStructure.getId() == null) {
            return createArtefactStructure(artefactStructure);
        }
        ArtefactStructure result = artefactStructureService.save(artefactStructure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, artefactStructure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artefact-structures : get all the artefactStructures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of artefactStructures in body
     */
    @GetMapping("/artefact-structures")
    @Timed
    public ResponseEntity<List<ArtefactStructure>> getAllArtefactStructures(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ArtefactStructures");
        Page<ArtefactStructure> page = artefactStructureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/artefact-structures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /artefact-structures/:id : get the "id" artefactStructure.
     *
     * @param id the id of the artefactStructure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the artefactStructure, or with status 404 (Not Found)
     */
    @GetMapping("/artefact-structures/{id}")
    @Timed
    public ResponseEntity<ArtefactStructure> getArtefactStructure(@PathVariable Long id) {
        log.debug("REST request to get ArtefactStructure : {}", id);
        ArtefactStructure artefactStructure = artefactStructureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artefactStructure));
    }

    /**
     * DELETE  /artefact-structures/:id : delete the "id" artefactStructure.
     *
     * @param id the id of the artefactStructure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artefact-structures/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtefactStructure(@PathVariable Long id) {
        log.debug("REST request to delete ArtefactStructure : {}", id);
        artefactStructureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/artefact-structures?query=:query : search for the artefactStructure corresponding
     * to the query.
     *
     * @param query the query of the artefactStructure search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/artefact-structures")
    @Timed
    public ResponseEntity<List<ArtefactStructure>> searchArtefactStructures(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ArtefactStructures for query {}", query);
        Page<ArtefactStructure> page = artefactStructureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/artefact-structures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
